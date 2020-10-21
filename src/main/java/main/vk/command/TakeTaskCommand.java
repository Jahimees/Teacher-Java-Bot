
package main.vk.command;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.Message;
import main.constant.QueryConstant;
import main.model.TaskQueryExecutor;
import main.model.entity.Task;
import main.vk.VKBotBean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static main.constant.FieldConstantDB.TASK_COUNT;
import static main.constant.MessageConstant.*;
import static main.constant.QueryConstant.FIND_TASK_BY_ID;
import static main.constant.QueryConstant.UPDATE_TASK_OWNER;

public class TakeTaskCommand implements Command {

    @Override
    public void execute(VKBotBean vkBotBean, Message message) throws ClientException, ApiException {
        if (!isValidMessage(vkBotBean, message)) {
            return;
        }

        if (countUserTasks(message) >= 2) {
            vkBotBean.getVk()
                    .messages()
                    .send(vkBotBean.getActor(), message.getUserId())
                    .message(TOO_MUCH_TASKS)
                    .execute();
            return;
        }

        String taskId = message.getBody().split(" ")[1];

        Map<Integer, Object> params = new HashMap<>();
        params.put(1, message.getUserId());
        params.put(2, LocalDateTime.now());
        params.put(3, taskId);

        TaskQueryExecutor queryExecutor = new TaskQueryExecutor();
        queryExecutor.executeNonQuery(UPDATE_TASK_OWNER, params);

        vkBotBean.getVk()
                .messages()
                .send(vkBotBean.getActor(), message.getUserId())
                .message(TASK_WITH_NUMBER + taskId + TASK_TOOK)
                .execute();
    }

    protected String validateTaskId(String taskId, int currentUserId) {

        List<Task> task = findTask(taskId);

        if (task == null || task.size() <= 0) {
            return INVALID_ID;
        }

        if (task.get(0).getIdUser() > 0) {
            return TASK_ALREADY_IN_WORK;
        }

        if (task.get(0).getDateEnd() != null) {
            return TASK_ALREADY_FINISHED;
        }

        return "";
    }

    protected List<Task> findTask(String taskId) {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, taskId);

        TaskQueryExecutor queryExecutor = new TaskQueryExecutor();

        return queryExecutor.executeQuery(FIND_TASK_BY_ID, params);
    }

    protected boolean isValidMessage(VKBotBean vkBotBean, Message message) throws ClientException, ApiException {
        if (message.getBody().split(" ").length != 2) {
            vkBotBean.getVk().messages().send(vkBotBean.getActor(), message.getUserId()).message(INPUT_TASK_ID).execute();
            return false;
        }
        String taskId = message.getBody().split(" ")[1];

        String errorString = validateTaskId(taskId, message.getUserId());

        if (!errorString.equals("")) {

            vkBotBean.getVk()
                    .messages()
                    .send(vkBotBean.getActor(), message.getUserId())
                    .message(errorString)
                    .execute();
            return false;
        }

        return true;
    }

    private int countUserTasks(Message message) {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, message.getUserId());

        int result = 0;

        TaskQueryExecutor taskQueryExecutor = new TaskQueryExecutor();
        ResultSet resultSet = taskQueryExecutor.execute(QueryConstant.TASKS_COUNT, params, false);
        try {
            if (resultSet.next()) {
                result = resultSet.getInt(TASK_COUNT);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return result;
    }

}
