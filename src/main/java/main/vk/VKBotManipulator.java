package main.vk;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.Message;
import com.vk.api.sdk.queries.messages.MessagesGetLongPollHistoryQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Пока чисто тестовое действие. Принимает сообщение и отвечает на него.
 *
 */
@Service
public class VKBotManipulator {

    @Autowired
    private VKBotBean vkBotBean;
    private boolean isBotStart = false;

    private final String sendMessage = "Если ты получил это сообщение, значит, я функционирую. Пока я буду отвечать на любое твое сообщение так";

    /**
     * Мониторит сообщения группы каждые 300 миллисекунд.
     * Если он их находит, то извлекает из каждого сообщения отправителя и отправляет сообщение.
     * После чего меняет Ts - номер события, с которого API нужно отправлять нам данные на наше приложение.
     *
     * Если Ts не менять на более новый, то API будет присылать старые данные.
     * @throws ClientException
     * @throws ApiException
     * @throws InterruptedException
     */
    public void start() throws ClientException, ApiException, InterruptedException {
        if (isBotStart) {
            return;
        }
        isBotStart = true;

        while (true) {
            Thread.sleep(300);
            MessagesGetLongPollHistoryQuery query = vkBotBean.getVk().messages()
                    .getLongPollHistory(vkBotBean.getActor())
                    .ts(vkBotBean.getTs());

            List<Message> messageList = query.execute().getMessages().getMessages();
            if (vkBotBean.getMaxMsgId() > 0) {
                query.maxMsgId(vkBotBean.getMaxMsgId());
            }
            if (messageList.size() != 0) {
                for (Message message : messageList) {
                    vkBotBean.getVk().messages().send(vkBotBean.getActor(), message.getUserId()).message(sendMessage).execute();
                    vkBotBean.setTs(vkBotBean.getVk().messages().getLongPollServer(vkBotBean.getActor()).execute().getTs());
                }

            }
        }
    }

}
