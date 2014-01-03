package com.github.oohira.intercom.model;

/**
 * Class representing a social profile of {@link User}.
 *
 * @author oohira
 */
public class SocialProfile {
    private String type;
    private String url;
    private String username;
    private String id;
    private Integer followerCount;

    public SocialProfile() {
    }

    public String getType() {
        return this.type;
    }

    public String getUrl() {
        return this.url;
    }

    public String getUsername() {
        return this.username;
    }

    public String getId() {
        return this.id;
    }

    public Integer getFollowerCount() {
        return this.followerCount;
    }
}
