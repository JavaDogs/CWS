/*
 * CWS, Cryptographic Web Store - open source Cryptographic Storage system.
 * Copyright (C) 2016-2021, JavaDog.io
 * mailto: cws AT JavaDog DOT io
 *
 * CWS is free software; you can redistribute it and/or modify it under the
 * terms of the Apache License, as published by the Apache Software Foundation.
 *
 * CWS is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the Apache License for more details.
 *
 * You should have received a copy of the Apache License, version 2, along with
 * this program; If not, you can download a copy of the License
 * here: https://www.apache.org/licenses/
 */
package io.javadog.cws.core.model.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import io.javadog.cws.api.common.Utilities;
import io.javadog.cws.core.model.CommonDao;
import io.javadog.cws.core.setup.DatabaseSetup;
import java.util.List;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.junit.jupiter.api.Test;

/**
 * @author Kim Jensen
 * @since CWS 1.0
 */
final class VersionEntityTest extends DatabaseSetup {

    @Test
    void testEntity() {
        final Query query = entityManager.createNamedQuery("version.findAll");
        final List<VersionEntity> found = CommonDao.findList(query);
        assertEquals(4, found.size());
        assertEquals(Long.valueOf(4L), found.get(0).getId());
        assertEquals("2.0.0", found.get(0).getCwsVersion());
        assertEquals("H2", found.get(0).getDbVendor());
        assertEquals(Integer.valueOf(4), found.get(0).getSchemaVersion());
        assertNotNull(found.get(0).getInstalled());

        // Now adding a new Entity, this must fail.
        final VersionEntity entity = new VersionEntity();
        entity.setId(9999L);
        entity.setCwsVersion("99.99.99");
        entity.setDbVendor("SuperDB");
        entity.setSchemaVersion(9999);
        entity.setInstalled(Utilities.newDate());

        try {
            entityManager.persist(entity);
        } catch (PersistenceException e) {
            assertEquals("ids for this class must be manually assigned before calling save(): io.javadog.cws.core.model.entities.VersionEntity", e.getMessage());
        }
    }
}
