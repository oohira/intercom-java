package com.github.oohira.intercom.model;

import java.util.Date;
import java.util.Map;

/**
 * Class representing a company of {@link User}.
 *
 * @author oohira
 */
public class Company {
    private String id;
    private String name;
    private Date createdAt;
    private String plan;
    private Double monthlySpend;
    private Map<String, Object> customData;

    public Company() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(final Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getPlan() {
        return this.plan;
    }

    public void setPlan(final String plan) {
        this.plan = plan;
    }

    public Double getMonthlySpend() {
        return this.monthlySpend;
    }

    public void setMonthlySpend(final Double monthlySpend) {
        this.monthlySpend = monthlySpend;
    }

    public Map<String, Object> getCustomData() {
        return this.customData;
    }

    public void setCustomData(final Map<String, Object> customData) {
        this.customData = customData;
    }
}
