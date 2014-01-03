package com.github.oohira.intercom;

import com.github.oohira.intercom.model.User;
import com.github.oohira.intercom.model.UserCollection;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * An iterator class for fetching users.
 *
 * @author oohira
 */
public class UserIterator implements Iterable<User>, Iterator<User> {
    private final Intercom intercom;
    private final String tagName;
    private int index;
    private UserCollection collection;

    public UserIterator(final Intercom intercom, final String tagName) {
        this.intercom = intercom;
        this.tagName = tagName;
        this.index = 0;
        this.collection = this.intercom.getUsers(1, this.tagName);
    }

    @Override
    public Iterator<User> iterator() {
        return this;
    }

    @Override
    public boolean hasNext() {
        if (this.collection == null) {
            return false;
        }
        User[] users = this.collection.getUsers();
        if (users == null) {
            return false;
        }
        if (this.index < 0 || users.length <= this.index) {
            Integer nextPage = this.collection.getNextPage();
            if (nextPage == null) {
                this.collection = null;
                return false;
            }
            this.collection = this.intercom.getUsers(nextPage, this.tagName);
            this.index = 0;
        }
        return (0 <= this.index && this.index < users.length);
    }

    @Override
    public User next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        User[] users = this.collection.getUsers();
        assert(0 <= this.index && this.index < users.length);
        return users[this.index++];
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
