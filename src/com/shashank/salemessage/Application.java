package com.shashank.salemessage;

import java.util.Scanner;

import com.shashank.salemessage.services.MessageProcessor;

public class Application {

	private static MessageProcessor messageProcessor = new MessageProcessor();
	
	public static void main(String[] args) {
		try(Scanner scanner = new Scanner(System.in)){
			while (messageProcessor.acceptingMessages()) {
				messageProcessor.consumeMessage(scanner.nextLine());
			}
		}
	}

}
