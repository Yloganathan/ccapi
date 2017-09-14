package com.capone.ccapi.resources;

import com.capone.ccapi.core.Journal;
import com.capone.ccapi.db.JournalDAO;
import com.capone.ccapi.core.Account;
import com.capone.ccapi.db.AccountDAO;
import com.capone.ccapi.db.LedgerDAO;
import com.capone.ccapi.util.LedgerService;
import com.capone.ccapi.util.AccountService;
import io.dropwizard.testing.junit.ResourceTestRule;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.MockitoJUnitRunner;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.anyLong;

public class JournalResourceTest {

    private static final JournalDAO journalDAO = mock(JournalDAO.class);
    private static final AccountDAO accountDAO = mock(AccountDAO.class);
    private static final LedgerDAO ledgerDAO = mock(LedgerDAO.class);

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new JournalResource(journalDAO))
            .build();

    @Captor
    private ArgumentCaptor<Journal> journalCaptor;
    private Journal journal;
    private Account account;

    @Before
    public void setUp() {
        AccountService.createAccountService(accountDAO);
        LedgerService.createLedgerService(ledgerDAO);
        account = new Account("WonderWoman");
        journalCaptor = ArgumentCaptor.forClass(Journal.class);
        journal = new Journal(0,"purchase",500);
    }

    @After
    public void tearDown() {
        reset(journalDAO);
    }

    @Test
    public void createJournal() throws JsonProcessingException {
        when(journalDAO.create(any(Journal.class))).thenReturn(journal);
        when(accountDAO.findById(anyLong())).thenReturn(Optional.ofNullable(account));
        
        final Response response = resources.target("/journals")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(journal, MediaType.APPLICATION_JSON_TYPE));

        assertThat(response.getStatusInfo()).isEqualTo(Response.Status.OK);
        verify(journalDAO).create(journalCaptor.capture());
        assertThat(journalCaptor.getValue()).isEqualTo(journal);
    }

    @Test
    public void createJournalWithNoAccount() throws JsonProcessingException {
        when(journalDAO.create(any(Journal.class))).thenReturn(journal);
        when(accountDAO.findById(anyLong())).thenReturn(Optional.empty());

        final Response response = resources.target("/journals")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(journal, MediaType.APPLICATION_JSON_TYPE));

        assertThat(response.getStatusInfo()).isEqualTo(Response.Status.NOT_FOUND);
    }


}