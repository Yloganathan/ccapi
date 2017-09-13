package com.capone.ccapi.resources;

import com.capone.ccapi.core.Account;
import com.capone.ccapi.db.AccountDAO;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AccountResourceTest {

    private static final AccountDAO accountDAO = mock(AccountDAO.class);

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new AccountResource(accountDAO))
            .build();

    @Captor
    private ArgumentCaptor<Account> accountCaptor;
    private Account account;

    @Before
    public void setUp() {
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

    //TODO: test get account summary
    //TODO: get account summary for non existant account
    //SQLDummy sqlDummy = Mockito.mock(SQLDummy.class);
//Mockito.when(sqlDummy.getAllShips()).thenReturn(new ArrayList< Ship >())


}