package com.capone.ccapi.resources;

import com.capone.ccapi.core.Journal;
import com.capone.ccapi.db.JournalDAO;
import com.capone.ccapi.util.LedgerService;
import io.dropwizard.hibernate.UnitOfWork;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.*;
import io.dropwizard.jersey.params.LongParam;
import java.util.List;
import static java.util.Objects.requireNonNull;

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
		Journal createdJournal = journalDAO.create(journal);
		LedgerService.getInstance().createLedgers(createdJournal);
		return createdJournal;
	}

	@GET
	@Path("/{id}")
	@UnitOfWork
	public List<Journal> getAllJournalsForAccount(@PathParam("id") LongParam accountId) {
		return journalDAO.findJournalsByAccountId(accountId.get());
	}

}
