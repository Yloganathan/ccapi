package com.capone.ccapi.core;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.sql.Timestamp;

@StaticMetamodel( Journal.class )
public class Journal_
{
    public static volatile SingularAttribute<Journal, Long> accountId;
    public static volatile SingularAttribute<Journal, String> transactionType;
    public static volatile SingularAttribute<Journal, Double> amount;
    public static volatile SingularAttribute<Journal, Timestamp> timeStamp;
    public static volatile SingularAttribute<Journal, Long> id;
}