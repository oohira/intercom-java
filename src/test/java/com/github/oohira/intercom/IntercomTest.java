package com.github.oohira.intercom;

import com.github.oohira.intercom.model.Company;
import com.github.oohira.intercom.model.Note;
import com.github.oohira.intercom.model.Tag;
import com.github.oohira.intercom.model.User;
import com.github.oohira.intercom.model.UserCollection;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

/**
 * Test class of {@link Intercom}.
 */
@Ignore("APP_ID and API_KEY are required.")
public class IntercomTest {
    private static final String APP_ID = "";
    private static final String API_KEY = "";
    private Intercom intercom;

    @Before
    public void setUp() {
        this.intercom = new Intercom(APP_ID, API_KEY);

        this.intercom.clearUsers();
        for (int i = 1; i <= 3; i++) {
            User user = new User();
            user.setUserId("user" +i);
            user.setEmail("user" + i + "@example.com");
            user.setName("User " + i);
            user.setCreatedAt(new Date(1270000000L * 1000));
            Map<String, Object> customData = new HashMap<String, Object>();
            customData.put("custom_data_1", "test");
            customData.put("custom_data_2", 7);
            user.setCustomData(customData);
            user.setLastSeenIp("192.168.0." + i);
            user.setLastSeenUserAgent("Intercom Java User Agent");
            user.setLastImpressionAt(new Date(1300000000L * 1000));
            Company company = new Company();
            company.setId("company" + i);
            company.setName("Company " + i);
            user.setCompanies(new Company[]{company});
            this.intercom.createUser(user);
        }
        {
            Tag tag = new Tag();
            tag.setName("Tag1");
            tag.setUserIds(new String[]{"user1", "user2"});
            tag.setTagOrUntag("tag");
            this.intercom.createTag(tag);
        }
        {
            Tag tag = new Tag();
            tag.setName("Tag2");
            tag.setUserIds(new String[]{"user1"});
            tag.setTagOrUntag("tag");
            this.intercom.createTag(tag);
        }
    }

    @Test
    public void constructor() {
        Intercom intercom = new Intercom("appId", "apiKey");
        assertThat(intercom.getAppId(), is("appId"));
        assertThat(intercom.getApiKey(), is("apiKey"));
    }

    @Test
    public void setAppId() {
        Intercom intercom = new Intercom();
        intercom.setAppId("appId");
        assertThat(intercom.getAppId(), is("appId"));
    }

    @Test
    public void setApiKey() {
        Intercom intercom = new Intercom();
        intercom.setApiKey("apiKey");
        assertThat(intercom.getApiKey(), is("apiKey"));
    }

    @Test
    public void getUsers() {
        for (Integer page : new Integer[]{null, 1}) {
            this.intercom.setUserCountPerPage(page);
            Set<String> userIds = new HashSet<String>();
            Iterator<User> it = this.intercom.getUsers();
            userIds.add(it.next().getUserId());
            userIds.add(it.next().getUserId());
            userIds.add(it.next().getUserId());
            assertThat(it.hasNext(), is(false));
            assertThat(userIds.contains("user1"), is(true));
            assertThat(userIds.contains("user2"), is(true));
            assertThat(userIds.contains("user3"), is(true));
        }
    }

    @Test
    public void getUsersByTagName() {
        {
            Set<String> userIds = new HashSet<String>();
            Iterator<User> it = this.intercom.getUsersByTagName("Tag1");
            userIds.add(it.next().getUserId());
            userIds.add(it.next().getUserId());
            assertThat(it.hasNext(), is(false));
            assertThat(userIds.contains("user1"), is(true));
            assertThat(userIds.contains("user2"), is(true));
        }
        {
            Iterator<User> it = this.intercom.getUsersByTagName("Tag2");
            assertThat(it.next().getUserId(), is("user1"));
            assertThat(it.hasNext(), is(false));
        }
    }

