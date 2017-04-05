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

/**
 * @author Kim Jensen
 * @since  CWS 1.0
 */
public final class KeyEntityTest extends DatabaseSetup {

    @Test
    public void testEntity() {
        final Settings settings = new Settings();
        final KeyEntity key = new KeyEntity();
        key.setAlgorithm(settings.getSymmetricAlgorithm());
        key.setCipherMode(settings.getSymmetricCipherMode());
        key.setPadding(settings.getSymmetricPadding());
        key.setStatus(Status.ACTIVE);
        key.setExpires(new Date());
        key.setGracePeriod(3);
        persistAndDetach(key);
        assertThat(key.getId(), is(not(nullValue())));

        final KeyEntity found = find(KeyEntity.class, key.getId());
        assertThat(found, is(not(nullValue())));
        assertThat(found.getAlgorithm(), is(key.getAlgorithm()));
        assertThat(found.getCipherMode(), is(key.getCipherMode()));
        assertThat(found.getPadding(), is(key.getPadding()));
        assertThat(found.getStatus(), is(key.getStatus()));
        assertThat(toString(found.getExpires()), is(toString(key.getExpires())));
        assertThat(found.getGracePeriod(), is(key.getGracePeriod()));

        found.setStatus(Status.DEPRECATED);
        persist(found);

        final KeyEntity updated = find(KeyEntity.class, key.getId());
        assertThat(updated.getStatus(), is(not(key.getStatus())));
    }

    @Test
    public void testUpdateAlgorithm() {
        final KeyEntity key = prepareKey();

        // Now to the actual test, change the Algorithm, persist, detach and
        // find the Entity again. Expected is no errors but the value is same.
        key.setAlgorithm("Other");
        persistAndDetach(key);

        final KeyEntity found = find(KeyEntity.class, key.getId());
        assertThat(found.getAlgorithm(), is(not("Other")));
    }

    @Test
    public void testUpdateCipherMode() {
        final KeyEntity key = prepareKey();

        // Now to the actual test, change the CipherMode, persist, detach and
        // find the Entity again. Expected is no errors but the value is same.
        key.setCipherMode("Other");
        persistAndDetach(key);

        final KeyEntity found = find(KeyEntity.class, key.getId());
        assertThat(found.getCipherMode(), is(not("Other")));
    }

    @Test
    public void testUpdatePadding() {
        final KeyEntity key = prepareKey();

        // Now to the actual test, change the Padding, persist, detach and
        // find the Entity again. Expected is no errors but the value is same.
        key.setPadding("Other");
        persistAndDetach(key);

        final KeyEntity found = find(KeyEntity.class, key.getId());
        assertThat(found.getPadding(), is(not("Other")));
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
