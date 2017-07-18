/*
 * =============================================================================
 * Copyright (c) 2016-2017, JavaDog.io
 * -----------------------------------------------------------------------------
 * Project: CWS (cws-model)
 * =============================================================================
 */
package io.javadog.cws.model.entities;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import io.javadog.cws.common.Settings;
import io.javadog.cws.common.enums.Status;
import io.javadog.cws.model.DatabaseSetup;
import org.junit.Test;

import java.util.Date;
import java.util.UUID;

/**
 * @author Kim Jensen
 * @since  CWS 1.0
 */
public final class KeyEntityTest extends DatabaseSetup {

    @Test
    public void testEntity() {
        final Settings mySettings = new Settings();
        final String salt = UUID.randomUUID().toString();
        final KeyEntity key = new KeyEntity();
        key.setAlgorithm(mySettings.getSymmetricAlgorithm());
        key.setSalt(salt);
        key.setStatus(Status.ACTIVE);
        key.setExpires(new Date());
        key.setGracePeriod(3);
        persistAndDetach(key);
        assertThat(key.getId(), is(not(nullValue())));

        final KeyEntity found = find(KeyEntity.class, key.getId());
        assertThat(found, is(not(nullValue())));
        assertThat(found.getAlgorithm(), is(key.getAlgorithm()));
        assertThat(found.getSalt(), is(salt));
        assertThat(found.getStatus(), is(key.getStatus()));
        assertThat(toString(found.getExpires()), is(toString(key.getExpires())));
        assertThat(found.getGracePeriod(), is(key.getGracePeriod()));

        found.setStatus(Status.DEPRECATED);
        persist(found);

        final KeyEntity updated = find(KeyEntity.class, key.getId());
        assertThat(updated.getStatus(), is(not(key.getStatus())));
    }

    @Test
    public void testUpdateExpires() {
        final KeyEntity key = prepareKey();

        // Now to the actual test, change the Expires, persist, detach and
        // find the Entity again. Expected is no errors but the value is same.
        final Date expires = new Date();
        key.setExpires(expires);
        persistAndDetach(key);

        final KeyEntity found = find(KeyEntity.class, key.getId());
        assertThat(toString(found.getExpires()), is(nullValue()));
    }

    @Test
    public void testUpdateGracePeriod() {
        final KeyEntity key = prepareKey();

        // Now to the actual test, change the GracePeriod, persist, detach and
        // find the Entity again. Expected is no errors but the value is same.
        key.setGracePeriod(3);
        persistAndDetach(key);

        final KeyEntity found = find(KeyEntity.class, key.getId());
        assertThat(found.getGracePeriod(), is(nullValue()));
    }
}
