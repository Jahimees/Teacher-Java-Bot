package main.vk.command.core;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.Message;
import main.vk.VKBotBean;

public interface Command {

    void execute(VKBotBean vkBotBean, Message message) throws ClientException, ApiException;
}
