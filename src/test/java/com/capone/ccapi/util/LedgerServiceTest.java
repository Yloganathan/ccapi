package com.capone.ccapi.util;

import com.capone.ccapi.core.Ledger;
import com.capone.ccapi.core.Journal;
import com.capone.ccapi.db.LedgerDAO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.anyString;


public class LedgerServiceTest {

    private static final LedgerDAO ledgerDAO = mock(LedgerDAO.class);

	@Captor
    private ArgumentCaptor<Ledger> ledgerCaptor;
	
    @Before
    public void setUp() {
    	ledgerCaptor = ArgumentCaptor.forClass(Ledger.class);
    	LedgerService.createLedgerService(ledgerDAO);
    }

    @After
    public void tearDown() {
        reset(ledgerDAO);
    }

    @Test
    public void createLedgers() {
        when(ledgerDAO.create(any(Ledger.class))).thenReturn(new Ledger());
        LedgerService.getInstance().createLedgers(new Journal(1,"purchase",500));
        verify(ledgerDAO, times(2)).create(ledgerCaptor.capture());
        assertThat(ledgerCaptor.getValue()).isEqualTo(new Ledger(1,"Cash-Out", 0, 500));
    }

	@Test
    public void createLedgersFailureCase() {
        when(ledgerDAO.create(any(Ledger.class))).thenReturn(new Ledger());
        LedgerService.getInstance().createLedgers(new Journal(1,"sometype",500));
        verify(ledgerDAO, never()).create(ledgerCaptor.capture());
    }

    @Test
    public void sumOfAllPrincipal() {
    	when(ledgerDAO.findByLedgerTypeAndAccountId(anyString(), anyLong())).thenReturn(new ArrayList<Ledger>(
            Arrays.asList(new Ledger(1,"purchase",200,0), new Ledger(1,"purchase",300,0), new Ledger(1,"purchase",211,0))));
    	 double sum = LedgerService.getInstance().getSumOfPrincipal(1L);
    	 assertThat(sum).isEqualTo(711.00);
    }


} 