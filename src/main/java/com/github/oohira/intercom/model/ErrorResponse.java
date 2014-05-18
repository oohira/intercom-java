package com.github.oohira.intercom.model;

import java.util.List;

/**
 * Class representing an error response.
 *
 * @author oohira
 */
public class ErrorResponse {
    /**
     * Class representing an error.
     */
    public static class Error {
        private String type; // initialized only when a single error
        private String code; // initialized only when an error list
        private String message;

        /**
         * Gets a type of this error.
         *
         * NOTE:
         * If this is an Events API error, this method will return null.
         * In that case, use {@link #getCode()} instead. This confusing
         * behavior is due to strange specification of Intercom Events API.
         *
         * @return an error type
         */
        public String getType() {
            return this.type;
        }

        /**
         * Gets a kind of this error.
         *
         * NOTE:
         * If this is not an Events API error, this method will return null.
         * In that case, use {@link #getType()} instead. This confusing
         * behavior is due to strange specification of Intercom Events API.
         *
         * @return an error type
         */
        public String getCode() {
            return this.code;
        }

        public String getMessage() {
            return this.message;
        }
    }

    private static final String TYPE_ERROR_LIST = "error.list";
    private String type;
    private Error error;
    private List<Error> errors;
    private transient int statusCode;

    public ErrorResponse() {
    }

    public boolean isErrorList() {
        return TYPE_ERROR_LIST.equals(this.type);
    }

    public Error getError() {
        if (isErrorList()) {
            throw new UnsupportedOperationException("This is an Error List object. Use getErrors() instead.");
        }
        return this.error;
    }

    public List<Error> getErrors() {
        if (!isErrorList()) {
            throw new UnsupportedOperationException("This is a single Error object. Use getError() instead.");
        }
        return this.errors;
    }

    /**
     * Gets a type of this error.
     *
     * @return If this is a single error, returns type of the error.
     *         If this is an error list, returns type of the first error.
     */
    public String getType() {
        if (isErrorList()) {
            if (this.errors != null && !this.errors.isEmpty()) {
                return this.errors.get(0).getCode();
            }
        } else {
            if (this.error != null) {
                return this.error.getType();
            }
        }
        return null;
    }

    /**
     * Gets a human readable description of this error.
     *
     * @return If this is a single error, returns description of the error.
     *         If this is an error list, returns description of the first error.
     */
    public String getMessage() {
        if (isErrorList()) {
            if (this.errors != null && !this.errors.isEmpty()) {
                return this.errors.get(0).getMessage();
            }
        } else {
            if (this.error != null) {
                return this.error.getMessage();
            }
        }
        return null;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public void setStatusCode(final int statusCode) {
        this.statusCode = statusCode;
    }
}
