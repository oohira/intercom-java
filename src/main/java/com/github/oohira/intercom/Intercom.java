package com.github.oohira.intercom;

import com.github.oohira.intercom.model.Company;
import com.github.oohira.intercom.model.ErrorResponse;
import com.github.oohira.intercom.model.Event;
import com.github.oohira.intercom.model.Impression;
import com.github.oohira.intercom.model.Note;
import com.github.oohira.intercom.model.Tag;
import com.github.oohira.intercom.model.User;
import com.github.oohira.intercom.model.UserCollection;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import org.apache.commons.codec.binary.Base64;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class wrapping Intercom APIs.
 *
 * @author oohira
 */
public class Intercom {
    private static final String API_ENDPOINT_URL = "https://api.intercom.io";
    private static final String API_V1_ENDPOINT_URL = API_ENDPOINT_URL + "/v1";
    private static final String USERS_API_URL = API_V1_ENDPOINT_URL + "/users";
    private static final String NOTES_API_URL = API_V1_ENDPOINT_URL + "/users/notes";
    private static final String IMPRESSIONS_API_URL = API_V1_ENDPOINT_URL + "/users/impressions";
    private static final String TAGGING_API_URL = API_V1_ENDPOINT_URL + "/tags";
    private static final String COMPANIES_API_URL = API_V1_ENDPOINT_URL + "/companies";
    // NOTE: Events API has no 'v1' path segment.
    private static final String EVENTS_API_URL = API_ENDPOINT_URL + "/events";

    private static final String DEBUG_KEY = "intercom.debug";
    private static final Logger LOGGER = Logger.getLogger(Intercom.class.getName());

    private final Gson gson;
    private String appId;
    private String apiKey;
    private Integer perPage;

    /**
     * Create an Intercom object without App ID and API key.
     *
     * App ID, API key must be set by {@link #setAppId(String)} and
     * {@link #setApiKey(String)}.
     */
    public Intercom() {
        this(null, null);
    }

    /**
     * Create an Intercom object with App ID and API key.
     *
     * @param appId App ID.
     * @param apiKey API key.
     * @see <a href="https://api.intercom.io/docs#authentication">
     *     Intercom API Document: Authentication</a>
     */
    public Intercom(String appId, String apiKey) {
        this.appId = appId;
        this.apiKey = apiKey;
        this.gson = initializeGson();
    }

