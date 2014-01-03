package com.github.oohira.intercom.model;

import com.github.oohira.intercom.Intercom;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

/**
 * Test class of {@link UserCollection}.
 */
public class UserCollectionTest {

    @Test
    public void deserializeNull() {
        Intercom intercom = new Intercom();
        String json = "";
        UserCollection users = intercom.deserialize(json, UserCollection.class);
        assertThat(users, is(nullValue()));
    }

    @Test
    public void deserializeEmpty() {
        Intercom intercom = new Intercom();
        String json = "{}";
        UserCollection users = intercom.deserialize(json, UserCollection.class);
        assertThat(users.getUsers(), is(nullValue()));
        assertThat(users.getTotalCount(), is(nullValue()));
        assertThat(users.getPage(), is(nullValue()));
        assertThat(users.getNextPage(), is(nullValue()));
        assertThat(users.getPreviousPage(), is(nullValue()));
        assertThat(users.getTotalPages(), is(nullValue()));
    }

    @Test
    public void deserialize() {
        Intercom intercom = new Intercom();
        String json = "{" +
                "\"users\":[" +
                "{" +
                "    \"intercom_id\":\"52322b366823b173eb000170\"," +
                "    \"email\":\"john.doe@example.com\"" +
                "}," +
                "{" +
                "    \"intercom_id\":\"52b84c49633802537c00648e\"," +
                "    \"email\":\"jane.doe@example.com\"" +
                "}" +
                "]," +
                "\"total_count\": 2,\n" +
                "\"page\": 1,\n" +
                "\"next_page\": null,\n" +
                "\"previous_page\": null,\n" +
                "\"total_pages\": 1\n" +
                "}";
        UserCollection users = intercom.deserialize(json, UserCollection.class);
        assertThat(users.getUsers().length, is(2));
        assertThat(users.getUsers()[0].getEmail(), is("john.doe@example.com"));
        assertThat(users.getUsers()[1].getEmail(), is("jane.doe@example.com"));
        assertThat(users.getTotalCount(), is(2));
        assertThat(users.getPage(), is(1));
        assertThat(users.getNextPage(), is(nullValue()));
        assertThat(users.getPreviousPage(), is(nullValue()));
        assertThat(users.getTotalPages(), is(1));
    }
}
