package com.github.oohira.intercom.model;

/**
 * Class representing an impression.
 *
 * @author oohira
 */
public class Impression {
    private String userId;
    private String email;
    private String userIp;
    private String userAgent;
    private String currentUrl;
    private Integer unreadMessages;

    public Impression() {
    }

    public void setUserId(final String userId) {
        this.userId = userId;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public void setUserIp(final String userIp) {
        this.userIp = userIp;
    }

    public void setUserAgent(final String userAgent) {
        this.userAgent = userAgent;
    }

    public void setCurrentUrl(final String currentUrl) {
        this.currentUrl = currentUrl;
    }

    public Integer getUnreadMessages() {
        return this.unreadMessages;
    }
}
