package main.vk.command;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.Message;
import main.constant.QueryConstant;
import main.model.TaskQueryExecutor;
import main.model.entity.Task;
import main.vk.Utils;
import main.vk.VKBotBean;

import java.util.HashMap;
import java.util.List;

public class TaskCommand implements Command {

    private static HashMap<String, Integer> currentPage = new HashMap<>();
    private static int pageCount;

    @Override
    public void execute(VKBotBean vkBotBean, Message message) throws ClientException, ApiException {
        TaskQueryExecutor taskQueryExecutor = new TaskQueryExecutor();

        List<Task> taskList = taskQueryExecutor.executeQuery(QueryConstant.FIND_FREE_TASKS, null);
        String messageStr = buildMessageFromTaskList(taskList);

        pageCount = Utils.calculatePageCount(taskList);


        vkBotBean.getVk()
                .messages()
                .send(vkBotBean.getActor(), message.getUserId())
                .message(messageStr)
                .execute();
    }

    private String buildMessageFromTaskList(List<Task> taskList) {
        StringBuilder builder = new StringBuilder("Вот доступные задания:\n\n");
        int pageCount = Utils.calculatePageCount(taskList);
        for (int i = 0; i < pageCount; i++) {
            builder.append(" Id задания: ").append(taskList.get(i).getId())
                .append("\n Задание: ").append(taskList.get(i).getText())
                .append("\n Бонусов за задание: ").append(taskList.get(i).getBonuses())
                .append("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
        }
        builder.append("\nЧтобы перейти к следующей странице введите команду 'Дальше'");
        return builder.toString();
    }

}
