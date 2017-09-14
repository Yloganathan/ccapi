package com.capone.ccapi.core;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "ledgers")
public class Ledger
{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

	@Column(name ="accountid", nullable = false)
	private long accountId;

	@Column(name ="debit")
	private double debit;

	@Column(name ="credit")
	private double credit;

	@Column(name ="type")
	private String type;

    public Ledger(){}

    public Ledger(long accountId, String type, double credit, double debit) {
        this.accountId = accountId;
        this.type = type;
        this.credit = credit;
        this.debit = debit;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

	public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public double getDebit() {
    	return debit;
    }

    public void setDebit(double debit) {
    	this.debit = debit;
    }

    public double getCredit() {
    	return credit;
    }

    public void setCredit(double credit) {
    	this.credit = credit;
    }

	public String getType() {
    	return type;
    }

    public void setType(String type) {
    	this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ledger)) {
            return false;
        }

        final Ledger that = (Ledger) o;

        return Objects.equals(this.accountId, that.accountId)&&
            Objects.equals(this.debit, that.debit)&&
            Objects.equals(this.credit, that.credit)&&
            Objects.equals(this.type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, debit, credit, type);
    }
}