package com.capone.ccapi.db;

import com.capone.ccapi.core.Ledger;
import io.dropwizard.testing.junit.DAOTestRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class LedgerDAOTest {

    @Rule
    public DAOTestRule daoTestRule = DAOTestRule.newBuilder()
        .addEntityClass(Ledger.class)
        .build();

    private LedgerDAO ledgerDAO;

    @Before
    public void setUp() throws Exception {
        ledgerDAO = new LedgerDAO(daoTestRule.getSessionFactory());
    }

    // @Test
    // public void createLedger() {
    //     final Ledger ww = daoTestRule.inTransaction(() -> ledgerDAO.create(new Ledger("WonerWoman")));
    //     assertThat(ww.getId()).isGreaterThan(0);
    //     assertThat(ww.getName()).isEqualTo("Jenn");
    //     assertThat(ledgerDAO.findById(ww.getId())).isEqualTo(Optional.of(ww));
    // }

    // @Test
    // public void createLedgerWithNoName() {
    //     final Ledger ww = daoTestRule.inTransaction(() -> ledgerDAO.create(new Ledger()));
    //     assertThat(ww.getId()).isGreaterThan(0);
    //     assertThat(ledgerDAO.findById(ww.getId())).isEqualTo(Optional.of(ww));
    // }
}