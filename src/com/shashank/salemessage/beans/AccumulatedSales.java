package com.shashank.salemessage.beans;

import java.math.BigDecimal;

public class AccumulatedSales {

	private Long totalSale;
	private BigDecimal totalValue;
	
	public Long getTotalSale() {
		return totalSale;
	}
	public void setTotalSale(Long totalSale) {
		this.totalSale = totalSale;
	}
	public BigDecimal getTotalValue() {
		return totalValue;
	}
	public void setTotalValue(BigDecimal totalValue) {
		this.totalValue = totalValue;
	}
	public AccumulatedSales(Long totalSale, BigDecimal value) {
		this.totalSale = totalSale;
		this.totalValue = value.multiply(BigDecimal.valueOf(totalSale));
	}
	
	public AccumulatedSales addSale(Long sales, BigDecimal value) {
		this.totalSale+=sales;
		this.totalValue = this.totalValue.add(value.multiply(BigDecimal.valueOf(sales)));
		return this;
	}
	
	public void addAmount(BigDecimal amount) {
		this.totalValue = this.totalValue.add(amount.multiply(BigDecimal.valueOf(totalSale)));
	}
	
	public void subtractAmount(BigDecimal amount) {
		this.totalValue = this.totalValue.subtract(amount.multiply(BigDecimal.valueOf(totalSale)));
	}
	
	public void multiplyAmount(BigDecimal factor) {
		this.totalValue = this.totalValue.multiply(factor);
	}
}
