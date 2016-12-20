package com.sugar.DVDlease.LeaseGUI;

import java.util.Scanner;

import com.sugar.DVDlease.Debtor.Member;
import com.sugar.DVDlease.Debtor.Management;
import com.sugar.DVDlease.Leasing.DVD;

public class Menu {
	Scanner input; 
	Management member;
	public Menu(){
		input =new Scanner(System.in);
		member=new Management();
		mainMenu();
	}
	public void mainMenu(){
		int exit=0;
		while(exit==0){
			System.out.println(" main menu");
			System.out.println(" 1:log");
			System.out.println(" 2:register");
			System.out.println(" 3:search");
			System.out.println(" 0 exit");			
			int choose=input.nextInt();
			input.nextLine();
			switch(choose){
			case 1:
				loginmenu();
				continue;
			case 2:
				registermenu();
				continue;
			case 3:
				searchmenu();
				continue;
			case 1512480128:
				managermenu();
				continue;
			case 0:
				exit=1;
				break;
			default:break;
			}
		}	
	}

	private void managermenu() {
		int exit=0;
		while(exit==0){
			System.out.println(" manager menu");
			System.out.println(" 1:add DVD");
			System.out.println(" 2:remove DVD");
			System.out.println(" 3:remove debtor");
			System.out.println(" 0 exit");
			int choose=input.nextInt();
			input.nextLine();
			switch(choose){
			case 1:
				addDVDmenu();
				continue;
			case 2:
				removeDVDMenu();
				continue;
			case 3:
				removeDebtorMenu();
				continue;
			case 0:
				exit=1;
				break;
			default:break;
			}
		}
	}
	private void removeDebtorMenu() {
		System.out.println("remove the debtor");
		System.out.print("input the id:");
		String id=input.nextLine();
		member.removeDebtor(id);
	}
	private void addDVDmenu(){
		System.out.println("input the DVD");
		System.out.print("input the name:");
		String name=input.nextLine();
		System.out.print("input the id:");
		String id=input.nextLine();
		System.out.print("input the length:");
		String length=input.nextLine();
		System.out.print("input the money:");
		int money=input.nextInt();
		input.nextLine();
		member.addDVD(new DVD(name,id,length,money));	
	}
	private void removeDVDMenu(){
		System.out.println("remove the DVD");
		System.out.print("input the id:");
		String id=input.nextLine();
		member.removeDVD(id);
	}
	private void searchmenu() {
		System.out.print("input the name:");
		String str =input.nextLine();
		member.printDVD(str);
		
	}

	private void registermenu() {
		System.out.println();
		System.out.print("input the name:");
		String name=input.nextLine();
		System.out.print("input the id:");
		String id=input.nextLine();
		if(member.isDebtorIdExisted(id)){
			System.out.println("this id is already existed:");
		}
		else{
			System.out.print("input the password:");
			String password=input.nextLine();
			System.out.print("input the password again:");
			String passwordinspect=input.nextLine();
			if(password.equals(passwordinspect)){
				Member customer=new Member(name, id, password);
				member.addDebtor(customer);
				System.out.println("register succeeded");
			}
			else{	
				System.out.println("register failed");
			}
		}				
	}

	private void loginmenu() {		
		System.out.print("input the id:");
		String id=input.nextLine();
		if(member.isDebtorIdExisted(id)){
			System.out.print("input the password:");
			String password=input.nextLine();
			if(member.findDebtor(id).getPassword().equals(password)){
				System.out.println("login success");
				custommenu(member.findDebtor(id));
			}
			else{
				System.out.println("password wrong");
			}
		}
		else{
			System.out.println("not find the id");
		}
		
	}
	private void custommenu(Member debtor) {
		int exit=0;
		while(exit==0){
			System.out.println(" manager menu");
			System.out.println(" 1:check the balance");
			System.out.println(" 2:Recharge");
			System.out.println(" 3:lease DVD");
			System.out.println(" 4:return DVD");
			System.out.println(" 0 exit");
			int choose=input.nextInt();
			input.nextLine();
			switch(choose){
			case 1:
				System.out.println("your have "+debtor.getMoney());
				continue;
			case 2:
				System.out.println("input the money:");
				double money=input.nextDouble();
				debtor.addMoney(money);
				continue;
			case 3:
				System.out.println("input the DVD name:");
				String name=input.nextLine();
				member.leaseDVD(member.findDVDFromName(name), debtor);
				continue;
			case 4:
				System.out.println("input the DVD id:");
				String id=input.nextLine();
				member.returnDVD(member.findDVDFromId(id), debtor);
				continue;
			case 0:
				exit=1;
				break;
			}
		}
		
	}
}







