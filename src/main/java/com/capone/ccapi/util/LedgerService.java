package com.capone.ccapi.util;

import com.capone.ccapi.db.LedgerDAO;
import com.capone.ccapi.core.Ledger;
import com.capone.ccapi.core.Journal;
import java.util.*;
import java.util.stream.Collectors;

public class LedgerService 
{
	private static HashMap<String, LedgerMap> transactionToLedgerMap = new HashMap<String, LedgerMap>(){{
		put("purchase" , LedgerMap.create()
						   		.setCreditLedger("Principal")
						   		.setDebitLedger("Cash-Out"));
	}};

	private static LedgerService ledgerServiceInstance;
	private final LedgerDAO ledgerDAO;

	public LedgerService(LedgerDAO ledgerDAO) {
		this.ledgerDAO = ledgerDAO;
	}

	public static void createLedgerService(LedgerDAO ledgerDAO) {
		ledgerServiceInstance = new LedgerService(ledgerDAO);
	}

	public static LedgerService getInstance() {
		return ledgerServiceInstance;
	}

	public void createLedgers(Journal journal) {
		LedgerMap cdmapping = transactionToLedgerMap.get(journal.getTransactionType().toLowerCase());
		if(cdmapping != null ) {
			ledgerDAO.create(new Ledger(journal.getAccountId(),cdmapping.getCreditLedger(),journal.getAmount(),0));
			ledgerDAO.create(new Ledger(journal.getAccountId(),cdmapping.getDebitLedger(),0,journal.getAmount()));
		}
	}

	public double getSumOfPrincipal(long accountId) {
		List<Ledger> principalLedgers = ledgerDAO.findByLedgerTypeAndAccountId("Principal", accountId);
		return principalLedgers.stream().collect(Collectors.summingDouble(o -> o.getCredit()));
	}

}