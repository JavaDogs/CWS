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
package io.javadog.cws.core.model;

import io.javadog.cws.core.enums.SanityStatus;
import io.javadog.cws.core.model.entities.DataEntity;
import io.javadog.cws.core.model.entities.MemberEntity;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.EntityManager;

/**
 * <p>Data Access Object functionality used explicitly for Sanity Checks.</p>
 *
 * @author Kim Jensen
 * @since CWS 1.1
 */
public final class SanityDao extends CommonDao {

    public SanityDao(final EntityManager entityManager) {
        super(entityManager);
    }

    public List<DataEntity> findFailedRecords(final LocalDateTime since) {
        final var query = entityManager
                .createNamedQuery("data.findAllWithState")
                .setParameter(STATUS, SanityStatus.FAILED)
                .setParameter(SINCE, since);

        return findList(query);
    }

    public List<DataEntity> findFailedRecords(final MemberEntity circleAdministrator, final LocalDateTime since) {
        final var query = entityManager
                .createNamedQuery("data.findAllWithStateForMember")
                .setParameter(STATUS, SanityStatus.FAILED)
                .setParameter(SINCE, since)
                .setParameter(MEMBER, circleAdministrator);

        return findList(query);
    }

    public List<DataEntity> findFailedRecords(final String circleId, final LocalDateTime since) {
        final var query = entityManager
                .createNamedQuery("data.findAllWithStateForCircle")
                .setParameter(STATUS, SanityStatus.FAILED)
                .setParameter(SINCE, since)
                .setParameter(EXTERNAL_ID, circleId);

        return findList(query);
    }
}
