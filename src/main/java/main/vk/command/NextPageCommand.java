package main.vk.command;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.Message;
import main.vk.PaginationUtils;
import main.vk.VKBotBean;

import static main.constant.MessageConstant.UNDEFINED_ENTITY_PAGINATION;

public class NextPageCommand implements PaginableCommand {

    @Override
    public void execute(VKBotBean vkBotBean, Message message) throws ClientException, ApiException {
        switch (PaginationUtils.currentEntity) {
            case "Task": {
                int pageCount = TaskCommand.getPageCount();
                int currentPage = PaginationUtils.getUserCurrentTaskPage(message.getUserId());
                doSetCurrentPage(message.getUserId(), Math.min(++currentPage, pageCount));
                new TaskCommand().execute(vkBotBean, message);
                break;
            }
            case "Course": {
                int pageCount = CoursesCommand.getPageCount();
                int currentPage = PaginationUtils.getUserCurrentCoursePage(message.getUserId());
                doSetCurrentPage(message.getUserId(), Math.min(++currentPage, pageCount));
                new CoursesCommand().execute(vkBotBean, message);
                break;
            }
            case "MyCourses": {
                int pageCount = MyCoursesCommand.getPageCount();
                int currentPage = PaginationUtils.getUserCurrentBoughtCoursePage(message.getUserId());
                doSetCurrentPage(message.getUserId(), Math.min(++currentPage, pageCount));
                new MyCoursesCommand().execute(vkBotBean, message);
                break;
            }
            default: {
                vkBotBean.getVk()
                        .messages()
                        .send(vkBotBean.getActor(), message.getUserId())
                        .message(UNDEFINED_ENTITY_PAGINATION)
                        .execute();
            }
        }
    }

    @Override
    public void doSetCurrentPage(int userId) {
        switch (PaginationUtils.currentEntity) {
            case "Task": {
                int currentPage = PaginationUtils.getUserCurrentTaskPage(userId);
                PaginationUtils.setUserCurrentTaskPage(userId, currentPage + 1);
                break;
            }
            case "Courses" : {
                int currentPage = PaginationUtils.getUserCurrentCoursePage(userId);
                PaginationUtils.setUserCurrentCoursePage(userId, currentPage + 1);
                break;
            }
            case "MyCourses": {
                int currentPage = PaginationUtils.getUserCurrentBoughtCoursePage(userId);
                PaginationUtils.setUserCurrentBoughtCoursePage(userId, currentPage + 1);
            }
        }
    }
}
