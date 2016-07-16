package com.inacio.boueres.fizz.buzz.entity;

import java.io.Serializable;
import java.util.List;

public class CustomParam implements Serializable{

	private static final long serialVersionUID = -2523694545220173297L;
	
	private String orderId;
	private String typeId;
	private List<Rules> rules;
	private String numbers;
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	public List<Rules> getRules() {
		return rules;
	}
	public void setRules(List<Rules> rules) {
		this.rules = rules;
	}
	public String getNumbers() {
		return numbers;
	}
	public void setNumbers(String numbers) {
		this.numbers = numbers;
	}
	
	
}
