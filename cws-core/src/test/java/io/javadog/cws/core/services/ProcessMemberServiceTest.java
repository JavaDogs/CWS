package io.javadog.cws.core.services;

import io.javadog.cws.api.common.Action;
import io.javadog.cws.api.common.Constants;
import io.javadog.cws.api.common.CredentialType;
import io.javadog.cws.api.requests.ProcessMemberRequest;
import io.javadog.cws.common.exceptions.CWSException;
import io.javadog.cws.model.EntityManagerSetup;
import io.javadog.cws.model.ProcessMemberDao;
import io.javadog.cws.model.jpa.ProcessMemberJpaDao;
import org.junit.Test;

/**
 * @author Kim Jensen
 * @since  CWS 1.0
 */
public final class ProcessMemberServiceTest extends EntityManagerSetup {

    @Test(expected = CWSException.class)
    public void testService() {
        final ProcessMemberDao dao = new ProcessMemberJpaDao(entityManager);
        final ProcessMemberService service = new ProcessMemberService(dao);
        final ProcessMemberRequest request = new ProcessMemberRequest();
        request.setName(Constants.ADMIN_ACCOUNT);
        request.setCredentialType(CredentialType.PASSPHRASE);
        request.setCredentials(Constants.ADMIN_ACCOUNT.toCharArray());
        request.setAction(Action.PROCESS);
        service.process(request);
    }
}