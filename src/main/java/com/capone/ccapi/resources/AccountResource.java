package com.capone.ccapi.resources;

import com.capone.ccapi.core.Account;
import com.capone.ccapi.db.AccountDAO;
import io.dropwizard.hibernate.UnitOfWork;
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
	public Account getAccount(@PathParam("id") LongParam accountId) {
		return findSafely(accountId.get());
	}

	private Account findSafely(long accountId) {
        return accountDAO.findById(accountId).orElseThrow(() -> new NotFoundException("No such account."));
    }
}
