package main.vk.command;

import main.vk.PaginationUtils;

import static main.vk.PaginationUtils.setUserCurrentPage;

public class PreviousPageCommand extends TaskCommand {

    @Override
    protected void doSetCurrentPage(int userId) {
        int currentPage = PaginationUtils.getUserCurrentPage(userId);
        setUserCurrentPage(userId, currentPage - 1);
    }
}
