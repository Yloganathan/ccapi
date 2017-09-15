package com.capone.ccapi.util;

public class LedgerMap
{
    private String creditLedger;
    private String debitLedger;

    protected LedgerMap(){}

    public String getCreditLedger(){
	return creditLedger;
    }

    public LedgerMap setCreditLedger(String creditLedger){
	this.creditLedger = creditLedger;
	return this;
    }

    public String getDebitLedger(){
	return debitLedger;
    }
	
    public LedgerMap setDebitLedger(String debitLedger){
	this.debitLedger = debitLedger;
	return this;
    }

    public static LedgerMap create()
    {
        return new LedgerMap();
    }
}
