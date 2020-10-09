package main.vk.command;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.Message;
import com.vk.api.sdk.objects.users.UserXtrCounters;
import main.constant.MessageConstant;
import main.constant.QueryConstant;
import main.model.UserQueryExecutor;
import main.vk.VKBotBean;

import java.util.HashMap;

public class StartCommand implements Command {

    @Override
    public void execute(VKBotBean vkBotBean, Message message) throws ClientException, ApiException {

        UserXtrCounters user = vkBotBean.getVk()
                .users()
                .get(vkBotBean.getActor())
                .userIds(message.getUserId().toString())
                .execute()
                .get(0);

        HashMap<Integer, Object> params = new HashMap<>();
        params.put(1, message.getUserId().toString());
        params.put(2, user.getFirstName());
        params.put(3, user.getLastName());

        UserQueryExecutor userQueryExecutor = new UserQueryExecutor();
        userQueryExecutor.executeNonQuery(QueryConstant.INSERT_USER, params);

        vkBotBean.getVk()
                .messages()
                .send(vkBotBean.getActor(), message.getUserId())
                .message(user.getFirstName() + MessageConstant.SUCCESS_REGISTRATION)
                .execute();

    }
}
