package com.sugar.DVDlease.Debtor;

import java.io.Serializable;
import java.util.ArrayList;

import com.sugar.DVDlease.Leasing.*;

enum Status {
	leased, unleased
}

public class Management implements Serializable{
	public ArrayList<Member> debtorlist;
	public ArrayList<DVDlist> storeroomlist;

	public Management() {
		debtorlist = new ArrayList<Member>();
		storeroomlist = new ArrayList<DVDlist>();
	}

	public void addDebtor(Member debtor) {
		debtorlist.add(debtor);
	}

	public void addDVD(DVD disk) {
		int flag = 0;
		for (DVDlist list : storeroomlist) {
			if (list.getName().equals(disk.getName())) {
				list.addDVD(disk);
				flag = 1;
			}

		}
		if (flag == 0) {
			DVDlist disklist = new DVDlist(disk.getName());
			storeroomlist.add(disklist);
			disklist.addDVD(disk);
		}
	}

	public void removeDVD(String id) {
		if (isDVDIdExisted(id)) {
			this.findDVDlistFromId(id).removeDVD(findDVDFromId(id));
		} else {
			System.out.println("not find the DVD");
		}
	}

	public void removeDebtor(String id) {
		debtorlist.remove(findDebtor(id));
	}

	public boolean isDebtorIdExisted(String id) {
		for (Member d : debtorlist) {
			if (d.getId().equals(id)) {
				return true;
			}
		}
		return false;
	}

	public boolean isDVDIdExisted(String id) {
		for (DVDlist list : storeroomlist) {
			for (DVD disk : list.getdisklist()) {
				if (disk.getId().equals(id)) {
					return true;
				}
			}
		}
		return false;
	}

	public Member findDebtor(String id) {
		for (Member d : debtorlist) {
			if (d.getId().equals(id)) {
				return d;
			}
		}
		return null;
	}

	public DVDlist findDVDlistFromName(String name) {
		for (DVDlist list : storeroomlist) {
			if (list.getName().equals(name)) {
				return list;
			}
		}
		return null;
	}

	public DVDlist findDVDlistFromId(String id) {
		for (DVDlist list : storeroomlist) {
			if (list.getName().equals(findDVDFromId(id).getName())) {
				return list;
			}
		}
		return null;
	}

	public DVD findDVDFromName(String name) {
		for (DVDlist list : storeroomlist) {
			if (list.getName().equals(name)) {
				if (!list.isEmpty()) {
					return list.getDVD(0);
				}
			}
		}
		return null;
	}

	public DVD findDVDFromId(String id) {
		for (DVDlist list : storeroomlist) {
			for (DVD disk : list.getdisklist()) {
				if (disk.getId().equals(id)) {
					return disk;
				}
			}
		}
		return null;
	}

	public void printDVD(String str) {
		System.out.println("name\tid\tlength\tmoney");
		for (DVDlist list : storeroomlist) {
			if (list.getName().contains(str)) {
				for (DVD disk : list.getdisklist()) {
					disk.printDVD();
				}
			}
		}
	}

	public void leaseDVD(DVD disk, Member debtor) {
		for (DVDlist list : storeroomlist) {
			if (list.getName().equals(disk.getName())) {
				int flag = 0;
				for (DVD d : list.getdisklist()) {
					if (d.isUnleased()) {
						list.leasecount += 1;
						d.changeStatetoleased();
						d.setTime();// 获取系统时间
						d.setDebtor(debtor);// 填入借主的名字
						debtor.addRent(d);
						flag = 1;
						break;
					}
				}
				if (flag == 0) {
					System.out.println("DVD insufficient ");
				}
			}
		}
	}

	public void returnDVD(DVD disk, Member debtor) {
		Time returntime = new Time();// 获取系统时间
		returntime.getTime();
		DVD temp = new DVD("", "", "", 0);
		for (int i = 0; i < debtor.getRent().size(); i++) {
			temp = debtor.getRent().get(i);
			if (temp.equals(disk)) {
				if (!disk.isOvertime(returntime)) {
					temp.changeStatetounleased();
					temp.setTime();// 归零
					debtor.getRent().remove(temp);
				} else if (debtor.issufficient(disk.money)) {
					debtor.punish(disk.money);
					temp.changeStatetounleased();
					temp.setTime();// 归零
					debtor.getRent().remove(temp);
				} else {
					System.out.println("return failed");
				}
			}
		}
	}
}
