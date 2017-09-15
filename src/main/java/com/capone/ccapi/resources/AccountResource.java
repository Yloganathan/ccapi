package com.capone.ccapi.resources;

import com.capone.ccapi.core.Account;
import com.capone.ccapi.db.AccountDAO;
import com.capone.ccapi.api.AccountSummary;
import io.dropwizard.hibernate.UnitOfWork;
import com.capone.ccapi.util.LedgerService;
import com.capone.ccapi.util.JournalService;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.*;
import io.dropwizard.jersey.params.LongParam;

@Path("/accounts")
@Produces(MediaType.APPLICATION_JSON)
public class AccountResource 
{
    private final AccountDAO accountDAO;
	
    public AccountResource(AccountDAO accountDAO) {
	this.accountDAO = accountDAO;
    }

    @POST
    @UnitOfWork
    public Account createAccount(Account account) {
	return accountDAO.create(account);
    }

    @GET
    @Path("/{id}")
    @UnitOfWork
    public AccountSummary getAccount(@PathParam("id") LongParam accountId) {
	accountDAO.findById(accountId.get()).orElseThrow(() -> new NotFoundException("No such account."));
	AccountSummary summary = new AccountSummary(accountId.get());
	summary.setPrincipal(LedgerService.getInstance().getSumOfPrincipal(accountId.get()));
	summary.setRelatedTransactions(JournalService.getInstance().getJournalsRelatedToAccount(accountId.get()));
	return summary;
    }
}
