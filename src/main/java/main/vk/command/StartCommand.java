package main.vk.command;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.Message;
import com.vk.api.sdk.objects.users.UserXtrCounters;
import main.model.UserQueryExecutor;
import main.model.entity.User;
import main.vk.VKBotBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static main.constant.MessageConstant.ALREADY_REGISTERED;
import static main.constant.MessageConstant.SUCCESS_REGISTRATION;
import static main.constant.QueryConstant.FIND_USER;
import static main.constant.QueryConstant.INSERT_USER;

public class StartCommand implements Command {

    @Override
    public void execute(VKBotBean vkBotBean, Message message) throws ClientException, ApiException {

        UserXtrCounters user = vkBotBean.getVk()
                .users()
                .get(vkBotBean.getActor())
                .userIds(message.getUserId().toString())
                .execute()
                .get(0);

        boolean isUserExist = isUserExist(message.getUserId());

        if (isUserExist) {
            Map<Integer, Object> params = new HashMap<>();
            params.put(1, message.getUserId().toString());
            params.put(2, user.getFirstName());
            params.put(3, user.getLastName());

            UserQueryExecutor userQueryExecutor = new UserQueryExecutor();
            userQueryExecutor.executeNonQuery(INSERT_USER, params);

            vkBotBean.getVk()
                    .messages()
                    .send(vkBotBean.getActor(), message.getUserId())
                    .message(user.getFirstName() + ALREADY_REGISTERED)
                    .execute();
        } else {
            vkBotBean.getVk()
                    .messages()
                    .send(vkBotBean.getActor(), message.getUserId())
                    .message(user.getFirstName() + SUCCESS_REGISTRATION)
                    .execute();
        }
    }

    private boolean isUserExist(Integer userId) {
        UserQueryExecutor userQueryExecutor = new UserQueryExecutor();
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, userId);
        List<User> userList = userQueryExecutor.executeQuery(FIND_USER, params);
        return !userList.isEmpty();
    }
}
