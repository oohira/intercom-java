package com.github.oohira.intercom.model;

import com.github.oohira.intercom.Intercom;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * Test class of {@link MessageThread}.
 */
public class MessageThreadTest {

    @Test
    public void deserializeNull() {
        Intercom intercom = new Intercom();
        String json = "";
        MessageThread thread = intercom.deserialize(json, MessageThread.class);
        assertThat(thread, is(nullValue()));
    }

    @Test
    public void deserializeEmpty() {
        Intercom intercom = new Intercom();
        String json = "{}";
        MessageThread thread = intercom.deserialize(json, MessageThread.class);
        assertThat(thread.getCreatedAt(), is(nullValue()));
        assertThat(thread.getUpdatedAt(), is(nullValue()));
        assertThat(thread.isRead(), is(nullValue()));
        assertThat(thread.isCreatedByUser(), is(nullValue()));
        assertThat(thread.getThreadId(), is(nullValue()));
        assertThat(thread.getMessageId(), is(nullValue()));
        assertThat(thread.getMessageType(), is(nullValue()));
        assertThat(thread.isInterrupt(), is(nullValue()));
        assertThat(thread.getMessages(), is(nullValue()));
    }

    @Test
    public void deserialize() {
        Intercom intercom = new Intercom();
        String json = "{" +
                "\"created_at\": 1379019594," +
                "\"updated_at\": 1378933195," +
                "\"read\": true," +
                "\"created_by_user\": true," +
                "\"thread_id\": 5591," +
                "\"message_id\": 5591," +
                "\"message_type\": \"conversation\"," +
                "\"interrupt\": true," +
                "\"messages\": [" +
                "  {" +
                "    \"created_at\": 1379019594," +
                "    \"url\": null," +
                "    \"html\": \"<p>Hey Intercom, What is up?</p>\n\n<p></p>\"," +
                "    \"subject\": \"\"," +
                "    \"from\": {" +
                "        \"email\": \"john.doe@example.com\"," +
                "        \"user_id\": \"abc123\"," +
                "        \"name\": \"John Doe\"," +
                "        \"is_admin\": false," +
                "        \"avatar\": {" +
                "        }" +
                "    }" +
                "  }," +
                "  {" +
                "    \"created_at\": 1378846794," +
                "    \"url\": null," +
                "    \"html\": \"<p>Not much, you?\n</p>\"" +
                "  }," +
                "  {" +
                "    \"created_at\": 1378933195," +
                "    \"url\": null," +
                "    \"html\": \"<p>Not much either :(</p>\n\n<p></p>\"," +
                "    \"from\": {" +
                "      \"email\": \"Jane.doe@example.com\"," +
                "      \"user_id\": \"def456\"," +
                "      \"name\": \"Jane Doe\"," +
                "      \"is_admin\": false," +
                "      \"avatar\": {" +
                "      }" +
                "    }" +
                "  }" +
                "]" +
                "}";
        MessageThread thread = intercom.deserialize(json, MessageThread.class);
        assertThat(thread.getCreatedAt(), is(new Date(1379019594 * 1000L)));
        assertThat(thread.getUpdatedAt(), is(new Date(1378933195 * 1000L)));
        assertThat(thread.isRead(), is(true));
        assertThat(thread.isCreatedByUser(), is(true));
        assertThat(thread.getThreadId(), is(5591));
        assertThat(thread.getMessageId(), is(5591));
        assertThat(thread.getMessageType(), is("conversation"));
        assertThat(thread.isInterrupt(), is(true));

        Message[] messages = thread.getMessages();
        assertThat(messages.length, is(3));
        assertThat(messages[0].getCreatedAt(), is(new Date(1379019594 * 1000L)));
        assertThat(messages[0].getHtml(), is("<p>Hey Intercom, What is up?</p>\n\n<p></p>"));
        assertThat(messages[1].getCreatedAt(), is(new Date(1378846794 * 1000L)));
        assertThat(messages[1].getHtml(), is("<p>Not much, you?\n</p>"));
        assertThat(messages[2].getCreatedAt(), is(new Date(1378933195 * 1000L)));
        assertThat(messages[2].getHtml(), is("<p>Not much either :(</p>\n\n<p></p>"));
    }

    @Test
    public void serializeNull() {
        Intercom intercom = new Intercom();
        MessageThread thread = null;
        String json = intercom.serialize(thread);
        assertThat(json, is("null"));
    }

    @Test
    public void serializeEmpty() {
        Intercom intercom = new Intercom();
        MessageThread thread = new MessageThread();
        String json = intercom.serialize(thread);
        assertThat(json, is("{}"));
    }

    @Test
    public void serialize() {
        Intercom intercom = new Intercom();
        MessageThread thread = new MessageThread();
        thread.setEmail("john.doe@example.com");
        thread.setBody("Not much either :(");
        thread.setThreadId(11547);
        thread.setUrl("http://example.com/my/sweet/url");
        String json = intercom.serialize(thread);
        assertThat(json, is("{" +
                "\"email\":\"john.doe@example.com\"," +
                "\"thread_id\":11547," +
                "\"body\":\"Not much either :(\"," +
                "\"url\":\"http://example.com/my/sweet/url\"" +
                "}"));
    }
}
