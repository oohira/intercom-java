package com.github.oohira.intercom;

import com.github.oohira.intercom.model.ErrorResponse;

/**
 * This exception is raised if there is a issue during calling Intercom APIs.
 *
 * @author oohira
 */
public class IntercomException extends RuntimeException {
    private ErrorResponse error;

    public IntercomException(final String message) {
        super(message);
    }

    public IntercomException(final ErrorResponse error) {
        super(error.getType() + ": " + error.getMessage());
        this.error = error;
    }

    public IntercomException(final Throwable cause) {
        super(cause);
    }

    /**
     * Get an error response object from Intercom.
     *
     * @return an error response object. (may be null)
     * @see <a href="https://api.intercom.io/docs#response_codes_and_errors">
     *     Intercom API Documentation: Response Codes & Errors</a>
     */
    public ErrorResponse getErrorResponse() {
        return this.error;
    }
}
