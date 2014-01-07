package com.github.oohira.intercom.model;

import com.github.oohira.intercom.Intercom;
import org.junit.Test;

import java.util.Date;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

/**
 * Test class of {@link Impression}.
 */
public class ImpressionTest {

    @Test
    public void deserializeNull() {
        Intercom intercom = new Intercom();
        String json = "";
        Impression impression = intercom.deserialize(json, Impression.class);
        assertThat(impression, is(nullValue()));
    }

    @Test
    public void deserializeEmpty() {
        Intercom intercom = new Intercom();
        String json = "{}";
        Impression impression = intercom.deserialize(json, Impression.class);
        assertThat(impression.getUnreadMessages(), is(nullValue()));
    }

    @Test
    public void deserialize() {
        Intercom intercom = new Intercom();
        String json = "{" +
                "\"unread_messages\": 1" +
                "}";
        Impression impression = intercom.deserialize(json, Impression.class);
        assertThat(impression.getUnreadMessages(), is(1));
    }

    @Test
    public void serializeNull() {
        Intercom intercom = new Intercom();
        Impression impression = null;
        String json = intercom.serialize(impression);
        assertThat(json, is("null"));
    }

    @Test
    public void serializeEmpty() {
        Intercom intercom = new Intercom();
        Impression impression = new Impression();
        String json = intercom.serialize(impression);
        assertThat(json, is("{}"));
    }

    @Test
    public void serialize() {
        Intercom intercom = new Intercom();
        Impression impression = new Impression();
        impression.setUserId("abc123");
        impression.setEmail("john.doe@example.com");
        impression.setUserIp("127.0.0.1");
        impression.setUserAgent("Mozilla/5.0");
        impression.setCurrentUrl("http://localhost/example/");
        String json = intercom.serialize(impression);
        assertThat(json, is("{" +
                "\"user_id\":\"abc123\"," +
                "\"email\":\"john.doe@example.com\"," +
                "\"user_ip\":\"127.0.0.1\"," +
                "\"user_agent\":\"Mozilla/5.0\"," +
                "\"current_url\":\"http://localhost/example/\"" +
                "}"));
    }
}
