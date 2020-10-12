package main.api;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import lombok.Data;
import main.constant.QueryConstant;
import main.model.UserQueryExecutor;
import main.model.entity.User;
import main.vk.VKBotBean;
import main.vk.VKBotManipulator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

import static main.constant.MessageConstant.PLUS_BONUS_1;
import static main.constant.MessageConstant.PLUS_BONUS_2;

/**
 * Данный контроллер необходим чисто для взаимодействия с ботом удаленно.
 */
@Data
@RestController
@RequestMapping("/bot")
public class ManipulationController {

    @Autowired
    VKBotManipulator vkBotManipulator;
    @Autowired
    VKBotBean vkBotBean;

    @GetMapping
    public String object() {
        UserQueryExecutor userQueryExecutor = new UserQueryExecutor();

        String query = "SELECT * FROM users";
        List<User> userList = userQueryExecutor.executeQuery(query, null);
        String result = !userList.isEmpty() ? userList.toString() : null;
        result = result != null ? result : "Not found";

        return result;
    }

    /**
     * Запускает бота.
     * @return
     */
    @GetMapping
    @RequestMapping("/start")
    public String start() {
      vkBotManipulator.run();
        return "success start";
    }

    //FIX по факту post но мне лень в postman
    @GetMapping
    @RequestMapping("/bonus/{userId}/{plusBonusCount}")
    public String payBonus(@PathVariable Integer userId, @PathVariable Integer plusBonusCount) throws ClientException, ApiException {
        HashMap<Integer, Object> params = new HashMap<>();
        params.put(1, userId);

        UserQueryExecutor userQueryExecutor = new UserQueryExecutor();
        List<User> userList = userQueryExecutor.executeQuery(QueryConstant.FIND_USER, params);

        if (userList.isEmpty()) {
            return "Can't find user with id " + userId;
        }
        User user = userList.get(0);
        user.setBalance(user.getBalance() + plusBonusCount);

        userQueryExecutor = new UserQueryExecutor();
        userQueryExecutor.updateUser(user);

        vkBotBean.getVk()
                .messages()
                .send(vkBotBean.getActor(), userId)
                .message(PLUS_BONUS_1 + plusBonusCount + PLUS_BONUS_2)
                .execute();

        return "User " + user.getFirstName() + " " + user.getLastName() + " get's " + plusBonusCount + " bonuses\n" +
                "Now he has " + user.getBalance() + " bonuses";
    }
}
