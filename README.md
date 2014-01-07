intercom-java
=============

A Java client library for [Intercom web service](https://www.intercom.io/).
This is just a tiny wrapper around Intercom API, please see [Intercom API Documentation](https://api.intercom.io/docs) for detail.

[![Build Status](https://travis-ci.org/oohira/intercom-java.png?branch=master)](https://travis-ci.org/oohira/intercom-java)

## Installation

Not available yet, but maybe comming soon to the Maven Central Repository. :-p

```xml
<dependency>
   <groupId>com.github.oohira</groupId>
   <artifactId>intercom-java</artifactId>
   <version>?.?.?</version>
</dependency>
```

## Dependencies

* Google GSON (https://code.google.com/p/google-gson/)
* Apache Commons Codec (http://commons.apache.org/proper/commons-codec/)

## License

This software is released under the MIT License, see [LICENSE.txt](https://github.com/oohira/intercom-java/blob/master/LICENSE.txt).

## Usage

#### Initialization

```java
Intercom intercom = new Intercom("APP_ID", "API_KEY");
```

#### Users

```java
User user = new User();
user.setUserId("abc123");
user.setEmail("john.doe@example.com");
user.setName("John Doe");
Company company = new Company();
company.setId("company1");
company.setName("Anonymous Company");
user.setCompanies(new Company[]{company});
intercom.createUser(user);
```

```java
User user = intercom.getUserById("abc123");
Map<String, Object> customData = new HashMap<String, Object>();
customData.put("custom_data_1", "test");
customData.put("custom_data_2", 7);
user.setCustomData(customData);
intercom.updateUser(user);
```

```java
for (User user : intercom.getUsers()) {
    // do something
}
```

#### Tags

```java
Tag tag = intercom.getTag("Free Trial");
tag.setUserIds(new String[]{"abc123", "def456"});
tag.setTagOrUntag("tag");
intercom.updateTag(tag);
```

```java
Tag tag = intercom.getTag("Free Trial");
tag.setUserIds(new String[]{"abc123"});
tag.setTagOrUntag("untag");
intercom.updateTag(tag);
```

#### Notes

```java
Note note = new Note();
note.setEmail("john.doe@example.com");
note.setBody("This is the text of my note.");
intercom.createNote(note);
```

#### Impressions

```java
Impression impression = new Impression();
impression.setUserId("abc123");
impression.setUserIp("127.0.0.1");
intercom.createImpression(impression);
```

## Limitations

* Message Threads API is not supported yet. Now implementing.

