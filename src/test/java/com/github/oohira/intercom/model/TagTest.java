package com.github.oohira.intercom.model;

import com.github.oohira.intercom.Intercom;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

/**
 * Test class of {@link Tag}.
 */
public class TagTest {

    @Test
    public void deserializeNull() {
        Intercom intercom = new Intercom();
        String json = "";
        Tag tag = intercom.deserialize(json, Tag.class);
        assertThat(tag, is(nullValue()));
    }

    @Test
    public void deserializeEmpty() {
        Intercom intercom = new Intercom();
        String json = "{}";
        Tag tag = intercom.deserialize(json, Tag.class);
        assertThat(tag.getId(), is(nullValue()));
        assertThat(tag.getName(), is(nullValue()));
        assertThat(tag.isSegment(), is(nullValue()));
        assertThat(tag.getTaggedUserCount(), is(nullValue()));
    }

    @Test
    public void deserialize() {
        Intercom intercom = new Intercom();
        String json = "{\n" +
                "  \"id\": \"52322b4150233908a800013c\",\n" +
                "  \"name\": \"Free Trial\",\n" +
                "  \"segment\": false,\n" +
                "  \"tagged_user_count\": 2\n" +
                "}";
        Tag tag = intercom.deserialize(json, Tag.class);
        assertThat(tag.getId(), is("52322b4150233908a800013c"));
        assertThat(tag.getName(), is("Free Trial"));
        assertThat(tag.isSegment(), is(false));
        assertThat(tag.getTaggedUserCount(), is(2));
    }

    @Test
    public void serializeNull() {
        Intercom intercom = new Intercom();
        Tag tag = null;
        String json = intercom.serialize(tag);
        assertThat(json, is("null"));
    }

    @Test
    public void serializeEmpty() {
        Intercom intercom = new Intercom();
        Tag tag = new Tag();
        String json = intercom.serialize(tag);
        assertThat(json, is("{}"));
    }

    @Test
    public void serialize() {
        Intercom intercom = new Intercom();
        Tag tag = new Tag();
        tag.setName("Free Trial");
        tag.setUserIds(new String[]{"abc123", "def456"});
        tag.setTagOrUntag("tag");
        String json = intercom.serialize(tag);
        assertThat(json, is("{" +
                "\"name\":\"Free Trial\"," +
                "\"user_ids\":[\"abc123\",\"def456\"]," +
                "\"tag_or_untag\":\"tag\"" +
                "}"));
    }
}
