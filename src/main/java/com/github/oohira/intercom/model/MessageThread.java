package com.github.oohira.intercom.model;

import java.util.Date;

/**
 * Class representing a message thread.
 *
 * @author oohira
 */
public class MessageThread {
    private String userId;
    private String email;
    private Date createdAt;
    private Date updatedAt;
    private Boolean read;
    private Boolean createdByUser;
    private Integer threadId;
    private Integer messageId;
    private String messageType;
    private Boolean interrupt;
    private Message[] messages;
    private String body;
    private String url;

    public MessageThread() {
    }

    public void setUserId(final String userId) {
        this.userId = userId;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public Date getUpdatedAt() {
        return this.updatedAt;
    }

    public Boolean isRead() {
        return this.read;
    }

    public void isRead(final Boolean isRead) {
        this.read = isRead;
    }

    public Boolean isCreatedByUser() {
        return this.createdByUser;
    }

    public Integer getThreadId() {
        return this.threadId;
    }

    public void setThreadId(final Integer threadId) {
        this.threadId = threadId;
    }

    public Integer getMessageId() {
        return this.messageId;
    }

    public String getMessageType() {
        return this.messageType;
    }

    public Boolean isInterrupt() {
        return this.interrupt;
    }

    public Message[] getMessages() {
        return this.messages;
    }

    public void setBody(final String body) {
        this.body = body;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public String getUrl() {
        return this.url;
    }
}
