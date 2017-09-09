package com.capone.ccapi.resources;

import com.capone.ccapi.core.Journal;
import com.capone.ccapi.db.JournalDAO;
import io.dropwizard.hibernate.UnitOfWork;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.*;

@Path("/journals")
@Produces(MediaType.APPLICATION_JSON)
public class JournalResource 
{
	private final JournalDAO journalDAO;

	public JournalResource(JournalDAO journalDAO) {
		this.journalDAO = journalDAO;
	}

	@POST
	@UnitOfWork
	public Journal createJournal(Journal journal) {
		return journalDAO.create(journal);
	}

}
