package com.github.oohira.intercom.model;

import com.github.oohira.intercom.Intercom;
import org.junit.Test;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

/**
 * Test class of {@link Event}.
 */
public class EventTest {

    @Test
    public void deserializeNull() {
        Intercom intercom = new Intercom();
        String json = "";
        Event event = intercom.deserialize(json, Event.class);
        assertThat(event, is(nullValue()));
    }

    @Test
    public void deserializeEmpty() {
        Intercom intercom = new Intercom();
        String json = "{}";
        Event event = intercom.deserialize(json, Event.class);
        assertThat(event.getEventName(), is(nullValue()));
        assertThat(event.getUserId(), is(nullValue()));
        assertThat(event.getEmail(), is(nullValue()));
        assertThat(event.getCreatedAt(), is(nullValue()));
        assertThat(event.getMetadata(), is(nullValue()));
    }

    @Test
    public void deserialize() {
        Intercom intercom = new Intercom();
        String json = "{" +
                "'event_name' : 'ordered-item'," +
                "'user_id'    : 'abc123'," +
                "'email'      : 'john.doe@example.com'," +
                "'created'    : 1270000000," +
                "'metadata'   : {" +
                "    'source'      : 'desktop'," +
                "    'load'        : 3.67," +
                "    'order_date'  : 1300000000," +
                "    'article'     : 'https://example.com/article.html'," +
                "    'order_number': {" +
                "        'url'  : 'https://example.com/orders/3434-3434'," +
                "        'value': '3434-3434'" +
                "    }," +
                "    'stripe_invoice' : 'inv_3434343434'," +
                "    'stripe_customer': 'cus_42424242424' ," +
                "    'price': {" +
                "        'amount'  : 34999," +
                "        'currency': 'eur'" +
                "    }" +
                "}" +
                "}";
        Event event = intercom.deserialize(json, Event.class);
        assertThat(event.getEventName(), is("ordered-item"));
        assertThat(event.getUserId(), is("abc123"));
        assertThat(event.getEmail(), is("john.doe@example.com"));
        assertThat(event.getCreatedAt(), is(new Date(1270000000L * 1000)));
        assertThat((String) event.getMetadata().get("source"), is("desktop"));
        assertThat((Double) event.getMetadata().get("load"), is(3.67));
        assertThat((Double) event.getMetadata().get("order_date"), is(1300000000.0));
        assertThat((String) event.getMetadata().get("article"), is("https://example.com/article.html"));
        Map<String, String> orderNumber = (Map<String, String>) event.getMetadata().get("order_number");
        assertThat(orderNumber.get("url"), is("https://example.com/orders/3434-3434"));
        assertThat(orderNumber.get("value"), is("3434-3434"));
        assertThat((String) event.getMetadata().get("stripe_invoice"), is("inv_3434343434"));
        assertThat((String) event.getMetadata().get("stripe_customer"), is("cus_42424242424"));
        Map<String, Object> price = (Map<String, Object>) event.getMetadata().get("price");
        assertThat((Double) price.get("amount"), is(34999.0));
        assertThat((String) price.get("currency"), is("eur"));
    }

    @Test
    public void serializeNull() {
        Intercom intercom = new Intercom();
        Event event = null;
        String json = intercom.serialize(event);
        assertThat(json, is("null"));
    }

    @Test
    public void serializeEmpty() {
        Intercom intercom = new Intercom();
        Event event = new Event();
        String json = intercom.serialize(event);
        assertThat(json, is("{}"));
    }

    @Test
    public void serialize() {
        Intercom intercom = new Intercom();
        Event event = new Event();
        event.setEventName("ordered-item");
        event.setUserId("abc123");
        event.setEmail("john.doe@example.com");
        event.setCreatedAt(new Date(1270000000L * 1000));
        Map<String, Object> metadata = new LinkedHashMap<String, Object>();
        metadata.put("source", "desktop");
        metadata.put("load", 3.67);
        metadata.put("order_date", new Date(1300000000L * 1000));
        metadata.put("article", "https://example.com/article.html");
        Map<String, String> orderNumber = new LinkedHashMap<String, String>(); // Rich Link
        orderNumber.put("url", "https://example.com/orders/3434-3434");
        orderNumber.put("value", "3434-3434");
        metadata.put("order_number", orderNumber);
        metadata.put("stripe_invoice", "inv_3434343434");
        metadata.put("stripe_customer", "cus_42424242424");
        Map<String, Object> price = new LinkedHashMap<String, Object>(); // Monetary Amount
        price.put("amount", 34999);
        price.put("currency", "eur");
        metadata.put("price", price);
        event.setMetadata(metadata);

        String json = intercom.serialize(event);
        assertThat(json, is("{" +
                "\"event_name\":\"ordered-item\"," +
                "\"user_id\":\"abc123\"," +
                "\"email\":\"john.doe@example.com\"," +
                "\"created\":1270000000," +
                "\"metadata\":{" +
                "\"source\":\"desktop\"," +
                "\"load\":3.67," +
                "\"order_date\":1300000000," +
                "\"article\":\"https://example.com/article.html\"," +
                "\"order_number\":{" +
                "\"url\":\"https://example.com/orders/3434-3434\"," +
                "\"value\":\"3434-3434\"" +
                "}," +
                "\"stripe_invoice\":\"inv_3434343434\"," +
                "\"stripe_customer\":\"cus_42424242424\"," +
                "\"price\":{" +
                "\"amount\":34999," +
                "\"currency\":\"eur\"" +
                "}" +
                "}" +
                "}"));
    }
}
