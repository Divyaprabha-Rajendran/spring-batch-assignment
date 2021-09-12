package com.td.springassignment.model;

public class Record {
	
	private String tradeId;
	private String term;
	private String termName;
	private String tradeValue;
	private String currency;
	
	public Record() {
		super();
	}

	public Record(String tradeId, String term, String tradeValue,String currency ) {
		super();
		this.tradeId = tradeId;
		this.term = term;
		this.tradeValue = tradeValue;
		this.currency = currency;
	}

	
	
	public Record(String tradeId, String term, String tradeValue,String currency,  String termValue) {
		super();
		this.tradeId = tradeId;
		this.term = term;
		this.tradeValue = tradeValue;
		this.currency = currency;
		this.termName = termValue;

	}

	public String getTradeId() {
		return tradeId;
	}

	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public String getTermName() {
		return termName;
	}

	public void setTermName(String termValue) {
		this.termName = termValue;
	}

	public String getTradeValue() {
		return tradeValue;
	}

	public void setTradeValue(String tradeValue) {
		this.tradeValue = tradeValue;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	
	public String toString()
	{
		return this.getTradeId()+","+this.getTerm()+","+this.getTradeValue()+","+this.getCurrency()+","+this.getTermName();
	}

}
