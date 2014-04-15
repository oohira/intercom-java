package com.github.oohira.intercom.model;

import com.github.oohira.intercom.Intercom;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

/**
 * Test class of {@link User}.
 */
public class UserTest {

    @Test
    public void deserializeNull() {
        Intercom intercom = new Intercom();
        String json = "";
        User user = intercom.deserialize(json, User.class);
        assertThat(user, is(nullValue()));
    }

    @Test
    public void deserializeEmpty() {
        Intercom intercom = new Intercom();
        String json = "{}";
        User user = intercom.deserialize(json, User.class);
        assertThat(user.getIntercomId(), is(nullValue()));
        assertThat(user.getUserId(), is(nullValue()));
        assertThat(user.getEmail(), is(nullValue()));
        assertThat(user.getName(), is(nullValue()));
        assertThat(user.getCreatedAt(), is(nullValue()));
        assertThat(user.getLastImpressionAt(), is(nullValue()));
    }

    @Test
    public void deserialize() {
        Intercom intercom = new Intercom();
        String json = "{" +
                "'intercom_id'       : '52322b396823b17b1100016a'," +
                "'email'             : 'john.doe@example.com'," +
                "'user_id'           : 'abc123'," +
                "'name'              : 'John Doe'," +
                "'created_at'        : 1270000000," +
                "'last_impression_at': 1300000000," +
                "'custom_data': {" +
                "    'app_name': 'Genesis'," +
                "    'monthly_spend': 155.5," +
                "    'team_mates': 7" +
                "}," +
                "'social_profiles': [" +
                "{" +
                "    'type': 'twitter'," +
                "    'url': 'http://twitter.com/abc'," +
                "    'username': 'abc'" +
                "}," +
                "{" +
                "    'type': 'facebook'," +
                "    'url': 'http://facebook.com/vanity'," +
                "    'username': 'vanity'," +
                "    'id': '13241141441141413'" +
                "}" +
                "]," +
                "'location_data': {" +
                "    'city_name'     : 'Santiago'," +
                "    'continent_code': 'SA'," +
                "    'country_name'  : 'Chile'," +
                "    'latitude'      : -33.44999999999999," +
                "    'longitude'     : -70.6667," +
                "    'postal_code'   : ''," +
                "    'region_name'   : '12'," +
                "    'timezone'      : 'Chile/Continental'," +
                "    'country_code'  : 'CHL'" +
                "}," +
                "'session_count': 0," +
                "'last_seen_ip': '127.0.0.1'," +
                "'last_seen_user_agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_3) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.56 Safari/535.11'," +
                "'unsubscribed_from_emails': false" +
                "}";
        User user = intercom.deserialize(json, User.class);
        assertThat(user.getIntercomId(), is("52322b396823b17b1100016a"));
        assertThat(user.getUserId(), is("abc123"));
        assertThat(user.getEmail(), is("john.doe@example.com"));
        assertThat(user.getName(), is("John Doe"));
        assertThat(user.getCreatedAt(), is(new Date(1270000000L * 1000)));
        assertThat(user.getLastImpressionAt(), is(new Date(1300000000L * 1000)));
        assertThat((String) user.getCustomData().get("app_name"), is("Genesis"));
        assertThat((Double) user.getCustomData().get("monthly_spend"), is(155.5));
        assertThat((Double) user.getCustomData().get("team_mates"), is(7.0));

        SocialProfile[] profiles = user.getSocialProfiles();
        assertThat(profiles.length, is(2));
        assertThat(profiles[0].getType(), is("twitter"));
        assertThat(profiles[0].getUrl(), is("http://twitter.com/abc"));
        assertThat(profiles[0].getUsername(), is("abc"));
        assertThat(profiles[0].getId(), is(nullValue()));
        assertThat(profiles[1].getType(), is("facebook"));
        assertThat(profiles[1].getUrl(), is("http://facebook.com/vanity"));
        assertThat(profiles[1].getUsername(), is("vanity"));
        assertThat(profiles[1].getId(), is("13241141441141413"));

        LocationData loc = user.getLocationData();
        assertThat(loc.getCityName(), is("Santiago"));
        assertThat(loc.getContinentCode(), is("SA"));
        assertThat(loc.getCountryName(), is("Chile"));
        assertThat(loc.getLatitude(), is(-33.44999999999999));
        assertThat(loc.getLongitude(), is(-70.6667));
        assertThat(loc.getPostalCode(), is(""));
        assertThat(loc.getRegionName(), is("12"));
        assertThat(loc.getTimezone(), is("Chile/Continental"));
        assertThat(loc.getCountryCode(), is("CHL"));

        assertThat(user.getSessionCount(), is(0L));
        assertThat(user.getLastSeenIp(), is("127.0.0.1"));
        assertThat(user.getLastSeenUserAgent(), is("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_3) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.56 Safari/535.11"));
        assertThat(user.isUnsubscribedFromEmails(), is(false));
    }

    @Test
    public void serializeNull() {
        Intercom intercom = new Intercom();
        User user = null;
        String json = intercom.serialize(user);
        assertThat(json, is("null"));
    }

    @Test
    public void serializeEmpty() {
        Intercom intercom = new Intercom();
        User user = new User();
        String json = intercom.serialize(user);
        assertThat(json, is("{}"));
    }

    @Test
    public void serialize() {
        Intercom intercom = new Intercom();
        User user = new User();
        user.setUserId("abc123");
        user.setEmail("john.doe@example.com");
        user.setName("John Doe");
        user.setCreatedAt(new Date(1270000000L * 1000));
        Map<String, Object> customData = new HashMap<String, Object>();
        customData.put("app_name", "Genesis");
        customData.put("monthly_spend", 155.5);
        customData.put("team_mates", 7);
        user.setCustomData(customData);
        user.setLastSeenIp("1.2.3.4");
        user.setLastSeenUserAgent("ie6");
        user.setLastImpressionAt(new Date(1300000000L * 1000));
        Company company = new Company();
        company.setId("6");
        company.setName("Intercom");
		HashMap<String, Object> customParams = new HashMap<String, Object>();
		customParams.put("last_plan_change", new Date(1270000000L * 1000));
		customParams.put("email", "jhon@doe.com");
		customParams.put("members", 2);
		customParams.put("null_value", null);
		company.setCustomData(customParams);
        user.setCompanies(new Company[]{company});

        String json = intercom.serialize(user);
        assertThat(json, is("{" +
                "\"email\":\"john.doe@example.com\"," +
                "\"user_id\":\"abc123\"," +
                "\"name\":\"John Doe\"," +
                "\"created_at\":1270000000," +
                "\"last_impression_at\":1300000000," +
                "\"custom_data\":{" +
                "\"team_mates\":7," +
                "\"app_name\":\"Genesis\"," +
                "\"monthly_spend\":155.5" +
                "}," +
                "\"last_seen_ip\":\"1.2.3.4\"," +
                "\"last_seen_user_agent\":\"ie6\"," +
 "\"companies\":[{\"id\":\"6\",\"name\":\"Intercom\",\"last_plan_change\":1270000000,\"email\":\"jhon@doe.com\",\"members\":2}]}"));
    }
}