    @Test
    public void getUserCollection() {
        UserCollection collection = this.intercom.getUsers(1, null);
        Set<String> userIds = new HashSet<String>();
        userIds.add(collection.getUsers()[0].getUserId());
        userIds.add(collection.getUsers()[1].getUserId());
        userIds.add(collection.getUsers()[2].getUserId());
        assertThat(userIds.contains("user1"), is(true));
        assertThat(userIds.contains("user2"), is(true));
        assertThat(userIds.contains("user3"), is(true));
        assertThat(collection.getTotalCount(), is(3));
        assertThat(collection.getPage(), is(1));
        assertThat(collection.getNextPage(), is(nullValue()));
        assertThat(collection.getPreviousPage(), is(nullValue()));
        assertThat(collection.getTotalPages(), is(1));
    }

    @Test
    public void getUserCollectionByTagName() {
        UserCollection collection = this.intercom.getUsers(1, "Tag2");
        assertThat(collection.getUsers()[0].getUserId(), is("user1"));
        assertThat(collection.getTotalCount(), is(1));
        assertThat(collection.getPage(), is(1));
        assertThat(collection.getNextPage(), is(nullValue()));
        assertThat(collection.getPreviousPage(), is(nullValue()));
        assertThat(collection.getTotalPages(), is(1));
    }

    @Test
    public void getUserCollectionOneUserPerPage() {
        this.intercom.setUserCountPerPage(1);
        Set<String> userIds = new HashSet<String>();
        {
            UserCollection collection = this.intercom.getUsers(1, "Tag1");
            userIds.add(collection.getUsers()[0].getUserId());
            assertThat(collection.getTotalCount(), is(2));
            assertThat(collection.getPage(), is(1));
            assertThat(collection.getNextPage(), is(2));
            assertThat(collection.getPreviousPage(), is(nullValue()));
            assertThat(collection.getTotalPages(), is(2));
        }
        {
            UserCollection collection = this.intercom.getUsers(2, "Tag1");
            userIds.add(collection.getUsers()[0].getUserId());
            assertThat(collection.getTotalCount(), is(2));
            assertThat(collection.getPage(), is(2));
            assertThat(collection.getNextPage(), is(nullValue()));
            assertThat(collection.getPreviousPage(), is(1));
            assertThat(collection.getTotalPages(), is(2));
        }
        assertThat(userIds.contains("user1"), is(true));
        assertThat(userIds.contains("user2"), is(true));
    }

    @Test
    public void getUserById() {
        User user = this.intercom.getUserById("user1");
        assertThat(user.getIntercomId(), is(not(nullValue())));
        assertThat(user.getEmail(), is("user1@example.com"));
        assertThat(user.getUserId(), is("user1"));
        assertThat(user.getName(), is("User 1"));
        assertThat(user.getCreatedAt(), is(new Date(1270000000L * 1000)));
        assertThat(user.getLastImpressionAt(), is(new Date(1300000000L * 1000)));
        assertThat((String)user.getCustomData().get("custom_data_1"), is("test"));
        assertThat((Double)user.getCustomData().get("custom_data_2"), is(7.0));
        assertThat(user.getSessionCount(), is(0L));
        assertThat(user.getLastSeenIp(), is("192.168.0.1"));
        assertThat(user.getLastSeenUserAgent(), is("Intercom Java User Agent"));
        assertThat(user.getAvatarUrl(), is(nullValue()));
        assertThat(user.isUnsubscribedFromEmails(), is(false));
    }

    @Test
    public void getUserByEmail() {
        User user = this.intercom.getUserByEmail("user2@example.com");
        assertThat(user.getIntercomId(), is(not(nullValue())));
        assertThat(user.getEmail(), is("user2@example.com"));
        assertThat(user.getUserId(), is("user2"));
        assertThat(user.getName(), is("User 2"));
        assertThat(user.getCreatedAt(), is(new Date(1270000000L * 1000)));
        assertThat(user.getLastImpressionAt(), is(new Date(1300000000L * 1000)));
        assertThat((String)user.getCustomData().get("custom_data_1"), is("test"));
        assertThat((Double)user.getCustomData().get("custom_data_2"), is(7.0));
        assertThat(user.getSessionCount(), is(0L));
        assertThat(user.getLastSeenIp(), is("192.168.0.2"));
        assertThat(user.getLastSeenUserAgent(), is("Intercom Java User Agent"));
        assertThat(user.getAvatarUrl(), is(nullValue()));
        assertThat(user.isUnsubscribedFromEmails(), is(false));
    }

