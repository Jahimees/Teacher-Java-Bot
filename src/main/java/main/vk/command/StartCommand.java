package main.vk.command;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.Message;
import main.constant.MessageConstant;
import main.vk.VKBotBean;

public class StartCommand implements Command {

    @Override
    public void execute(VKBotBean vkBotBean, Message message) throws ClientException, ApiException {

        String firstName = vkBotBean.getVk()
                .users()
                .get(vkBotBean.getActor())
                .userIds(message.getUserId().toString())
                .execute()
                .get(0)
                .getFirstName();

        vkBotBean.getVk()
                .messages()
                .send(vkBotBean.getActor(), message.getUserId())
                .message(firstName + MessageConstant.SUCCESS_REGISTRATION)
                .execute();

    }
}
