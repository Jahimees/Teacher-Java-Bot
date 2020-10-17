package main.vk;

import main.model.entity.Task;

import java.util.List;

public final class Utils {

    private Utils() {
    }

    public static int calculatePageCount(List<Task> taskList) {
        if (taskList.size() == 0) {
            return 0;
        }

        int fullPage;
        fullPage = taskList.size() / 3;

        if (fullPage == 0) {
            return 1;
        }

        return fullPage + taskList.size() % 3;
    }

    //Текущая страница пользователя
}
