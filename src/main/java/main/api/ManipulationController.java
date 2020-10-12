package main.api;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import lombok.Data;
import main.model.UserQueryExecutor;
import main.model.entity.User;
import main.vk.VKBotManipulator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Данный контроллер необходим чисто для взаимодействия с ботом удаленно.
 */
@Data
@RestController
@RequestMapping("/bot")
public class ManipulationController {

    @Autowired
    VKBotManipulator vkBotManipulator;

    @GetMapping
    public String object() {
        UserQueryExecutor userQueryExecutor = new UserQueryExecutor();
//        String query1 = "INSERT INTO users (id, first_name, last_name, balance) values (?,?,?,?);";

//        HashMap<Integer, Object> params = new HashMap<>();
//        params.put(1, 143);
//        params.put(2, "Henkel");
//        params.put(3, "Gavr");
//        params.put(4, 100);
//
//        userQueryExecutor.executeNonQuery(query1, params);

        userQueryExecutor = new UserQueryExecutor();

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
}
