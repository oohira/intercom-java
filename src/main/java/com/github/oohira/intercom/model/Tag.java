package com.github.oohira.intercom.model;

/**
 * Class representing a tag.
 *
 * @author oohira
 */
public class Tag {
    private String id;
    private String name;
    private Boolean segment;
    private Integer taggedUserCount;
    private String[] userIds;
    private String[] emails;
    private String tagOrUntag;

    public Tag() {
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Boolean isSegment() {
        return this.segment;
    }

    public Integer getTaggedUserCount() {
        return this.taggedUserCount;
    }

    public void setUserIds(final String[] userIds) {
        this.userIds = userIds;
    }

    public void setEmails(final String[] emails) {
        this.emails = emails;
    }

    public void setTagOrUntag(final String tagOrUntag) {
        this.tagOrUntag = tagOrUntag;
    }
}