    private Gson initializeGson() {
        GsonBuilder builder = new GsonBuilder();
        builder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            @Override
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                return new Date(json.getAsJsonPrimitive().getAsLong() * 1000);
            }
        });
        builder.registerTypeAdapter(Date.class, new JsonSerializer<Date>() {
            @Override
            public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
                return new JsonPrimitive(src.getTime() / 1000);
            }
        });
        builder.registerTypeAdapter(Company.class, new JsonSerializer<Company>() {
            @Override
            public JsonElement serialize(Company company, Type typeOfSrc, JsonSerializationContext context) {
                JsonObject result = new JsonObject();
                result.add("id", context.serialize(company.getId()));
                result.add("name", context.serialize(company.getName()));
                result.add("created_at", context.serialize(company.getCreatedAt()));
                result.add("plan", context.serialize(company.getPlan()));
                result.add("monthly_spend", context.serialize(company.getMonthlySpend()));
                Map<String, Object> customData = company.getCustomData();
                if (customData != null) {
                    for (String key : customData.keySet()) {
                        result.add(key, context.serialize(customData.get(key)));
                    }
                }
                return result;
            }
        });
        return builder.create();
    }

    private void log(final Level level, final String message) {
        if (Boolean.getBoolean(DEBUG_KEY)) {
            LOGGER.log(level, message);
        }
    }

    public <T> String serialize(final T object) {
        return this.gson.toJson(object);
    }

    public <T> T deserialize(final String json, final Class<T> clazz) throws IntercomException {
        try {
            return this.gson.fromJson(json, clazz);
        } catch (JsonParseException e) {
            throw new IntercomException(e);
        }
    }

    /**
     * Set an App ID.
     *
     * @param appId App ID.
     */
    public void setAppId(final String appId) {
        this.appId = appId;
    }

    /**
     * Get an App ID.
     *
     * @return App ID (may be null).
     */
    public String getAppId() {
        return this.appId;
    }

    /**
     * Set an API key.
     *
     * @param apiKey API key (may be null).
     */
    public void setApiKey(final String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * Get an API key.
     *
     * @return API key (may be null).
     */
    public String getApiKey() {
        return this.apiKey;
    }

    /**
     * Set count of users per page.
     *
     * NOTE: for testing only.
     *
     * @param perPage user count per page.
     */
    void setUserCountPerPage(final Integer perPage) {
        this.perPage = perPage;
    }

    /**
     * Retrieves all users.
     *
     * NOTE: The order of users is not defined.
     *
     * @return iterator for all users.
     * @throws IntercomException when some error occurred.
     * @see <a href="https://api.intercom.io/docs#getting_all_users">
     *     Intercom API Document: Getting all Users</a>
     */
    public UserIterator getUsers() {
        return new UserIterator(this, null);
    }

    /**
     * Retrieves all users.
     *
     * NOTE: The order of users is not defined.
     *
     * @param tagName tag name.
     * @return iterator for users attached specified tag.
     * @throws IntercomException when some error occurred.
     * @see <a href="https://api.intercom.io/docs#getting_all_users">
     *     Intercom API Document: Getting all Users</a>
     */
    public UserIterator getUsersByTagName(final String tagName) {
        return new UserIterator(this, tagName);
    }

    UserCollection getUsers(final int page, final String tagName) throws IntercomException {
        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("page", String.valueOf(page));
        if (this.perPage != null) {
            params.put("per_page", this.perPage.toString());
        }
        if (tagName != null) {
            params.put("tag_name", tagName);
        }
        String response = httpGet(USERS_API_URL, params);

        return deserialize(response, UserCollection.class);
    }

    /**
     * Retrieves total count of users.
     *
     * @return total count of users.
     * @throws IntercomException when some error occurred.
     */
    public int getUserCount() throws IntercomException {
        UserCollection collection = getUsers(1, null);
        if (collection == null) {
            return 0;
        }
        return collection.getTotalCount();
    }

    /**
     * Retrieves a user.
     *
     * @param userId an unique identifier for the user.
     * @return the retrieved user.
     * @throws IntercomException when some error occurred.
     * @see <a href="https://api.intercom.io/docs#getting_a_user">
     *     Intercom API Document: Getting a User</a>
     */
    public User getUserById(final String userId) throws IntercomException {
        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("user_id", userId);
        String response = httpGet(USERS_API_URL, params);

        return deserialize(response, User.class);
    }

    /**
     * Retrieves a user.
     *
     * @param email an email address.
     * @return the retrieved user.
     * @throws IntercomException when some error occurred.
     * @see <a href="https://api.intercom.io/docs#getting_a_user">
     *     Intercom API Document: Getting a User</a>
     */
    public User getUserByEmail(final String email) throws IntercomException {
        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("email", email);
        String response = httpGet(USERS_API_URL, params);

        return deserialize(response, User.class);
    }

    /**
     * Creates a user.
     *
     * @param user a new user object.
     * @return the created user object.
     * @throws IntercomException when some error occurred.
     * @see <a href="https://api.intercom.io/docs#creating_a_user">
     *     Intercom API Document: Creating a User</a>
     */
    public User createUser(final User user) throws IntercomException {
        String json = serialize(user);
        String response = httpPost(USERS_API_URL, json);

        return deserialize(response, User.class);
    }

    /**
     * Updates an already existing user.
     *
     * @param user a user object.
     * @return the updated user object.
     * @throws IntercomException when some error occurred.
     * @see <a href="https://api.intercom.io/docs#updating_a_user">
     *     Intercom API Document: Updating a User</a>
     */
    public User updateUser(final User user) throws IntercomException {
        String json = serialize(user);
        String response = httpPut(USERS_API_URL, json);

        return deserialize(response, User.class);
    }

    /**
     * Deletes a user.
     *
     * @param userId an unique identifier for the user.
     * @return the deleted user.
     * @throws IntercomException when some error occurred.
     * @see <a href="https://api.intercom.io/docs#deleting_a_user">
     *     Intercom API Document: Deleting a User</a>
     */
    public User deleteUserById(final String userId) throws IntercomException {
        User user = new User();
        user.setUserId(userId);
        String json = serialize(user);
        String response = httpDelete(USERS_API_URL, json);

        return deserialize(response, User.class);
    }

    /**
     * Deletes a user.
     *
     * @param email an email address.
     * @return the deleted user.
     * @throws IntercomException when some error occurred.
     * @see <a href="https://api.intercom.io/docs#deleting_a_user">
     *     Intercom API Document: Deleting a User</a>
     */
    public User deleteUserByEmail(final String email) throws IntercomException {
        User user = new User();
        user.setEmail(email);
        String json = serialize(user);
        String response = httpDelete(USERS_API_URL, json);

        return deserialize(response, User.class);
    }

    /**
     * Deletes all users.
     *
     * NOTE:
     * This method is not a simple Intercom wrapper API. There is a potential
     * performance issue, because Intercom APIs are called multiple times.
     */
    void clearUsers() throws IntercomException {
        List<String> userIds = new ArrayList<String>();
        List<String> emails = new ArrayList<String>();
        for (User user : getUsers()) {
            String userId = user.getUserId();
            if (userId != null) {
                userIds.add(userId);
            } else {
                emails.add(user.getEmail());
            }
        }
        for (String userId : userIds) {
            deleteUserById(userId);
        }
        for (String email : emails) {
            deleteUserByEmail(email);
        }
    }

    /**
     * Retrieves a company.
     *
     * NOTE: This company retrieval API is not documented in official.
     *
     * @param companyId an unique identifier for the company.
     * @return the retrieved company.
     * @throws IntercomException when some error occurred.
     */
    public Company getCompanyById(final String companyId) throws IntercomException {
        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("id", companyId);
        String response = httpGet(COMPANIES_API_URL, params);

        return deserialize(response, Company.class);
    }

    /**
     * Retrieves a tag.
     *
     * @param name the name of the tag.
     * @return the tag object.
     * @throws IntercomException when some error occurred.
     * @see <a href="https://api.intercom.io/docs#getting_a_tag">
     *     Intercom API Documentation: Getting a Tag</a>
     */
    public Tag getTag(final String name) throws IntercomException {
        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("name", name);
        String response = httpGet(TAGGING_API_URL, params);

        return deserialize(response, Tag.class);
    }

    /**
     * Creates a new tag, optionally, tag/untag users.
     *
     * @param tag a new tag object.
     * @return the created tag object.
     * @throws IntercomException when some error occurred.
     * @see <a href="https://api.intercom.io/docs#creating_a_tag">
     *     Intercom API Documentation: Creating a Tag</a>
     */
    public Tag createTag(final Tag tag) throws IntercomException {
        String json = serialize(tag);
        String response = httpPost(TAGGING_API_URL, json);

        return deserialize(response, Tag.class);
    }

    /**
     * Updates an already existing tag.
     *
     * @param tag a tag object.
     * @return the updated tag object.
     * @throws IntercomException when some error occurred.
     * @see <a href="https://api.intercom.io/docs#updating_a_tag">
     *     Intercom API Documentation: Updating a Tag</a>
     */
    public Tag updateTag(final Tag tag) throws IntercomException {
        String json = serialize(tag);
        String response = httpPut(TAGGING_API_URL, json);

        return deserialize(response, Tag.class);
    }

    /**
     * Creates a new note.
     *
     * @param note a new note object.
     * @return the created note object.
     * @throws IntercomException when some error occurred.
     * @see <a href="https://api.intercom.io/docs#creating_a_note">
     *     Intercom API Documentation: Creating a Note</a>
     */
    public Note createNote(final Note note) throws IntercomException {
        String json = serialize(note);
        String response = httpPost(NOTES_API_URL, json);

        return deserialize(response, Note.class);
    }

    /**
     * Creates an impression.
     *
     * @param impression an impression object.
     * @return the response object.
     * @throws IntercomException when some error occurred.
     * @see <a href="https://api.intercom.io/docs#creating_an_impression">
     *     Intercom API Documentation: Creating an Impression</a>
     */
    public Impression createImpression(final Impression impression) throws IntercomException {
        String json = serialize(impression);
        String response = httpPost(IMPRESSIONS_API_URL, json);

        return deserialize(response, Impression.class);

    }

    /**
     * Tracks an event.
     *
     * @param event an event object.
     * @throws IntercomException when some error occurred.
     * @see <a href="http://doc.intercom.io/api/#submitting-events">
     *     Intercom API Documentation: Submitting Events</a>
     */
    public void trackEvent(final Event event) throws IntercomException {
        String json = serialize(event);
        httpPost(EVENTS_API_URL, json);
    }

    private String httpGet(final String url, final Map<String, String> params) throws IntercomException {
        try {
            StringBuilder buf = new StringBuilder();
            for (String key : params.keySet()) {
                buf.append(buf.length() == 0 ? "?" : "&");
                buf.append(URLEncoder.encode(key, "UTF-8"));
                buf.append("=");
                buf.append(URLEncoder.encode(params.get(key), "UTF-8"));
            }
            return send(url + buf, "GET", null);
        } catch (UnsupportedEncodingException e) {
            throw new IntercomException(e);
        }
    }

    private String httpPost(final String url, final String body) throws IntercomException {
        return send(url, "POST", body);
    }

    private String httpPut(final String url, final String body) throws IntercomException {
        return send(url, "PUT", body);
    }

    private String httpDelete(final String url, final String body) throws IntercomException {
        return send(url, "DELETE", body);
    }

    private String encodeBasicAuthenticationString() {
        if (this.appId != null && this.apiKey != null) {
            String str = this.appId + ":" + this.apiKey;
            return "Basic " + Base64.encodeBase64String(str.getBytes());
        }
        return null;
    }

    private String send(final String url, final String method, final String body) throws IntercomException {
        HttpURLConnection http = null;
        try {
            log(Level.INFO, String.format("%s %s %s", method, url, body));
            http = (HttpURLConnection) new URL(url).openConnection();
            http.setRequestMethod(method);
            String authorizationHeader = encodeBasicAuthenticationString();
            if (authorizationHeader != null) {
                http.setRequestProperty("Authorization", authorizationHeader);
            }
            if (method.equals("POST") || method.equals("PUT") || method.equals("DELETE")) {
                http.setDoOutput(true);
                http.setRequestProperty("Content-Type", "application/json");
                http.setRequestProperty("Content-Length", String.valueOf(body));
            }
            http.connect();

            if (method.equals("POST") || method.equals("PUT") || method.equals("DELETE")) {
                OutputStreamWriter writer = new OutputStreamWriter(http.getOutputStream());
                writer.write(body);
                writer.close();
            }

            int statusCode = http.getResponseCode();
            if (statusCode == HttpURLConnection.HTTP_OK ||
                    statusCode == HttpURLConnection.HTTP_CREATED ||
                    statusCode == HttpURLConnection.HTTP_ACCEPTED) {
                String response = getResponse(http.getInputStream());
                log(Level.INFO, response);
                return response;
            } else {
                String response = getResponse(http.getErrorStream());
                log(Level.WARNING, response);
                ErrorResponse error = deserialize(response, ErrorResponse.class);
                error.setStatusCode(statusCode);
                throw new IntercomException(error);
            }
        } catch (IOException e) {
            throw new IntercomException(e);
        } finally {
            if (http != null) {
                http.disconnect();
            }
        }
    }

    private String getResponse(final InputStream stream) throws IntercomException {
        if (stream == null) {
            return null;
        }
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder response = new StringBuilder();
            String line;
            while((line = reader.readLine()) != null) {
                response.append(line);
            }
            return response.toString();
        } catch (IOException e) {
            throw new IntercomException(e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ex) {
                }
            }
        }
    }
}
