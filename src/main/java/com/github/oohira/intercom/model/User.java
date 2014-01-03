package com.github.oohira.intercom.model;

import java.util.Date;
import java.util.Map;

/**
 * Class representing a user.
 *
 * @author oohira
 */
public class User {
    private String intercomId;
    private String email;
    private String userId;
    private String name;
    private Date createdAt;
    private Date lastImpressionAt;
    private Map<String, Object> customData;
    private SocialProfile[] socialProfiles;
    private LocationData locationData;
    private Long sessionCount;
    private String lastSeenIp;
    private String lastSeenUserAgent;
    private String avatarUrl;
    private Boolean unsubscribedFromEmails;
    private String[] companyIds;
    private Company[] companies;

    public User() {
    }

    public String getIntercomId() {
        return this.intercomId;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(final String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(final Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getLastImpressionAt() {
        return this.lastImpressionAt;
    }

    public void setLastImpressionAt(final Date lastImpressionAt) {
        this.lastImpressionAt = lastImpressionAt;
    }

    public Map<String, Object> getCustomData() {
        return this.customData;
    }

    public void setCustomData(final Map<String, Object> customData) {
        this.customData = customData;
    }

    public SocialProfile[] getSocialProfiles() {
        return this.socialProfiles;
    }

    public LocationData getLocationData() {
        return this.locationData;
    }

    public Long getSessionCount() {
        return this.sessionCount;
    }

    public String getLastSeenIp() {
        return this.lastSeenIp;
    }

    public void setLastSeenIp(final String lastSeenIp) {
        this.lastSeenIp = lastSeenIp;
    }

    public String getLastSeenUserAgent() {
        return this.lastSeenUserAgent;
    }

    public void setLastSeenUserAgent(final String lastSeenUserAgent) {
        this.lastSeenUserAgent = lastSeenUserAgent;
    }

    public String getAvatarUrl() {
        return this.avatarUrl;
    }

    public Boolean isUnsubscribedFromEmails() {
        return this.unsubscribedFromEmails;
    }

    public String[] getCompanyIds() {
        return this.companyIds;
    }

    public void setCompanies(final Company[] companies) {
        this.companies = companies;
    }
}
