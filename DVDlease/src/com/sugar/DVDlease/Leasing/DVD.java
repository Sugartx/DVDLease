package com.sugar.DVDlease.Leasing;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.sugar.DVDlease.Debtor.Member;

enum Status {
	leased, unleased
}

public class DVD implements Serializable{
	String name;
	String id;
	String length;
	public Status state;
	public Member debtor;
	public int money;
	Time starttime;
	Time endtime;

	public DVD(String name, String id, String length, int money) {
		this.name = name;
		this.id = id;
		this.length = length;
		this.money = money;
		state = Status.unleased;
	}

	public void setTime() {
		Date date = new Date();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String time = format.format(date);
		String[] t=time.split("-");
		starttime = new Time(Integer.valueOf(t[0]), Integer.valueOf(t[1]), Integer.valueOf(t[2]));
		endtime = new Time(starttime.year, starttime.mouth, starttime.day);
		endtime.TimePlus(30);
		starttime.timeLegalization();
		endtime.timeLegalization();
	}

	public String getName() {
		return name;
	}

	public int getMoney() {
		return money;
	}

	public String getId() {
		return id;
	}

	public String getLength() {
		return length;
	}

	public String getStringState() {
		return state.toString();
	}

	public String getStarttime() {
		return starttime.toString();
	}

	public String getEndtime() {
		return endtime.toString();
	}

	public Member getDebtor() {
		return debtor;
	}

	public Boolean isUnleased() {
		if (state == Status.unleased) {
			return true;
		} else {
			return false;
		}
	}

	public void changeStatetoleased() {
		state = Status.leased;
	}

	public void changeStatetounleased() {
		state = Status.unleased;
	}

	public void setDebtor(Member debtor) {
		this.debtor = debtor;
	}

	public boolean isOvertime(Time returntime) {
		if (!returntime.TimeGreater(endtime)) {
			System.out.println("Overtime");
			return true;
		}
		return false;
	}

	public void printDVD() {
		System.out.println(toString());
	}

	public String toString() {
		return name + "\t" + id + "\t" + length + "\t" + money + "\t" + state;
	}
}
