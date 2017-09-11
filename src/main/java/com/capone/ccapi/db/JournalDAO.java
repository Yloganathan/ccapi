package com.capone.ccapi.db;

import com.capone.ccapi.core.Journal;
import com.capone.ccapi.core.Journal_;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import javax.persistence.criteria.*;
import java.util.*;

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

	public List<Journal> findJournalsByAccountId(long accountId) {
		CriteriaBuilder builder = currentSession().getCriteriaBuilder();
        CriteriaQuery<Journal> criteria = builder.createQuery( Journal.class );
        Root<Journal> JournalRoot = criteria.from(Journal.class);
        criteria.select( JournalRoot);
        criteria.where( builder.equal( JournalRoot.get( Journal_.accountId ), accountId ));
        return list(criteria);
	}
}