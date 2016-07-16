package com.inacio.boueres.fizz.buzz.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class Rules implements Serializable{
	
	private static final long serialVersionUID = 1559641595489252202L;
	
	private BigDecimal id;
	private String desc;
	
	public BigDecimal getId() {
		return id;
	}
	public void setId(BigDecimal id) {
		this.id = id;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	

}
