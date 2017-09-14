package com.capone.ccapi.api;

import java.util.*;
import com.capone.ccapi.core.Journal;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AccountSummary
{
	private long id;
	private double principal;
	@JsonProperty("transactions")
	private List<Journal> relatedTransactions;

    public AccountSummary() {}
    
	public AccountSummary(long id) {
		this.id = id;
	}

	public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

	public double getPrincipal() {
        return principal;
    }

    public void setPrincipal(double amount) {
        this.principal = amount;
    }

    public List<Journal> getRelatedTransactions() {
    	return relatedTransactions;
    }

    public void setRelatedTransactions(List<Journal> relatedTransactions) {
    	this.relatedTransactions = relatedTransactions;
    }
}