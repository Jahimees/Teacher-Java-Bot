package main.vk.command.core;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.Message;
import lombok.SneakyThrows;
import main.vk.VKBotBean;

import java.util.Arrays;
import java.util.List;

import static main.constant.MessageConstant.INVALID_ARGUMENTS;
import static main.constant.MessageConstant.NOT_ADMIN;

public interface AdminCommand extends Command {

    @SneakyThrows
    @Override
    default void execute(VKBotBean vkBotBean, Message message) {
        if (message.getUserId() != 144467749) {
            vkBotBean.getVk()
                    .messages()
                    .send(vkBotBean.getActor(), message.getUserId())
                    .message(NOT_ADMIN)
                    .execute();
            return;
        }

        doExecute(vkBotBean, message);
    }

    @SneakyThrows
    default boolean checkArguments(VKBotBean vkBotBean, Message message) {
        List<String> arguments = Arrays.asList(message.getBody().split("~"));

        if (arguments.size() != 3) {
            vkBotBean.getVk()
                    .messages()
                    .send(vkBotBean.getActor(), message.getUserId())
                    .message(INVALID_ARGUMENTS)
                    .execute();

            return false;
        }
        return true;
    }

    void doExecute(VKBotBean vkBotBean, Message message) throws ClientException, ApiException;
}
