package com.github.oohira.intercom.model;

import java.util.Date;

/**
 * Class representing a message.
 *
 * @author oohira
 */
public class Message {
    private Date createdAt;
    private String url;
    private String html;
    private String subject;
    private MessageAuthor from;

    public Message() {
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public String getUrl() {
        return this.url;
    }

    public String getHtml() {
        return this.html;
    }

    public String getSubject() {
        return this.subject;
    }

    public MessageAuthor getFrom() {
        return this.from;
    }
}
