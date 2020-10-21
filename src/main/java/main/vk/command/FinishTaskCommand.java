package main.vk.command;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.Message;
import main.model.TaskQueryExecutor;
import main.model.UserQueryExecutor;
import main.model.entity.Task;
import main.model.entity.User;
import main.vk.VKBotBean;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static main.constant.MessageConstant.*;
import static main.constant.QueryConstant.FIND_USER;
import static main.constant.QueryConstant.FINISH_TASK;

public class FinishTaskCommand extends RevokeTaskCommand {

    @Override
    public void execute(VKBotBean vkBotBean, Message message) throws ClientException, ApiException {
        if (!isValidMessage(vkBotBean, message)) {
            return;
        }

        String taskId = message.getBody().split(" ")[1];

        finishTask(taskId);
        Task task = findTask(taskId).get(0);
        updateUserBalance(message, task);

        vkBotBean.getVk()
                .messages()
                .send(vkBotBean.getActor(), message.getUserId())
                .message(TASK_WITH_NUMBER + taskId + TASK_FINISHED + "\n" + PLUS_BONUS_1 + task.getBonuses() + PLUS_BONUS_2)
                .execute();
    }

    private void finishTask(String taskId) {
        Map<Integer, Object> taskParams = new HashMap<>();
        taskParams.put(1, LocalDateTime.now());
        taskParams.put(2, taskId);

        TaskQueryExecutor taskQueryExecutor = new TaskQueryExecutor();
        taskQueryExecutor.executeNonQuery(FINISH_TASK, taskParams);
    }

    private void updateUserBalance(Message message, Task task) {
        Map<Integer, Object> userParams = new HashMap<>();
        userParams.put(1, message.getUserId());

        UserQueryExecutor userQueryExecutor = new UserQueryExecutor();
        User user = userQueryExecutor.executeQuery(FIND_USER, userParams).get(0);

        user.setBalance(user.getBalance() + task.getBonuses());

        userQueryExecutor.updateUser(user);
    }

}
