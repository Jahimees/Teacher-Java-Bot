package main.vk.command;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.Message;
import main.constant.QueryConstant;
import main.model.BoughtCourseQueryExecutor;
import main.model.CourseQueryExecutor;
import main.model.entity.BoughtCourse;
import main.model.entity.Course;
import main.vk.PaginationUtils;
import main.vk.VKBotBean;
import main.vk.command.core.PaginableCommand;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static main.constant.MessageConstant.NO_ONE_BOUGHT_COURSE;
import static main.constant.QueryConstant.FIND_COURSE_BY_ID;
import static main.vk.PaginationUtils.*;

public class MyCoursesCommand implements PaginableCommand {

    private static int pageCount = 0;

    @Override
    public void execute(VKBotBean vkBotBean, Message message) throws ClientException, ApiException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, message.getUserId());

        BoughtCourseQueryExecutor boughtCourseQueryExecutor = new BoughtCourseQueryExecutor();
        List<BoughtCourse> boughtCourses = boughtCourseQueryExecutor.executeQuery(QueryConstant.FIND_BOUGHT_COURSES_FOR_USER, params);

        String messageStr = boughtCourses.isEmpty() ? NO_ONE_BOUGHT_COURSE : buildMessageFromCourseList(boughtCourses, getUserCurrentBoughtCoursePage(message.getUserId()));

        PaginationUtils.currentEntity = "MyCourses";

        if (getUserCurrentBoughtCoursePage(message.getUserId()) == 0) {
            doSetCurrentPage(message.getUserId());
        }

        vkBotBean.getVk()
                .messages()
                .send(vkBotBean.getActor(), message.getUserId())
                .message(messageStr)
                .execute();
    }

    private String buildMessageFromCourseList(List<BoughtCourse> boughtCourses, int nextPage) {
        List<Course> courses = boughtCourses.stream().map(boughtCourse -> {
            Map<Integer, Object> params = new HashMap<>();
            params.put(1, boughtCourse.getIdCourse());
            CourseQueryExecutor courseQueryExecutor = new CourseQueryExecutor();
            return courseQueryExecutor.executeQuery(FIND_COURSE_BY_ID, params).get(0);
        }).collect(Collectors.toList());

        StringBuilder builder = new StringBuilder("Вот ваши курсы:\n\n");
        pageCount = calculatePageCount(courses);

        nextPage = Math.min(nextPage, pageCount);
        nextPage = nextPage <= 0 ? 1 : nextPage;

        builder.append("\nСтраница ").append(nextPage).append(" из ").append(pageCount == 0 ? 1 : pageCount);

        int until = Math.min((nextPage - 1) * COUNT_ENTITIES_ON_PAGE + COUNT_ENTITIES_ON_PAGE, courses.size());

        for (int i = (nextPage - 1) * COUNT_ENTITIES_ON_PAGE; i < until; i++) {
            builder.append("\nId курса: ").append(courses.get(i).getId())
                    .append("\nИмя курса: ").append(courses.get(i).getName())
                    .append("\nСсылка на курс: ").append(courses.get(i).getLink())
                    .append("\nСтоимость курса: ").append(courses.get(i).getCost())
                    .append("\n~~~~~~~~~~~~~~~~~~~~~~~~\n");
        }
        builder.append("\nЧтобы перейти к следующей странице введите команду 'Дальше'. Чтобы вернуться, введите 'Назад'");

        return builder.toString();
    }

    @Override
    public void doSetCurrentPage(int userId) {
        PaginationUtils.setUserCurrentBoughtCoursePage(userId, 1);
    }

    public static int getPageCount() {
        return pageCount;
    }
}
