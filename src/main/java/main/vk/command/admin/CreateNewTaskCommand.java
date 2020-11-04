package main.vk.command.admin;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.Message;
import main.constant.QueryConstant;
import main.model.TaskQueryExecutor;
import main.vk.VKBotBean;
import main.vk.command.core.AdminCommand;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static main.constant.MessageConstant.ERROR;
import static main.constant.MessageConstant.TASK_CREATED;

public class CreateNewTaskCommand implements AdminCommand {

    /**
     * новое_задание ~Бла-бла бла-бла~15
     */
    @Override
    public void doExecute(VKBotBean vkBotBean, Message message) throws ClientException, ApiException {
        if (!checkArguments(vkBotBean, message)) {
            return;
        }

        List<String> arguments = Arrays.asList(message.getBody().split("~"));

        String uuid = java.util.UUID.randomUUID().toString();
        String taskText = arguments.get(1);
        int bonuses = 0;
        try {
            bonuses = Integer.parseInt(arguments.get(2));
        } catch (NumberFormatException e) {
            vkBotBean.getVk()
                    .messages()
                    .send(vkBotBean.getActor(), message.getUserId())
                    .message(ERROR)
                    .execute();
            e.printStackTrace();
            return;
        }

        Map<Integer, Object> params = new HashMap<>();
        params.put(1, uuid);
        params.put(2, taskText);
        params.put(3, bonuses);
        params.put(4, LocalDateTime.now());

        TaskQueryExecutor taskQueryExecutor = new TaskQueryExecutor();
        taskQueryExecutor.executeNonQuery(QueryConstant.INSERT_TASK, params);

        vkBotBean.getVk()
                .messages()
                .send(vkBotBean.getActor(), message.getUserId())
                .message(TASK_CREATED)
                .execute();
    }
}
