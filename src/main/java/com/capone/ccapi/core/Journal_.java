package com.capone.ccapi.core;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel( Journal.class )
public class Journal_
{
    public static volatile SingularAttribute<Journal, Long> accountId;
}