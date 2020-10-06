package main.api;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import lombok.Data;
import main.vk.VKBotManipulator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Данный контроллер необходим чисто для взаимодействия с ботом удаленно.
 */
@Data
@RestController
@RequestMapping("/bot")
public class ManipulationController {

    @Autowired
    VKBotManipulator vkBotManipulator;

//    @GetMapping
//    public String object() {
//        try {
//            Class.forName("org.h2.Driver").newInstance();
//            Connection conn = DriverManager.getConnection("jdbc:h2:file:./data/demo", "sa", "password");
//            PreparedStatement statement = conn.prepareStatement("Select * from billionaires");
//            ResultSet resultSet = statement.executeQuery();
//            String resultString = "";
//            while (resultSet.next()) {
//                String name = resultSet.getString(1);
//                String surname = resultSet.getString(2);
//                String career = resultSet.getString(3);
//                resultString += name + "\n" + surname + "\n" + career + "\n\n";
//            }
//            return resultString;
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//
//        return "success";
//    }

    //TODO Сделать запуск бота в отдельном потоке
    /**
     * Запускает бота.
     * @return
     */
    @GetMapping
    @RequestMapping("/start")
    public String start() {
        try {
            vkBotManipulator.start();
        } catch (ClientException e) {
            return e.toString();
        } catch (ApiException e) {
            return e.toString();
        } catch (InterruptedException e) {
            return e.toString();
        }
        return "success start";
    }
}
