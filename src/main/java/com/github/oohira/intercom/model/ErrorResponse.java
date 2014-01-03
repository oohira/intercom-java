package com.github.oohira.intercom.model;

import java.util.Map;

/**
 * Class representing an error response.
 *
 * @author oohira
 */
public class ErrorResponse {
    private Map<String, String> error;
    private transient int statusCode;

    public ErrorResponse() {
    }

    public String getType() {
        if (this.error == null) {
            return null;
        }
        return this.error.get("type");
    }

    public String getMessage() {
        if (this.error == null) {
            return null;
        }
        return this.error.get("message");
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public void setStatusCode(final int statusCode) {
        this.statusCode = statusCode;
    }
}
