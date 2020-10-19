package main.vk;

import main.model.entity.Task;

import java.util.HashMap;
import java.util.List;

public final class PaginationUtils {

    public static HashMap<Integer, Integer> currentPage = new HashMap<>();
    public static final int COUNT_TASKS_ON_PAGE = 3;

    private PaginationUtils() {
    }

    public static int calculatePageCount(List<Task> taskList) {
        if (taskList.size() == 0) {
            return 0;
        }

        int fullPage;
        fullPage = taskList.size() / COUNT_TASKS_ON_PAGE;

        if (fullPage == 0) {
            return 1;
        }

        return fullPage + taskList.size() % COUNT_TASKS_ON_PAGE;
    }

    public static int getUserCurrentPage(Integer id) {
        return currentPage.get(id) == null ? 0 : currentPage.get(id);
    }

    public static void setUserCurrentPage(Integer id, int pageNumber) {
        currentPage.put(id, pageNumber);
    }

    //Текущая страница пользователя
}
