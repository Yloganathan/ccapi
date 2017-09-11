package com.capone.ccapi.core;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel( Ledger.class )
public class Ledger_
{
    public static volatile SingularAttribute<Ledger, Long> accountId;
    public static volatile SingularAttribute<Ledger, String> type;
}