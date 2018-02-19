package com.shashank.salemessage.services;

import java.math.BigDecimal;

import com.shashank.salemessage.beans.Sale;

public class MessageProcessor {

	private int messageCount;
	private SaleRecords saleRecords;
	
	public MessageProcessor() {
		this.messageCount=0;
		this.saleRecords= new SaleRecords();
	}
	
	public void consumeMessage(String message) {
		if(this.messageCount<50) {
			
			try {
				processMessage(message.trim().toLowerCase());
				
				this.messageCount++;
				if(this.messageCount%10==0) {
					this.saleRecords.printSales();
					if(this.messageCount==50) {
						System.out.println("System is pausing...");
						this.saleRecords.printAdjustments();
					}
				}
			} catch (Exception e) {
				// Ignoring parsing exceptions
			}
		}
		
	}

	private void processMessage(String message) throws Exception {
		String[] parts = message.split(" sales of ");
		
		if(parts.length==2) {
			saleRecords.addSales(getSale(parts[1]), Long.parseLong(parts[0]));
		}else if(parts.length==1 && message.contains("at")) {
			saleRecords.addSales(getSale(parts[0]), 1L);
		}else if(parts.length==1) {
			processAdjustment(parts[0]);
		}else {
			throw new Exception("Invalid message");
		}
		
	}
	
	private void processAdjustment(String adjustmentMessage) throws Exception {
		String[] parts = adjustmentMessage.trim().split(" ",3);
		switch(parts[0]) {
			case "add": saleRecords.addValue(getPluralType(parts[2]), getValueInP(parts[1]));
						return;
			case "subtract": saleRecords.subtractValue(getPluralType(parts[2]), getValueInP(parts[1]));
						return;
			case "multiply": saleRecords.multiplyValue(getPluralType(parts[2]), getValueInP(parts[1]));
						return;
			default:
				break;
		}
	}
	
	private Sale getSale(String saleString) throws Exception {
		String[] parts = saleString.split(" at ");
		
		if(parts.length==2) {
			return new Sale(getPluralType(parts[0]),getValueInP(parts[1].replaceAll(" each", "")));
		}
		throw new Exception("Invalid message");
		
	}

	private BigDecimal getValueInP(String value) throws Exception {
		if(value.endsWith("p")) {
			return new BigDecimal(value.substring(0, value.length()-1));
		}else if(value.endsWith("£")) {
			return new BigDecimal(value.substring(0, value.length()-1)).multiply(new BigDecimal("100"));
		}
		throw new Exception("Invalid message");
	}
	
	public String getPluralType(String type) {
        String pluralType = "";
        String typeWithoutLastChar = type.substring(0, type.length() - 1);
        
        if (type.endsWith("o")) {
            pluralType = String.format("%soes", typeWithoutLastChar);
        } else if (type.endsWith("y")) {
            pluralType = String.format("%sies", typeWithoutLastChar);
        } else if (type.endsWith("h")) {
            pluralType = String.format("%shes", typeWithoutLastChar);
        } else if (!type.endsWith("s")) {
            pluralType = String.format("%ss", type);
        } else {
            pluralType = String.format("%s", type);
        }
        return pluralType.toLowerCase();
    }
	
	public boolean acceptingMessages() {
		return this.messageCount<50;
	}
	
}
