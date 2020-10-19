package main.vk.command;

import main.vk.PaginationUtils;

public class NextPageCommand extends TaskCommand {

    @Override
    protected void doSetCurrentPage(int userId) {
        int currentPage = PaginationUtils.getUserCurrentPage(userId);
        PaginationUtils.setUserCurrentPage(userId, currentPage + 1);
    }

}
