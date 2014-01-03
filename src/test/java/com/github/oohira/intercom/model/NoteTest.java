package com.github.oohira.intercom.model;

import com.github.oohira.intercom.Intercom;
import org.junit.Test;

import java.util.Date;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

/**
 * Test class of {@link Note}.
 */
public class NoteTest {

    @Test
    public void deserializeNull() {
        Intercom intercom = new Intercom();
        String json = "";
        Note note = intercom.deserialize(json, Note.class);
        assertThat(note, is(nullValue()));
    }

    @Test
    public void deserializeEmpty() {
        Intercom intercom = new Intercom();
        String json = "{}";
        Note note = intercom.deserialize(json, Note.class);
        assertThat(note.getHtml(), is(nullValue()));
        assertThat(note.getCreatedAt(), is(nullValue()));
        assertThat(note.getUser(), is(nullValue()));
    }

    @Test
    public void deserialize() {
        Intercom intercom = new Intercom();
        String json = "{" +
                "\"html\":\"<p>This is the text of my note.</p>\"," +
                "\"created_at\":1300000000," +
                "\"user\":{" +
                "    \"intercom_id\":\"52b84c49633802537c00648e\"," +
                "    \"email\":\"john.doe@example.com\"" +
                "}" +
                "}";
        Note note = intercom.deserialize(json, Note.class);
        assertThat(note.getHtml(), is("<p>This is the text of my note.</p>"));
        assertThat(note.getCreatedAt(), is(new Date(1300000000L * 1000)));
        assertThat(note.getUser().getEmail(), is("john.doe@example.com"));
    }

    @Test
    public void serializeNull() {
        Intercom intercom = new Intercom();
        Note note = null;
        String json = intercom.serialize(note);
        assertThat(json, is("null"));
    }

    @Test
    public void serializeEmpty() {
        Intercom intercom = new Intercom();
        Note note = new Note();
        String json = intercom.serialize(note);
        assertThat(json, is("{}"));
    }

    @Test
    public void serialize() {
        Intercom intercom = new Intercom();
        Note note = new Note();
        note.setUserId("abc123");
        note.setBody("This is the text of my note.");
        String json = intercom.serialize(note);
        assertThat(json, is("{" +
                "\"user_id\":\"abc123\"," +
                "\"body\":\"This is the text of my note.\"" +
                "}"));
    }
}
