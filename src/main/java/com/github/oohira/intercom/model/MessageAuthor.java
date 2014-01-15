package com.github.oohira.intercom.model;

/**
 * Class representing an author of message.
 *
 * @author oohira
 */
public class MessageAuthor {
    private String userId;
    private String email;
    private String name;
    private Boolean isAdmin;

    public MessageAuthor() {
    }

    public String getUserId() {
        return this.userId;
    }

    public String getEmail() {
        return this.email;
    }

    public String getName() {
        return this.name;
    }

    public Boolean isAdmin() {
        return this.isAdmin;
    }
}
