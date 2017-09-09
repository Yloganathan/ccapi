package com.capone.ccapi.db;

import com.capone.ccapi.core.Journal;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import java.util.Optional;

public class JournalDAO extends AbstractDAO<Journal>
{
	public JournalDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public Optional<Journal> findById(long id) {
		return Optional.ofNullable(get(id));
	}

	public Journal create(Journal journal) {
		return persist(journal);
	}
}