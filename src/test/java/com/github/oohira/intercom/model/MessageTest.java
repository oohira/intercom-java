package com.github.oohira.intercom.model;

import com.github.oohira.intercom.Intercom;
import org.junit.Test;

import java.util.Date;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

/**
 * Test class of {@link Message}.
 */
public class MessageTest {

    @Test
    public void deserializeNull() {
        Intercom intercom = new Intercom();
        String json = "";
        Message message = intercom.deserialize(json, Message.class);
        assertThat(message, is(nullValue()));
    }

    @Test
    public void deserializeEmpty() {
        Intercom intercom = new Intercom();
        String json = "{}";
        Message message = intercom.deserialize(json, Message.class);
        assertThat(message.getCreatedAt(), is(nullValue()));
        assertThat(message.getUrl(), is(nullValue()));
        assertThat(message.getHtml(), is(nullValue()));
        assertThat(message.getSubject(), is(nullValue()));
        assertThat(message.getFrom(), is(nullValue()));
    }

    @Test
    public void deserialize() {
        Intercom intercom = new Intercom();
        String json = "{" +
                "\"created_at\": 1379019594," +
                "\"url\": null," +
                "\"html\": \"<p>Hey Intercom, What is up?</p>\\n\\n<p></p>\"," +
                "\"subject\": \"\"," +
                "\"from\": {" +
                "  \"user_id\": \"abc123\"," +
                "  \"email\": \"john.doe@example.com\"," +
                "  \"name\": \"John Doe\"," +
                "  \"is_admin\": false," +
                "  \"avatar\": {" +
                "  }" +
                "}" +
                "}";
        Message message = intercom.deserialize(json, Message.class);
        assertThat(message.getCreatedAt(), is(new Date(1379019594L * 1000)));
        assertThat(message.getUrl(), is(nullValue()));
        assertThat(message.getHtml(), is("<p>Hey Intercom, What is up?</p>\n\n<p></p>"));
        assertThat(message.getSubject(), is(""));

        MessageAuthor author = message.getFrom();
        assertThat(author.getUserId(), is("abc123"));
        assertThat(author.getEmail(), is("john.doe@example.com"));
        assertThat(author.getName(), is("John Doe"));
        assertThat(author.isAdmin(), is(false));
    }
}
