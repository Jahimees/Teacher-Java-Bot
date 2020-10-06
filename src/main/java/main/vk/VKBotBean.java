package main.vk;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import lombok.Data;
import main.config.VkGroupConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Данный класс представляет собой саму начинку бота, через которую с ним можно взаимодействовать.
 *
 * Пока что (и в дальнейшем) боту задается именно конфигурация на группу. Id группы и AccessToken берется из конфигурации
 * vk_group.properties
 */
@Data
@Component
public class VKBotBean {

    private VkGroupConfiguration vkGroupConfiguration;

    private GroupActor actor;
    private VkApiClient vk;
    private int ts;
    //Нужно записывать в файл, чтобы не терять пропущенные сообщения
    private int maxMsgId = -1;

    @Autowired
    public VKBotBean(VkGroupConfiguration vkGroupConfiguration) throws ClientException, ApiException {
        this.vkGroupConfiguration = vkGroupConfiguration;

        TransportClient transportClient = HttpTransportClient.getInstance();
        vk = new VkApiClient(transportClient);
        actor = new GroupActor(vkGroupConfiguration.getGroupId(), vkGroupConfiguration.getAccessToken());
        ts = vk.messages().getLongPollServer(actor).execute().getTs();
    }
}
