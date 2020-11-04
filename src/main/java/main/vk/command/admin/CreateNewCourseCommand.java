package main.vk.command.admin;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.Message;
import lombok.SneakyThrows;
import main.constant.QueryConstant;
import main.model.CourseQueryExecutor;
import main.vk.VKBotBean;
import main.vk.command.core.AdminCommand;

import java.util.*;

import static main.constant.MessageConstant.*;

public class CreateNewCourseCommand implements AdminCommand {

    /**
     * новый_курс ~cxlFpBXl7HQ~name~cost
     */
    @Override
    public void doExecute(VKBotBean vkBotBean, Message message) throws ClientException, ApiException {
        List<String> arguments = Arrays.asList(message.getBody().split("~"));

        String id = UUID.randomUUID().toString();
        String link = "https://youtu.be/" + arguments.get(1);
        String name = arguments.get(2);
        int cost;

        try {
          cost = Integer.parseInt(arguments.get(3));
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
        params.put(1, id);
        params.put(2, name);
        params.put(3, link);
        params.put(4, cost);

        CourseQueryExecutor courseQueryExecutor = new CourseQueryExecutor();
        courseQueryExecutor.executeNonQuery(QueryConstant.ADD_NEW_COURSE, params);

        vkBotBean.getVk()
                .messages()
                .send(vkBotBean.getActor(), message.getUserId())
                .message(COURSE_CREATED)
                .execute();
    }

    @SneakyThrows
    @Override
    public boolean checkArguments(VKBotBean vkBotBean, Message message) {
        List<String> arguments = Arrays.asList(message.getBody().split("~"));

        if (arguments.size() != 4) {
            vkBotBean.getVk()
                    .messages()
                    .send(vkBotBean.getActor(), message.getUserId())
                    .message(INVALID_ARGUMENTS)
                    .execute();

            return false;
        }
        return true;
    }
}
