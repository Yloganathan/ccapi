package com.capone.ccapi.db;

import com.capone.ccapi.core.Ledger;
import com.capone.ccapi.core.Ledger_;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import javax.persistence.criteria.*;
import java.util.*;

public class LedgerDAO extends AbstractDAO<Ledger>
{
	public LedgerDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public Optional<Ledger> findById(long id) {
		return Optional.ofNullable(get(id));
	}

	public Ledger create(Ledger account) {
		return persist(account);
	}

	public List<Ledger> findByLedgerTypeAndAccountId(String type, long accountId) {
		CriteriaBuilder builder = currentSession().getCriteriaBuilder();
        CriteriaQuery<Ledger> criteria = builder.createQuery( Ledger.class );
        Root<Ledger> LedgerRoot = criteria.from(Ledger.class);
        criteria.select( LedgerRoot);
        // Predicate predicate1 = builder.equal( LedgerRoot.get( Ledger_.type ), type );
        // Predicate predicate2 = builder.equal( LedgerRoot.get( Ledger_.accountId ), accountId );
        criteria.where(builder.and(new Predicate[] { builder.equal( LedgerRoot.get( Ledger_.type ), type )
        	, builder.equal( LedgerRoot.get( Ledger_.accountId ), accountId )}) 
        	);
        //criteria.where( builder.equal( LedgerRoot.get( Ledger_.type ), type ) );
        return list(criteria);
	}
}