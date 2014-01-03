package com.github.oohira.intercom.model;

import com.github.oohira.intercom.Intercom;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

/**
 * Test class of {@link ErrorResponse}.
 */
public class ErrorResponseTest {

    @Test
    public void deserialize() {
        Intercom intercom = new Intercom();
        String json = "{\"error\":{\"type\":\"not_found\",\"message\":\"The user was not found\"}}";
        ErrorResponse error = intercom.deserialize(json, ErrorResponse.class);
        error.setStatusCode(404);
        assertThat(error.getType(), is("not_found"));
        assertThat(error.getMessage(), is("The user was not found"));
        assertThat(error.getStatusCode(), is(404));
    }
}
