package main.vk.command;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.Message;
import main.model.UserQueryExecutor;
import main.model.entity.User;
import main.vk.VKBotBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static main.constant.MessageConstant.CURRENT_BALANCE;
import static main.constant.QueryConstant.FIND_USER;

/**
 * Сообщает пользователю его текущий баланс баллов
 */
public class BalanceCommand implements Command {

    @Override
    public void execute(VKBotBean vkBotBean, Message message) throws ClientException, ApiException {

        Map<Integer, Object> params = new HashMap<>();
        params.put(1, message.getUserId());

        UserQueryExecutor userQueryExecutor = new UserQueryExecutor();
        List<User> userList = userQueryExecutor.executeQuery(FIND_USER, params);

        vkBotBean.getVk()
                .messages()
                .send(vkBotBean.getActor(), message.getUserId())
                .message(CURRENT_BALANCE + userList.get(0).getBalance())
                .execute();
    }
}
