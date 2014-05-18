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
    public void deserializeSingleError() {
        Intercom intercom = new Intercom();
        String json = "{\"error\":{\"type\":\"not_found\",\"message\":\"The user was not found\"}}";
        ErrorResponse error = intercom.deserialize(json, ErrorResponse.class);
        error.setStatusCode(404);

        assertThat(error.isErrorList(), is(false));
        assertThat(error.getType(), is("not_found"));
        assertThat(error.getMessage(), is("The user was not found"));
        assertThat(error.getError().getType(), is("not_found"));
        assertThat(error.getError().getCode(), is(nullValue()));
        assertThat(error.getError().getMessage(), is("The user was not found"));
        assertThat(error.getStatusCode(), is(404));

        try {
            error.getErrors();
            fail();
        } catch (UnsupportedOperationException expected) {
        }
    }

    @Test
    public void deserializeErrorList() {
        Intercom intercom = new Intercom();
        String json = "{\"type\":\"error.list\",\"errors\":[{\"code\":\"not_found\",\"message\":\"User Not Found\"}]}";
        ErrorResponse error = intercom.deserialize(json, ErrorResponse.class);
        error.setStatusCode(404);

        assertThat(error.isErrorList(), is(true));
        assertThat(error.getType(), is("not_found"));
        assertThat(error.getMessage(), is("User Not Found"));
        assertThat(error.getErrors().size(), is(1));
        assertThat(error.getErrors().get(0).getType(), is(nullValue()));
        assertThat(error.getErrors().get(0).getCode(), is("not_found"));
        assertThat(error.getErrors().get(0).getMessage(), is("User Not Found"));
        assertThat(error.getStatusCode(), is(404));

        try {
            error.getError();
            fail();
        } catch (UnsupportedOperationException expected) {
        }
    }
}
