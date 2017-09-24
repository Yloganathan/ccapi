package com.capone.ccapi.db;

import com.capone.ccapi.core.Account;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import java.util.Optional;

public class AccountDAO extends AbstractDAO<Account>
{
    public AccountDAO(SessionFactory sessionFactory) {
	super(sessionFactory);
    }

    public Optional<Account> findById(long id) {
	return Optional.ofNullable(get(id));
    }

    public Account create(Account account) {
	return persist(account);
    }
}
