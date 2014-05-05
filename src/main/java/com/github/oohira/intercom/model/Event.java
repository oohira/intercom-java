package com.github.oohira.intercom.model;

import java.util.Date;
import java.util.Map;

/**
 * Class representing an event.
 *
 * @author oohira
 */
public class Event {
    private String eventName;
    private String userId;
    private String email;
    private Date created;
    private Map<String, Object> metadata;

    public String getEventName() {
        return this.eventName;
    }

    public void setEventName(final String eventName) {
        this.eventName = eventName;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(final String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public Date getCreatedAt() {
        return this.created;
    }

    public void setCreatedAt(final Date createdAt) {
        this.created = createdAt;
    }

    public Map<String, Object> getMetadata() {
        return this.metadata;
    }

    public void setMetadata(final Map<String, Object> metadata) {
        this.metadata = metadata;
    }
}
