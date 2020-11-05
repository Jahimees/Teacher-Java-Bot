package main.vk;

import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.Message;
import com.vk.api.sdk.queries.messages.MessagesGetLongPollHistoryQuery;
import lombok.SneakyThrows;
import main.model.UserQueryExecutor;
import main.model.entity.User;
import main.vk.command.core.Command;
import main.vk.command.core.CommandResolver;
import main.vk.command.core.UnregisteredCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static main.constant.MessageConstant.NOT_REGISTERED;
import static main.constant.MessageConstant.UNKNOWN_COMMAND_MESSAGE;
import static main.constant.QueryConstant.FIND_USER;

/**
 * Пока чисто тестовое действие. Принимает сообщение и отвечает на него.
 */
@Service
public class VKBotManipulator extends Thread {

    @Autowired
    private VKBotBean vkBotBean;
    private boolean isBotStart = false;

    /**
     * Мониторит сообщения группы каждые 300 миллисекунд.
     * Если он их находит, то извлекает из каждого сообщения отправителя и отправляет сообщение.
     * После чего меняет Ts - номер события, с которого API нужно отправлять нам данные на наше приложение.
     * <p>
     * Если Ts не менять на более новый, то API будет присылать старые данные.
     */
    @SneakyThrows
    public Message goStart() throws ClientException {
        MessagesGetLongPollHistoryQuery query = vkBotBean
                .getVk().messages()
                .getLongPollHistory(vkBotBean.getActor())
                .ts(vkBotBean.getTs());

        List<Message> messageList = query.execute().getMessages().getMessages();
        if (vkBotBean.getMaxMsgId() > 0) {
            query.maxMsgId(vkBotBean.getMaxMsgId());
        }
        if (!messageList.isEmpty()) {
            try {
                for (Message message : messageList) {
                    String commandName = message.getBody().split(" ")[0];
                    Command command = CommandResolver.resolveCommand(commandName);
                    if (command == null) {
                        vkBotBean.getVk().messages().send(vkBotBean.getActor(), message.getUserId()).message(UNKNOWN_COMMAND_MESSAGE).execute();
                    } else if (command instanceof UnregisteredCommand) {
                        command.execute(vkBotBean, message);
                    } else if (isUserRegistered(message)) {
                        command.execute(vkBotBean, message);
                    } else {
                        vkBotBean.getVk()
                                .messages()
                                .send(vkBotBean.getActor(), message.getUserId())
                                .message(NOT_REGISTERED)
                                .execute();
                    }

                    vkBotBean.setTs(vkBotBean.getVk()
                            .messages()
                            .getLongPollServer(vkBotBean.getActor())
                            .execute()
                            .getTs());
                }
            } catch (ClientException e) {
                e.printStackTrace();
            }
        }

        if (!messageList.isEmpty() && !messageList.get(0).isOut()) {
            /*
            messageId - максимально полученный ID, нужен, чтобы не было ошибки 10 internal server error,
            который является ограничением в API VK. В случае, если ts слишком старый (больше суток),
            а max_msg_id не передан, метод может вернуть ошибку 10 (Internal server error).
             */
            int messageId = messageList.get(0).getId();
            if (messageId > vkBotBean.getMaxMsgId()){
                vkBotBean.setMaxMsgId(messageId);
            }
            return messageList.get(0);
        }
        return null;
    }

    private boolean isUserRegistered(Message message) {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, message.getUserId());
        UserQueryExecutor userQueryExecutor = new UserQueryExecutor();
        List<User> userList = userQueryExecutor.executeQuery(FIND_USER, params);
        return !userList.isEmpty();
    }

    @SneakyThrows
    public void run() {
        if (isBotStart) {
            return;
        }
        isBotStart = true;
        while (true) {
            Thread.sleep(300);
            try {
                goStart();
            } catch (ClientException e) {
                System.out.println("Возникли проблемы");
                final int RECONNECT_TIME = 10000;
                System.out.println("Повторное соединение через " + RECONNECT_TIME / 1000 + " секунд");
                e.printStackTrace();
                Thread.sleep(RECONNECT_TIME);
            }
        }
    }
}
