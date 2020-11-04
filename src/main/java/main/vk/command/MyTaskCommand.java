package main.vk.command;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.Message;
import main.model.TaskQueryExecutor;
import main.model.entity.Task;
import main.vk.VKBotBean;
import main.vk.command.core.Command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static main.constant.MessageConstant.HAS_NOT_ACTIVE_TASKS;
import static main.constant.QueryConstant.FIND_USER_TASKS;

public class MyTaskCommand implements Command {

    @Override
    public void execute(VKBotBean vkBotBean, Message message) throws ClientException, ApiException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, message.getUserId());

        TaskQueryExecutor queryExecutor = new TaskQueryExecutor();
        List<Task> userTasks = queryExecutor.executeQuery(FIND_USER_TASKS, params);

        String responseMessage = buildResponseMessage(userTasks);

        vkBotBean.getVk()
                .messages()
                .send(vkBotBean.getActor(), message.getUserId())
                .message(responseMessage)
                .execute();
    }

    private String buildResponseMessage(List<Task> userTasks) {
        if (userTasks.size() <= 0) {
            return HAS_NOT_ACTIVE_TASKS;
        }

        StringBuilder message = new StringBuilder("Ваши текущие задачи:\n\n");
        for (Task task : userTasks) {
            message.append("ID: ").append(task.getId()).append("\n")
                .append("Задача: ").append(task.getText()).append("\n")
                .append("Награда: ").append(task.getBonuses()).append("\n")
                .append("Взята в работу: ").append(task.getDateStart()).append("\n______________________");
        }

        message.append("\n\nЧтобы отменить задание, введите 'отменить_задание id_задания'");
        message.append("\nЧтобы закончить задание, введите 'закончить_задание id_задания'");

        return message.toString();
    }
}
