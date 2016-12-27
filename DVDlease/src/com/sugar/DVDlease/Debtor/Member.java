package com.sugar.DVDlease.Debtor;

import java.io.Serializable;
import java.util.ArrayList;

import com.sugar.DVDlease.Leasing.DVD;

enum Identity{
	DEBTOR,MANAGER,BOSS
}

public class Member implements Serializable{
	private String name;
	private String id;
	private String password;
	private double money;
	private double alreadycharge;
	private int vip;
	private Identity identity;
	private ArrayList<DVD> rent;

	public Member(String name, String id, String password) {
		this.name = name;
		this.id = id;
		this.password = password;
		alreadycharge = 0;
		money = 0;
		vip = 0;
		rent = new ArrayList<DVD>();
	}
	
	public Member() {
		this.name = "";
		this.id = "";
		this.password = "";
		alreadycharge = 0;
		money = 0;
		vip = 0;
		rent = new ArrayList<DVD>();
	}

	public String getName() {
		return name;
	}

	public String getId() {
		return id;
	}

	public String getPassword() {
		return password;
	}

	public double getMoney() {
		return money;
	}

	public int getVip() {
		return vip;
	}

	public ArrayList<DVD> getRent() {
		return rent;
	}
	public void setIdentityToManager(){
		identity=Identity.MANAGER;
	}
	public void setIdentityToDebtor(){
		identity=Identity.DEBTOR;
	}
	public void setIdentityToBOSS(){
		identity=Identity.BOSS;
	}
	public boolean isManager(){
		if(identity==Identity.MANAGER){
			return true;
		}
		return false;
	}
	public boolean isBoss(){
		if(identity==Identity.BOSS){
			return true;
		}
		return false;
	}

	public void addRent(DVD disk) {
		rent.add(disk);
	}

	public DVD findDVDfromid(String id){
		for(DVD disk:rent){
			if(disk.getId().equals(id)){
				return disk;
			}
		}
		return null;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	
	
	public void addMoney(double money) {
		this.money += money;
		this.alreadycharge += money;		
		vip=setVip();
	}

	public double discount(int vip) {
		switch (vip) {
		case 1:
			return 0.95;
		case 2:
			return 0.9;
		case 3:
			return 0.85;
		case 4:
			return 0.8;
		case 5:
			return 0.75;
		default:
			return 1;
		}
		
	}

	public boolean issufficient(double money) {
		if (this.money - money > 0) {
			return true;
		}
		System.out.println("insufficient money");
		return false;
	}

	public boolean isCustom(double money) {
		money *= discount(vip);
		if (issufficient(money)) {
			this.money -= money;
			System.out.println("custom success");
			return true;
		}
		return false;
	}

	public void punish(int money) {
		this.money -= money;
	}

	public int setVip() {
		if (alreadycharge < 18) {
			return 0;
		} else if (alreadycharge < 48) {
			return 1;
		} else if (alreadycharge < 98) {
			return 2;
		} else if (alreadycharge < 168) {
			return 3;
		} else if (alreadycharge < 328) {
			return 4;
		} else {
			return 5;
		}
	}
}
