package com.capone.ccapi.resources;

import com.capone.ccapi.core.Journal;
import com.capone.ccapi.db.JournalDAO;
import com.capone.ccapi.core.Account;
import com.capone.ccapi.db.AccountDAO;
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
    

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new JournalResource(journalDAO))
            .build();

    @Captor
    private ArgumentCaptor<Journal> journalCaptor;
    private Journal journal;
    private Optional<Account> optionalAccount =  Optional.empty();
    private Account account;

    @Before
    public void setUp() {
        account = new Account("WonderWoman");
        account.setId(1);
        Optional<Account> optionalAccount = Optional.ofNullable(account);
    System.out.print(optionalAccount.get());
        journalCaptor = ArgumentCaptor.forClass(Journal.class);
        journal = new Journal(1,"purchase",500);
    }

    @After
    public void tearDown() {
        reset(journalDAO);
    }

    @Test
    public void createJournal() throws JsonProcessingException {
        when(journalDAO.create(any(Journal.class))).thenReturn(journal);
        when(accountDAO.findById(anyLong())).thenReturn(optionalAccount);
        AccountService.createAccountService(accountDAO);
        final Response response = resources.target("/journals")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(journal, MediaType.APPLICATION_JSON_TYPE));

        assertThat(response.getStatusInfo()).isEqualTo(Response.Status.OK);
        verify(journalDAO).create(journal);
        assertThat(journalCaptor.getValue()).isEqualTo(journal);
    }


}