    @Test
    public void createUser() {
        User user = new User();
        user.setUserId("new.user");
        user.setEmail("new.user@example.com");
        user.setName("New User");
        user.setCreatedAt(new Date(1270000000L * 1000));
        Map<String, Object> customData = new HashMap<String, Object>();
        customData.put("custom_data_1", "hoge");
        customData.put("custom_data_2", 777);
        user.setCustomData(customData);
        user.setLastSeenIp("1.2.3.4");
        user.setLastSeenUserAgent("ie6");
        user.setLastImpressionAt(new Date(1300000000L * 1000));
        Company company = new Company();
        company.setId("new.company");
        company.setName("New Company");
        user.setCompanies(new Company[]{company});

        User created = this.intercom.createUser(user);
        assertThat(created.getIntercomId(), is(not(nullValue())));
        assertThat(created.getEmail(), is("new.user@example.com"));
        assertThat(created.getUserId(), is("new.user"));
        assertThat(created.getName(), is("New User"));
        assertThat(created.getCreatedAt(), is(new Date(1270000000L * 1000)));
        assertThat(created.getLastImpressionAt(), is(new Date(1300000000L * 1000)));
        assertThat((String)created.getCustomData().get("custom_data_1"), is("hoge"));
        assertThat((Double)created.getCustomData().get("custom_data_2"), is(777.0));
        assertThat(created.getSocialProfiles().length, is(0));
        assertThat(created.getLocationData(), is(nullValue()));
        assertThat(created.getSessionCount(), is(0L));
        assertThat(created.getLastSeenIp(), is("1.2.3.4"));
        assertThat(created.getLastSeenUserAgent(), is("ie6"));
        assertThat(created.getAvatarUrl(), is(nullValue()));
        assertThat(created.isUnsubscribedFromEmails(), is(false));
        assertThat(created.getCompanyIds()[0], is("new.company"));
    }

    @Test
    public void updateUser() {
        User user = new User();
        user.setUserId("user1");
        user.setName("Updated User");
        Date newDate = new Date(1400000000L * 1000);
        user.setLastImpressionAt(newDate);
        Map<String, Object> customData = new HashMap<String, Object>();
        customData.put("custom_data_1", "updated");
        user.setCustomData(customData);

        User updated = this.intercom.updateUser(user);
        assertThat(updated.getUserId(), is("user1"));
        assertThat(updated.getName(), is("Updated User"));
        assertThat(updated.getLastImpressionAt(), is(newDate));
        assertThat((String)user.getCustomData().get("custom_data_1"), is("updated"));
    }

    @Test
    public void deleteUserById() {
        assertThat(this.intercom.getUserCount(), is(3));

        User deleted1 = this.intercom.deleteUserById("user1");
        assertThat(deleted1.getUserId(), is("user1"));
        assertThat(this.intercom.getUserCount(), is(2));

        User deleted2 = this.intercom.deleteUserById("user2");
        assertThat(deleted2.getUserId(), is("user2"));
        assertThat(this.intercom.getUserCount(), is(1));
    }

    @Test
    public void deleteUserByEmail() {
        assertThat(this.intercom.getUserCount(), is(3));

        User deleted1 = this.intercom.deleteUserByEmail("user1@example.com");
        assertThat(deleted1.getUserId(), is("user1"));
        assertThat(this.intercom.getUserCount(), is(2));

        User deleted2 = this.intercom.deleteUserByEmail("user2@example.com");
        assertThat(deleted2.getUserId(), is("user2"));
        assertThat(this.intercom.getUserCount(), is(1));
    }

