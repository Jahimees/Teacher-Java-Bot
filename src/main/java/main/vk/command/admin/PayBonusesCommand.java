package main.vk.command.admin;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.Message;
import main.constant.MessageConstant;
import main.model.UserQueryExecutor;
import main.model.entity.User;
import main.vk.VKBotBean;
import main.vk.command.core.AdminCommand;

import java.util.Arrays;
import java.util.List;

import static main.constant.MessageConstant.ERROR;
import static main.constant.MessageConstant.FOR_USER;

public class PayBonusesCommand implements AdminCommand {

    /**
     * подарить ~idUser~bonusesCount
     */
    @Override
    public void doExecute(VKBotBean vkBotBean, Message message) throws ClientException, ApiException {
        if (!checkArguments(vkBotBean, message)) {
            return;
        }

        List<String> arguments = Arrays.asList(message.getBody().split("~"));
        int uid;
        int bonuses;
        try {
            uid = Integer.parseInt(arguments.get(1));
            bonuses = Integer.parseInt(arguments.get(2));
        } catch (NumberFormatException e) {
            vkBotBean.getVk()
                    .messages()
                    .send(vkBotBean.getActor(), message.getUserId())
                    .message(ERROR)
                    .execute();
            e.printStackTrace();
            return;
        }

        UserQueryExecutor userQueryExecutor = new UserQueryExecutor();
        User user = userQueryExecutor.findUser(uid);
        user.setBalance(user.getBalance() + bonuses);
        userQueryExecutor.updateUser(user);

        vkBotBean.getVk()
                .messages()
                .send(vkBotBean.getActor(), message.getUserId())
                .message(FOR_USER + user.getFirstName() + MessageConstant.PAYED_BONUSES + bonuses)
                .execute();
    }
}
