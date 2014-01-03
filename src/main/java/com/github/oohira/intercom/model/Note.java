package com.github.oohira.intercom.model;

import java.util.Date;

/**
 * Class representing a note.
 *
 * @author oohira
 */
public class Note {
    private String userId;
    private String email;
    private String body;
    private String html;
    private Date createdAt;
    private User user;

    public Note() {
    }

    public void setUserId(final String userId) {
        this.userId = userId;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public void setBody(final String body) {
        this.body = body;
    }

    public String getHtml() {
        return this.html;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public User getUser() {
        return this.user;
    }
}
