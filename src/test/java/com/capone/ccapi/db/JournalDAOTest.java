package com.capone.ccapi.db;

import com.capone.ccapi.core.Journal;
import com.capone.ccapi.core.Account;
import io.dropwizard.testing.junit.DAOTestRule;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;
import java.util.Optional;
import javax.ws.rs.NotFoundException;

import static org.assertj.core.api.Assertions.assertThat;

public class JournalDAOTest {

    @Rule
    public DAOTestRule daoTestRule = DAOTestRule.newBuilder()
        .addEntityClass(Journal.class)
        .addEntityClass(Account.class)
        .build();

    private JournalDAO journalDAO;
    private AccountDAO accountDAO;
    private Account account;

    @Before
    public void setUp() throws Exception {
        journalDAO = new JournalDAO(daoTestRule.getSessionFactory());
        accountDAO = new AccountDAO(daoTestRule.getSessionFactory());
        account = daoTestRule.inTransaction(() -> accountDAO.create(new Account("WonerWoman")));
    }

    @Test
    public void createJournal() {
        final Journal journal = daoTestRule.inTransaction(() -> journalDAO.create(new Journal(account.getId(),"purchase",500)));
        assertThat(journal.getId()).isGreaterThan(0);
        assertThat(journal.getAccountId()).isEqualTo(account.getId());
        assertThat(journal.getTransactionType()).isEqualTo("purchase");
        assertThat(journal.getAmount()).isEqualTo(500);
        assertThat(journalDAO.findById(journal.getId())).isEqualTo(Optional.of(journal));
    }

    @Test
    public void findAll() {
        long accountId = account.getId();
        Account account2 = daoTestRule.inTransaction(() -> accountDAO.create(new Account("WonerWoman")));
        daoTestRule.inTransaction(() -> {
            journalDAO.create(new Journal(accountId,"purchase",500.11));
            journalDAO.create(new Journal(accountId,"purchase",400.50));
            journalDAO.create(new Journal(accountId,"purchase",99));
            journalDAO.create(new Journal(account2.getId(),"purchase",1000));
        });

        final List<Journal> journals = journalDAO.findJournalsByAccountId(accountId);
        assertThat(journals).extracting("amount").containsOnly(500.11, 400.50, 99.0);
    }

    @Test(expected = ConstraintViolationException.class)
    public void handlesNullTransactionType() {
        daoTestRule.inTransaction(() -> journalDAO.create(new Journal(account.getId(), null, 500)));
    }
}