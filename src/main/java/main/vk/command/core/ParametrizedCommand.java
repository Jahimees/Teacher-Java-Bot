package main.vk.command.core;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.Message;
import main.vk.VKBotBean;

import static main.constant.MessageConstant.INPUT_ID;

public interface ParametrizedCommand extends Command {

    default boolean isValidMessage(VKBotBean vkBotBean, Message message) throws ClientException, ApiException {
        if (message.getBody().split(" ").length != 2) {
            vkBotBean.getVk().messages().send(vkBotBean.getActor(), message.getUserId()).message(INPUT_ID).execute();
            return false;
        }
        String taskId = message.getBody().split(" ")[1];

        String errorString = validateEntityId(taskId, message.getUserId());

        if (!errorString.equals("")) {

            vkBotBean.getVk()
                    .messages()
                    .send(vkBotBean.getActor(), message.getUserId())
                    .message(errorString)
                    .execute();
            return false;
        }

        return true;
    }

    String validateEntityId(String entityId, int userId);
}
