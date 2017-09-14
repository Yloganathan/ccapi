package com.capone.ccapi.db;

import com.capone.ccapi.core.Ledger;
import com.capone.ccapi.core.Account;
import io.dropwizard.testing.junit.DAOTestRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import java.util.Optional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class LedgerDAOTest {

    @Rule
    public DAOTestRule daoTestRule = DAOTestRule.newBuilder()
        .addEntityClass(Account.class)
        .addEntityClass(Ledger.class)
        .build();

    private LedgerDAO ledgerDAO;
    private AccountDAO accountDAO;
    private Account account;

    @Before
    public void setUp() throws Exception {
        ledgerDAO = new LedgerDAO(daoTestRule.getSessionFactory());
        accountDAO = new AccountDAO(daoTestRule.getSessionFactory());
        account = daoTestRule.inTransaction(() -> accountDAO.create(new Account("WonerWoman")));
    }

    @Test
    public void createLedger() {
        final Ledger ww = daoTestRule.inTransaction(() -> ledgerDAO.create(new Ledger(account.getId(),"Principal",500, 0)));
        assertThat(ww.getId()).isGreaterThan(0);
        assertThat(ww.getAccountId()).isEqualTo(account.getId());
        assertThat(ledgerDAO.findById(ww.getId())).isEqualTo(Optional.of(ww));
    }

    @Test
    public void findByLedgerTypeAndAccountId() {
        Account account2 = daoTestRule.inTransaction(() -> accountDAO.create(new Account("WonerWoman")));
        daoTestRule.inTransaction(() -> {
            ledgerDAO.create(new Ledger(account.getId(),"Principal",500, 0));
            ledgerDAO.create(new Ledger(account.getId(),"Cash-Out",0, 500));
            ledgerDAO.create(new Ledger(account.getId(),"Principal",200, 0));
            ledgerDAO.create(new Ledger(account.getId(),"Cash-Out",0, 200));
            ledgerDAO.create(new Ledger(account2.getId(),"Principal",199.99, 0));
            ledgerDAO.create(new Ledger(account2.getId(),"Cash-Out",0, 199.99));
        });
        List<Ledger> ledgers = ledgerDAO.findByLedgerTypeAndAccountId("Principal", account.getId());
        assertThat(ledgers.size()).isEqualTo(2);
    }
}