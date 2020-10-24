package main.vk.command;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.Message;
import main.model.TaskQueryExecutor;
import main.model.entity.Task;
import main.vk.PaginationUtils;
import main.vk.VKBotBean;

import java.util.List;

import static main.constant.MessageConstant.NO_TASKS;
import static main.constant.QueryConstant.FIND_FREE_TASKS;
import static main.vk.PaginationUtils.*;

public class TaskCommand implements Command {

    @Override
    public void execute(VKBotBean vkBotBean, Message message) throws ClientException, ApiException {
        TaskQueryExecutor taskQueryExecutor = new TaskQueryExecutor();

        List<Task> taskList = taskQueryExecutor.executeQuery(FIND_FREE_TASKS, null);

        doSetCurrentPage(message.getUserId());

        String messageStr = taskList.size() == 0 ? NO_TASKS : buildMessageFromTaskList(taskList, getUserCurrentPage(message.getUserId()));

        vkBotBean.getVk()
                .messages()
                .send(vkBotBean.getActor(), message.getUserId())
                .message(messageStr)
                .execute();
    }

    protected String buildMessageFromTaskList(List<Task> taskList, int nextPage) {
        StringBuilder builder = new StringBuilder("Вот доступные задания:\n\n");
        int pageCount = calculatePageCount(taskList);

        nextPage = Math.min(nextPage, pageCount);
        nextPage = nextPage <= 0 ? 1 : nextPage;

        builder.append("\nСтраница ").append(nextPage).append(" из ").append(pageCount == 0 ? 1 : pageCount);

        int until = Math.min((nextPage - 1) * COUNT_TASKS_ON_PAGE + COUNT_TASKS_ON_PAGE, taskList.size());

        for (int i = (nextPage - 1) * COUNT_TASKS_ON_PAGE; i < until; i++) {
            builder.append("\nId задания: ").append(taskList.get(i).getId())
                    .append("\n Задание: ").append(taskList.get(i).getText())
                    .append("\n Бонусов за задание: ").append(taskList.get(i).getBonuses())
                    .append("\n~~~~~~~~~~~~~~~~~~~~~~~~\n");
        }
        builder.append("\nЧтобы перейти к следующей странице введите команду 'Дальше'. Чтобы вернуться, введите 'Назад'");
        builder.append("\nЧтобы взять задание в работу, введите 'взять_задание id_задания'");
        builder.append("\nЧтобы посмотреть список ваших задач, введите 'мои_задания'");
        return builder.toString();
    }

    protected void doSetCurrentPage(int userId) {
        PaginationUtils.setUserCurrentPage(userId, 1);
    }

}
