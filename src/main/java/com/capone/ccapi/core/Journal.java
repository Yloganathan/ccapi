package com.capone.ccapi.core;

import javax.persistence.*;
import javax.persistence.GenerationType;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@Entity
@Table(name = "journals")
public class Journal
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	@Column(name ="accountid", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private long accountId;

	@Column(name ="transactiontype", nullable = false)
    @JsonProperty("type")
	private String transactionType;

    @Column(name ="createdAt")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Timestamp timeStamp; 

	@Column(name ="amount", nullable = false)
	private double amount;


	public Journal() {
        this.timeStamp = new Timestamp(System.currentTimeMillis());
    }

    public Journal(long accountid, String type, double amount) {
        this.accountId = accountid;
        this.transactionType = type;
        this.amount = amount;
        this.timeStamp = new Timestamp(System.currentTimeMillis());
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

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public double getAmount() {
    	return amount;
    }

    public void setAmount(double amount) {
    	this.amount = amount;
    }

    public String getTimeStamp() {
        if(timeStamp != null) {
            return new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(timeStamp);
        } else {
            return "No Time Provided";
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Journal)) {
            return false;
        }

        final Journal that = (Journal) o;

        return Objects.equals(this.id, that.id)&&
            Objects.equals(this.accountId, that.accountId)&&
            Objects.equals(this.transactionType, that.transactionType)&&
            Objects.equals(this.amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accountId, transactionType, amount);
    }

    @Override
    public String toString()
    {
        return "Journal{" +
            "id=" + id +
            ", accountId='" + accountId + '\'' +
            ", transactiontype='" + transactionType + '\'' +
            ", amount='" + amount + '\'' +
            '}';
    }

}