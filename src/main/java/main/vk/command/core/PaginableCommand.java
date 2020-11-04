package main.vk.command.core;

import main.vk.PaginationUtils;

public interface PaginableCommand extends Command {

    void doSetCurrentPage(int userId);

    default void doSetCurrentPage(int userId, int pageNumber) {
        switch (PaginationUtils.currentEntity) {
            case "Task": {
                PaginationUtils.setUserCurrentTaskPage(userId, pageNumber);
                break;
            }
            case "Course" : {
                PaginationUtils.setUserCurrentCoursePage(userId, pageNumber);
                break;
            }
            case "MyCourses": {
                PaginationUtils.setUserCurrentBoughtCoursePage(userId, pageNumber);
                break;
            }
        }
    }
}
