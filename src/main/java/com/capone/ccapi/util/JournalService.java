package com.capone.ccapi.util;

import com.capone.ccapi.db.JournalDAO;
import com.capone.ccapi.core.Journal;
import java.util.*;

public class JournalService 
{
    private static JournalService accountServiceInstance;
    private final JournalDAO journalDAO;

    public JournalService(JournalDAO journalDAO) {
	this.journalDAO = journalDAO;
    }

    public static void createJournalService(JournalDAO journalDAO) {
	accountServiceInstance = new JournalService(journalDAO);
    }

    public static JournalService getInstance() {
	return accountServiceInstance;
    }

    public List<Journal> getJournalsRelatedToAccount(long accountId) {
	return journalDAO.findJournalsByAccountId(accountId);
    }

}
