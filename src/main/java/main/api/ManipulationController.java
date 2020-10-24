package main.api;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import lombok.Data;
import main.constant.QueryConstant;
import main.model.TaskQueryExecutor;
import main.model.UserQueryExecutor;
import main.model.entity.Task;
import main.model.entity.User;
import main.vk.VKBotBean;
import main.vk.VKBotManipulator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static main.constant.MessageConstant.PLUS_BONUS_1;
import static main.constant.MessageConstant.PLUS_BONUS_2;
import static main.constant.QueryConstant.DROP_USER;

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
    @RequestMapping("/users")
    public String allUsers() {
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

    @GetMapping
    @RequestMapping("/create_task")
    public String addNewTask() {
        Map<Integer, Object> params = new HashMap<>();
        UUID uuid =  UUID.randomUUID();
        String taskText = "СОРТИРОВКА\nСоздать метод, который будет сортировать указанный массив по возрастанию любым известным вам способом.";
        params.put(1, uuid.toString());
        params.put(2, taskText);
        params.put(3, 15);
        params.put(4, LocalDateTime.now());

        TaskQueryExecutor taskQueryExecutor = new TaskQueryExecutor();
        taskQueryExecutor.executeNonQuery(QueryConstant.INSERT_TASK, params);

        List<Task> tasks = taskQueryExecutor.executeQuery(QueryConstant.FIND_ALL_TASKS, null);

        return "Task " + uuid.toString() + " successfully created. Tasks: " + tasks.toString();
    }

    @GetMapping
    @RequestMapping("/task")
    public String getAllTasks() {

        TaskQueryExecutor taskQueryExecutor = new TaskQueryExecutor();

        List<Task> tasks = taskQueryExecutor.executeQuery(QueryConstant.FIND_ALL_TASKS, null);

        return tasks.toString();
    }

    //FIX по факту post но мне лень в postman
    @GetMapping
    @RequestMapping("/bonus/{userId}/{plusBonusCount}")
    public String payBonus(@PathVariable Integer userId, @PathVariable Integer plusBonusCount) throws ClientException, ApiException {
        Map<Integer, Object> params = new HashMap<>();
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

    @GetMapping
    @RequestMapping("/dropUser/{userId}")
    public static String dropUser(@PathVariable Integer userId) {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, userId);

        UserQueryExecutor userQueryExecutor = new UserQueryExecutor();
        userQueryExecutor.executeNonQuery(DROP_USER, params);

        return "User successfully deleted";
    }
}
