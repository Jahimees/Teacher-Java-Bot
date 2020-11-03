package main.vk.command;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.Message;
import main.model.BoughtCourseQueryExecutor;
import main.model.CourseQueryExecutor;
import main.model.UserQueryExecutor;
import main.model.entity.BoughtCourse;
import main.model.entity.Course;
import main.model.entity.User;
import main.vk.VKBotBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static main.constant.MessageConstant.*;
import static main.constant.QueryConstant.*;

public class BuyCourseCommand implements ParametrizedCommand {

    @Override
    public void execute(VKBotBean vkBotBean, Message message) throws ClientException, ApiException {
        if (!isValidMessage(vkBotBean, message)) {
            return;
        }

        String courseId = message.getBody().split(" ")[1];

        Map<Integer, Object> params = new HashMap<>();
        params.put(1, message.getUserId());
        params.put(2, courseId);

        BoughtCourseQueryExecutor queryExecutor = new BoughtCourseQueryExecutor();
        queryExecutor.executeNonQuery(INSERT_BOUGHT_COURSE, params);

        UserQueryExecutor userQueryExecutor = new UserQueryExecutor();
        User currentUser = userQueryExecutor.findUser(message.getUserId());

        Course course = findCourse(courseId).get(0);
        currentUser.setBalance(currentUser.getBalance() - course.getCost());

        userQueryExecutor.updateUser(currentUser);

        vkBotBean.getVk()
                .messages()
                .send(vkBotBean.getActor(), message.getUserId())
                .message(COURSE_WITH_NUMBER + courseId + SUCCESSFULLY_BOUGHT)
                .execute();
    }

    @Override
    public String validateEntityId(String courseId, int currentUserId) {

        List<Course> courses = findCourse(courseId);

        if (courses == null || courses.size() <= 0) {
            return INVALID_ID;
        }

        List<BoughtCourse> boughtCourses = findBoughtCourses(currentUserId);

        List<Integer> boughtCourseIds = boughtCourses.stream()
                .map(BoughtCourse::getIdCourse)
                .collect(Collectors.toList());

        if (!boughtCourseIds.isEmpty() && boughtCourseIds.contains(Integer.parseInt(courses.get(0).getId()))) {
            return COURSE_ALREADY_BOUGHT;
        }

        UserQueryExecutor userQueryExecutor = new UserQueryExecutor();
        User user = userQueryExecutor.findUser(currentUserId);

        if (user.getBalance() < courses.get(0).getCost()) {
            return NOT_ENOUGH_BONUSES;
        }

        return "";
    }

    protected List<Course> findCourse(String courseId) {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, courseId);

        CourseQueryExecutor queryExecutor = new CourseQueryExecutor();

        return queryExecutor.executeQuery(FIND_COURSE_BY_ID, params);
    }

    private List<BoughtCourse> findBoughtCourses(int userId) {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, userId);

        BoughtCourseQueryExecutor boughtCourseQueryExecutor = new BoughtCourseQueryExecutor();
        return boughtCourseQueryExecutor.executeQuery(FIND_BOUGHT_COURSES_FOR_USER, params);
    }

}
