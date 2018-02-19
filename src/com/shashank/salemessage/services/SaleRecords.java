package com.shashank.salemessage.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.shashank.salemessage.beans.AccumulatedSales;
import com.shashank.salemessage.beans.Sale;

public class SaleRecords {

	private Map<String,AccumulatedSales> productTypeAccumulatedSales = new HashMap<>();
	private List<String> adjustments = new ArrayList<>();
	
	public void addSales(Sale sale, Long numOfSales) {
		
		productTypeAccumulatedSales.put(sale.getProductType(), 
				productTypeAccumulatedSales.getOrDefault(sale.getProductType(), new AccumulatedSales(numOfSales, sale.getValue())).addSale(numOfSales, sale.getValue()));
	}
	
	public void addValue(String productType, BigDecimal adjustAmount) {
		productTypeAccumulatedSales.getOrDefault(productType, new AccumulatedSales(0L, BigDecimal.ZERO)).addAmount(adjustAmount);
		
		
		adjustments.add(String.format("Added %.0fp to %d sales of %s", adjustAmount, 
				productTypeAccumulatedSales.getOrDefault(productType, new AccumulatedSales(0L, BigDecimal.ZERO)).getTotalSale(), productType));
	}
	
	public void subtractValue(String productType, BigDecimal adjustAmount) {
		productTypeAccumulatedSales.getOrDefault(productType, new AccumulatedSales(0L, BigDecimal.ZERO)).subtractAmount(adjustAmount);
		
		adjustments.add(String.format("Subtracted %.0fp from %d sales of %s", adjustAmount, 
				productTypeAccumulatedSales.getOrDefault(productType, new AccumulatedSales(0L, BigDecimal.ZERO)).getTotalSale(), productType));
	}

	public void multiplyValue(String productType, BigDecimal adjustFactor) {
		productTypeAccumulatedSales.getOrDefault(productType, new AccumulatedSales(0L, BigDecimal.ZERO)).multiplyAmount(adjustFactor);
		
		adjustments.add(String.format("Multiplied %.0f to %d sales of %s", adjustFactor, 
				productTypeAccumulatedSales.getOrDefault(productType, new AccumulatedSales(0L, BigDecimal.ZERO)).getTotalSale(), productType));
	}
	
	public void printSales() {
		System.out.println("\n\n==============================Sale Report==============================\n\n");
		System.out.println("Product Type \t\t\t\tTotal Sold\t\t\t\tTotal Value");
		
		productTypeAccumulatedSales.forEach((product,sale)->System.out.println(
				product  +  "\t\t\t\t\t\t"  +  sale.getTotalSale()  +  "\t\t\t\t\t\t"  +  sale.getTotalValue()
				) );
		
		System.out.println("=========================================================================\n\n");
	}
	
	public void printAdjustments() {
		System.out.println("\n\n=============================Adjustment Report==========================\n\n");
		adjustments.forEach(System.out::println);
		System.out.println("=========================================================================\n\n");
	}
}
