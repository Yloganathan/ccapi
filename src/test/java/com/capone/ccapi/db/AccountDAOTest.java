package com.capone.ccapi.db;

import com.capone.ccapi.core.Account;
import io.dropwizard.testing.junit.DAOTestRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountDAOTest {

    @Rule
    public DAOTestRule daoTestRule = DAOTestRule.newBuilder()
        .addEntityClass(Account.class)
        .build();

    private AccountDAO accountDAO;

    @Before
    public void setUp() throws Exception {
        accountDAO = new AccountDAO(daoTestRule.getSessionFactory());
    }

    @Test
    public void createAccount() {
        final Account ww = daoTestRule.inTransaction(() -> accountDAO.create(new Account("WonerWoman")));
        assertThat(ww.getId()).isGreaterThan(0);
        assertThat(ww.getName()).isEqualTo("WonerWoman");
        assertThat(accountDAO.findById(ww.getId())).isEqualTo(Optional.of(ww));
    }

    @Test
    public void createAccountWithNoName() {
        final Account ww = daoTestRule.inTransaction(() -> accountDAO.create(new Account()));
        assertThat(ww.getId()).isGreaterThan(0);
        assertThat(accountDAO.findById(ww.getId())).isEqualTo(Optional.of(ww));
    }
}