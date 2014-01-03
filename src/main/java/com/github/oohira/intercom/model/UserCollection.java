package com.github.oohira.intercom.model;

/**
 * Class representing a collection of users.
 *
 * @author oohira
 */
public class UserCollection {
    private User[] users;
    private Integer totalCount;
    private Integer page;
    private Integer nextPage;
    private Integer previousPage;
    private Integer totalPages;

    public UserCollection() {
    }

    public User[] getUsers() {
        return this.users;
    }

    public Integer getTotalCount() {
        return this.totalCount;
    }

    public Integer getPage() {
        return this.page;
    }

    public Integer getNextPage() {
        return this.nextPage;
    }

    public Integer getPreviousPage() {
        return this.previousPage;
    }

    public Integer getTotalPages() {
        return this.totalPages;
    }
}
