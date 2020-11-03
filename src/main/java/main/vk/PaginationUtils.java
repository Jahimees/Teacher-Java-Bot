package main.vk;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class PaginationUtils {

    public static Map<Integer, Integer> currentTaskPage = new HashMap<>();
    public static Map<Integer, Integer> currentCoursePage = new HashMap<>();
    public static Map<Integer, Integer> currentBoughtCoursePage = new HashMap<>();
    public static String currentEntity = "undefined";
    public static final int COUNT_ENTITIES_ON_PAGE = 3;

    private PaginationUtils() {
    }

    public static int   calculatePageCount(List entityList) {
        if (entityList.size() == 0) {
            return 0;
        }

        int fullPage;
        fullPage = entityList.size() / COUNT_ENTITIES_ON_PAGE;

        if (fullPage == 0) {
            return 1;
        }

        return fullPage + (entityList.size() % COUNT_ENTITIES_ON_PAGE > 0 ? 1 : 0);
    }

    public static int getUserCurrentTaskPage(Integer id) {
        return currentTaskPage.get(id) == null ? 0 : currentTaskPage.get(id);
    }

    public static void setUserCurrentTaskPage(Integer id, int pageNumber) {
        currentTaskPage.put(id, pageNumber);
    }

    public static int getUserCurrentCoursePage(Integer id) {
        return currentCoursePage.get(id) == null ? 0 : currentCoursePage.get(id);
    }

    public static void setUserCurrentCoursePage(Integer id, int pageNumber) {
        currentCoursePage.put(id, pageNumber);
    }

    public static int getUserCurrentBoughtCoursePage(Integer id) {
        return currentBoughtCoursePage.get(id) == null ? 0 : currentBoughtCoursePage.get(id);
    }

    public static void setUserCurrentBoughtCoursePage(Integer id, int pageNumber) {
        currentBoughtCoursePage.put(id, pageNumber);
    }
    //Текущая страница пользователя
}
