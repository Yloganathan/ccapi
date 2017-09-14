package com.capone.ccapi.resources;

import com.capone.ccapi.core.Account;
import com.capone.ccapi.core.Journal;
import com.capone.ccapi.core.Ledger;
import com.capone.ccapi.api.AccountSummary;
import com.capone.ccapi.db.AccountDAO;
import com.capone.ccapi.db.JournalDAO;
import com.capone.ccapi.db.LedgerDAO;
import com.capone.ccapi.util.LedgerService;
import com.capone.ccapi.util.JournalService;
import io.dropwizard.testing.junit.ResourceTestRule;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.GenericType;
import java.util.Optional;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.anyString;

public class AccountResourceTest {

    private static final JournalDAO journalDAO = mock(JournalDAO.class);
    private static final AccountDAO accountDAO = mock(AccountDAO.class);
    private static final LedgerDAO ledgerDAO = mock(LedgerDAO.class);

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new AccountResource(accountDAO))
            .build();

    @Captor
    private ArgumentCaptor<Account> accountCaptor;
    private Account account;

    @Before
    public void setUp() {
        JournalService.createJournalService(journalDAO);
        LedgerService.createLedgerService(ledgerDAO);
        accountCaptor = ArgumentCaptor.forClass(Account.class);
        account = new Account();
        account.setId(1);
        account.setName("WonderWoman");
    }

    @After
    public void tearDown() {
        reset(accountDAO);
    }

    @Test
    public void createAccount() throws JsonProcessingException {
        when(accountDAO.create(any(Account.class))).thenReturn(account);

        final Response response = resources.target("/accounts")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(account, MediaType.APPLICATION_JSON_TYPE));

        assertThat(response.getStatusInfo()).isEqualTo(Response.Status.OK);
        verify(accountDAO).create(accountCaptor.capture());
        assertThat(accountCaptor.getValue()).isEqualTo(account);
    }

    @Test
    public void getAccountThatDoesNotExist() {
        when(accountDAO.findById(anyLong())).thenReturn(Optional.empty());
        final Response response = resources.target("/accounts/2")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get();

        assertThat(response.getStatusInfo()).isEqualTo(Response.Status.NOT_FOUND);
        verify(accountDAO).findById(anyLong());

    }

    @Test
    public void getAccountSummary() {
        when(accountDAO.findById(anyLong())).thenReturn(Optional.ofNullable(account));
        when(ledgerDAO.findByLedgerTypeAndAccountId(anyString(), anyLong())).thenReturn(new ArrayList<Ledger>(
            Arrays.asList(new Ledger(1,"purchase",200,0), new Ledger(1,"purchase",300,0), new Ledger(1,"purchase",211,0))));
        when(journalDAO.findJournalsByAccountId(anyLong())).thenReturn(new ArrayList<Journal>(
            Arrays.asList(new Journal(1,"purchase",200), new Journal(1,"purchase",300), new Journal(1,"purchase",211))));

        final AccountSummary summary = resources.target("/accounts/1")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get(AccountSummary.class);

        verify(accountDAO).findById(anyLong());
        verify(ledgerDAO).findByLedgerTypeAndAccountId(anyString(), anyLong());
        verify(journalDAO).findJournalsByAccountId(anyLong());
        assertThat(summary.getId()).isEqualTo(1);
        assertThat(summary.getPrincipal()).isEqualTo(711.00);
        assertThat(summary.getRelatedTransactions().size()).isEqualTo(3);

    }
}