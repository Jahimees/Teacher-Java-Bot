package main.vk.command;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.Message;
import main.constant.QueryConstant;
import main.model.CourseQueryExecutor;
import main.model.entity.Course;
import main.vk.PaginationUtils;
import main.vk.VKBotBean;
import main.vk.command.core.PaginableCommand;

import java.util.List;

import static main.constant.MessageConstant.NO_COURSES;
import static main.vk.PaginationUtils.*;

public class CoursesCommand implements PaginableCommand {

    private static int pageCount = 0;

    @Override
    public void execute(VKBotBean vkBotBean, Message message) throws ClientException, ApiException {

        CourseQueryExecutor courseQueryExecutor = new CourseQueryExecutor();
        List<Course> courseList = courseQueryExecutor.executeQuery(QueryConstant.FIND_ALL_COURCES, null);

        PaginationUtils.currentEntity = "Course";

        String messageStr = courseList.size() == 0 ? NO_COURSES : buildMessageFromCourseList(courseList, getUserCurrentCoursePage(message.getUserId()));

        if (getUserCurrentCoursePage(message.getUserId()) == 0) {
            doSetCurrentPage(message.getUserId());
        }

        vkBotBean.getVk()
                .messages()
                .send(vkBotBean.getActor(), message.getUserId())
                .message(messageStr)
                .execute();
    }

    private String buildMessageFromCourseList(List<Course> courseList, int nextPage) {
        StringBuilder builder = new StringBuilder("Вот доступные курсы:\n\n");
        pageCount = calculatePageCount(courseList);

        nextPage = Math.min(nextPage, pageCount);
        nextPage = nextPage <= 0 ? 1 : nextPage;

        builder.append("\nСтраница ").append(nextPage).append(" из ").append(pageCount == 0 ? 1 : pageCount);

        int until = Math.min((nextPage - 1) * COUNT_ENTITIES_ON_PAGE + COUNT_ENTITIES_ON_PAGE, courseList.size());

        for (int i = (nextPage - 1) * COUNT_ENTITIES_ON_PAGE; i < until; i++) {
            builder.append("\nId курса: ").append(courseList.get(i).getId())
                    .append("\nИмя курса: ").append(courseList.get(i).getName())
                    .append("\nСтоимость курса: ").append(courseList.get(i).getCost())
                    .append("\n~~~~~~~~~~~~~~~~~~~~~~~~\n");
        }
        builder.append("\nЧтобы перейти к следующей странице введите команду 'Дальше'. Чтобы вернуться, введите 'Назад'");
        builder.append("\nЧтобы купить курс, введите 'купить_курс id_курса'");
        builder.append("\nЧтобы посмотреть купленные курсы, введите 'мои_курсы'");

        return builder.toString();
    }

    @Override
    public void doSetCurrentPage(int userId) {
        PaginationUtils.setUserCurrentCoursePage(userId, 1);
    }

    public static int getPageCount() {
        return pageCount;
    }

}
