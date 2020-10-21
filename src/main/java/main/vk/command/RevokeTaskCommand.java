package main.vk.command;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.Message;
import main.model.TaskQueryExecutor;
import main.model.entity.Task;
import main.vk.VKBotBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static main.constant.MessageConstant.*;
import static main.constant.QueryConstant.UPDATE_TASK_OWNER;

public class RevokeTaskCommand extends TakeTaskCommand {

    @Override
    public void execute(VKBotBean vkBotBean, Message message) throws ClientException, ApiException {
        if (!isValidMessage(vkBotBean, message)) {
            return;
        }

        String taskId = message.getBody().split(" ")[1];

        Map<Integer, Object> params = new HashMap<>();
        params.put(1, null);
        params.put(2, null);
        params.put(3, taskId);

        TaskQueryExecutor queryExecutor = new TaskQueryExecutor();
        queryExecutor.executeNonQuery(UPDATE_TASK_OWNER, params);

        vkBotBean.getVk()
                .messages()
                .send(vkBotBean.getActor(), message.getUserId())
                .message(TASK_WITH_NUMBER + taskId + TASK_REVOKED)
                .execute();
    }

    @Override
    protected String validateTaskId(String taskId, int currentUserId) {
        List<Task> task = findTask(taskId);

        if (task == null || task.size() <= 0) {
            return INVALID_ID;
        }

        if (task.get(0).getIdUser() != currentUserId) {
            return TASK_OWNER_NOT_CURRENT_USER;
        }

        if (task.get(0).getDateEnd() != null) {
            return TASK_ALREADY_FINISHED;
        }

        return "";
    }
}
