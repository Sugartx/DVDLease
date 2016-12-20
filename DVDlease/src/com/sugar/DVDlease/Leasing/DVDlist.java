package com.sugar.DVDlease.Leasing;

import java.io.Serializable;
import java.util.ArrayList;

public class DVDlist implements Serializable{
	String name;
	public int leasecount;
	ArrayList<DVD> disklist;
	public DVDlist(String name){
		this.name=name;
		disklist=new ArrayList<DVD>();
	}
	public String getName(){
		return name;
	}
	public int getleasecount(){
		return leasecount;
	}
	public ArrayList<DVD> getdisklist(){
		return disklist;
	}
	public void addDVD(DVD disk){
		disklist.add(disk);
	}
	public void removeDVD(DVD disk){
		for(DVD d :disklist){
			if(d.id.equals(disk.id)){
				disklist.remove(d);
				break;
			}
		}
	}
	public boolean isEmpty(){
		return disklist.isEmpty();
	}
	public DVD getDVD(int index){
		return disklist.get(index);
	}
	public void printDVDlist(){
		System.out.println(name);
	}
}
