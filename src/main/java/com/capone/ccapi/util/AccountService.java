package com.capone.ccapi.util;

import com.capone.ccapi.db.AccountDAO;
import com.capone.ccapi.core.Account;
import java.util.*;

public class AccountService 
{
	private static AccountService accountServiceInstance;
	private final AccountDAO accountDAO;

	public AccountService(AccountDAO accountDAO) {
		this.accountDAO = accountDAO;
	}

	public static void createAccountService(AccountDAO accountDAO) {
		accountServiceInstance = new AccountService(accountDAO);
	}

	public static AccountService getInstance() {
		return accountServiceInstance;
	}

	public Boolean isAccountValid(long accountId) {
		System.out.print(accountDAO.findById(accountId).isPresent());
		return accountDAO.findById(accountId).isPresent();
	}

}