    @Test
    public void getTag() {
        Tag tag1 = this.intercom.getTag("Tag1");
        assertThat(tag1.getName(), is("Tag1"));
        assertThat(tag1.getTaggedUserCount(), is(2));

        Tag tag2 = this.intercom.getTag("Tag2");
        assertThat(tag2.getName(), is("Tag2"));
        assertThat(tag2.getTaggedUserCount(), is(1));
    }

    @Test
    public void createTag() {
        Tag tag = new Tag();
        tag.setName("New Tag");
        tag.setUserIds(new String[]{"user1", "user2"});
        tag.setTagOrUntag("tag");

        Tag created = this.intercom.createTag(tag);
        assertThat(created.getName(), is("New Tag"));
        assertThat(created.getTaggedUserCount(), is(2));
    }

    @Test
    public void updateTag() {
        Tag tag = this.intercom.getTag("Tag2");
        assertThat(tag.getTaggedUserCount(), is(1));
        assertThat(this.intercom.getUsersByTagName("Tag2").next().getUserId(), is("user1"));

        // Untag
        tag.setUserIds(new String[]{"user1"});
        tag.setTagOrUntag("untag");
        Tag unsetTag = this.intercom.updateTag(tag);
        assertThat(unsetTag.getTaggedUserCount(), is(0));

        // Tag
        tag.setUserIds(new String[]{"user2"});
        tag.setTagOrUntag("tag");
        Tag setTag = this.intercom.updateTag(tag);
        assertThat(setTag.getTaggedUserCount(), is(1));
        assertThat(this.intercom.getUsersByTagName("Tag2").next().getUserId(), is("user2"));
    }

    @Test
    public void createNote() {
        Note note = new Note();
        note.setUserId("user1");
        note.setBody("This is the text of my note.");

        Note created = this.intercom.createNote(note);
        assertThat(created.getHtml(), is("<p>This is the text of my note.</p>"));
        assertThat(created.getCreatedAt(), is(not(nullValue())));
        assertThat(created.getUser().getUserId(), is("user1"));
    }

    @Test
    public void createImpression() {
        Impression impression = new Impression();
        impression.setUserId("user1");
        impression.setUserIp("127.0.0.1");
        impression.setUserAgent("Anonymous User Agent");
        impression.setCurrentUrl("http://localhost/example/");

        Impression created = this.intercom.createImpression(impression);
        assertThat(created.getUnreadMessages(), is(0));
        User user = this.intercom.getUserById("user1");
        assertThat(user.getLastSeenIp(), is("127.0.0.1"));
        assertThat(user.getLastSeenUserAgent(), is("Anonymous User Agent"));
    }

    @Test
    public void badRequest_400() {
        try {
            this.intercom.getTag("");
            fail("IntercomException must be raised for missing a parameter.");
        } catch (IntercomException e) {
            assertThat(e.getErrorResponse().getType(), is("invalid_parameters"));
            assertThat(e.getErrorResponse().getMessage(), is("Missing required parameter 'name'."));
            assertThat(e.getErrorResponse().getStatusCode(), is(400));
        }
    }

    @Test
    public void unauthorized_401() {
        try {
            Intercom intercom = new Intercom();
            intercom.getTag("");
            fail("IntercomException must be raised for missing an API key.");
        } catch (IntercomException e) {
            assertThat(e.getMessage(), is("not_authenticated: HTTP Basic: Access denied."));
            assertThat(e.getErrorResponse().getType(), is("not_authenticated"));
            assertThat(e.getErrorResponse().getMessage(), is("HTTP Basic: Access denied."));
            assertThat(e.getErrorResponse().getStatusCode(), is(401));
        }
    }

    @Test
    public void notFound_404() {
        try {
            this.intercom.getUserById("UnknownUser");
            fail("IntercomException must be raised for getting an unknown user.");
        } catch (IntercomException e) {
            assertThat(e.getMessage(), is("not_found: The user was not found"));
            assertThat(e.getErrorResponse().getType(), is("not_found"));
            assertThat(e.getErrorResponse().getMessage(), is("The user was not found"));
            assertThat(e.getErrorResponse().getStatusCode(), is(404));
        }
    }
}
