package com.cg.mypaymentapp.pl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.cg.mypaymentapp.beans.Customer;
import com.cg.mypaymentapp.beans.Transactions;
import com.cg.mypaymentapp.beans.Wallet;
import com.cg.mypaymentapp.exception.InsufficientBalanceException;
import com.cg.mypaymentapp.exception.InvalidInputException;
import com.cg.mypaymentapp.service.WalletService;

public class Client {

	public static void main(String[] args) {

		ApplicationContext context = new ClassPathXmlApplicationContext("projectBeans.xml");

		WalletService walletService = (WalletService)context.getBean("walletService");

		Client client = new Client();

		while(true) {
			
			System.out.println("1) Create Account");
			System.out.println("2) Show Balance");
			System.out.println("3) Deposit Amount");
			System.out.println("4) Withdraw Amount");
			System.out.println("5) Fund Transfer");
			System.out.println("6) Print Transactions");
			System.out.println("0) Exit Application");

			Scanner console = new Scanner(System.in);

			System.out.println("Enter your choice");
			int choice = console.nextInt();

			switch(choice) {

			case 1:

				Customer customer = new Customer();
				Wallet wallet = new Wallet();

				System.out.print("\nEnter name: ");
				String name = console.next();

				System.out.print("\nEnter mobileNumber: ");
				String mobileNumber = console.next();

				System.out.print("\nEnter Amount: ");
				BigDecimal amount = console.nextBigDecimal();

				try {
					customer = walletService.createAccount(name, mobileNumber, amount);
					System.out.println("Your account has successfully registered with mobile number " +  customer.getMobileNo());
				} catch (InvalidInputException e) {
					e.printStackTrace();
				}
				break;

			case 2:

				System.out.println("\nEnter mobile number");
				mobileNumber = console.next();

				try {
					customer = walletService.showBalance(mobileNumber);
					System.out.print("The balance in account " + customer.getName() + " is " + customer.getWallet().getBalance() + "\n");
				} catch (InvalidInputException e3) {
					e3.printStackTrace();
				}
				break;

			case 3:

				System.out.println("\nEnter mobile number");
				mobileNumber = console.next();

				System.out.println("\nEnter amount to be deposited");
				amount = console.nextBigDecimal();

				try {
					customer = walletService.depositAmount(mobileNumber, amount);
					System.out.println("Successfully deposited");
					System.out.println("Account balance is: " + customer.getWallet().getBalance());
				} catch (InvalidInputException e2) {
					e2.printStackTrace();
				}
				break;

			case 4:

				System.out.println("\nEnter mobile number");
				mobileNumber = console.next();

				System.out.println("\nEnter amount to be withdrawn");
				amount = console.nextBigDecimal();

				try {
					customer = walletService.withdrawAmount(mobileNumber, amount);
					System.out.println("Successfully withdrawn");
					System.out.println("Account balance is: " + customer.getWallet().getBalance());
				} catch (InvalidInputException e1) {
					e1.printStackTrace();
				} catch (InsufficientBalanceException e) {
					e.printStackTrace();
				}
				break;

			case 5:

				System.out.print("\nEnter source mobile number: ");
				String sourceMobile = console.next();

				System.out.print("\nEnter target mobile number: ");
				String targetMobile = console.next();

				System.out.println("\nEnter amount to be transferred");
				amount = console.nextBigDecimal();

				try {
					customer = walletService.fundTransfer(sourceMobile, targetMobile, amount);
					System.out.println("Amount has successfully transferred from account " + customer.getName());
					System.out.println("And now your balance is " + customer.getWallet().getBalance());

				} catch (InvalidInputException e) {
					e.printStackTrace();
				} catch (InsufficientBalanceException e) {
					e.printStackTrace();
				}
				break;
				
			case 6:
				
				System.out.println("\nEnter mobile Number");
				mobileNumber = console.next();
				
				try {
					List<Transactions> trans = new ArrayList<>();
					trans = walletService.getAllTransactions(mobileNumber);
					Iterator<Transactions> it = trans.iterator();
					System.out.println("\t\tMobile Number \t\tType \t\tAmount \t\tDate \t\tTransaction Status");
					
					while(it.hasNext()) {
						
						Transactions transactions = it.next();
						System.out.println(mobileNumber +  "\t");
						System.out.println(transactions.getTransactionType() + "\t");
						System.out.println(transactions.getAmount() + "\t");
						System.out.println(transactions.getDateResult() + "\t");
						System.out.println(transactions.getTransactionStatus() + "\t");
						System.out.println("");
					}
				} catch (InvalidInputException e) {
					e.printStackTrace();
				}
				break;

			case 0:

				System.out.println("Thank you for using our services");
				System.out.println("Good Bye");
				System.exit(0);

			default:
				System.out.println("Please enter valid choice");
				break;
			}	

		}
	}
}

