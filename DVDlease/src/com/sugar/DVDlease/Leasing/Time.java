package com.sugar.DVDlease.Leasing;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Time implements Serializable{
	int year;
	int mouth;
	int day;

	public Time(int year, int mouth, int day) {
		this.year = year;
		this.mouth = mouth;
		this.day = day;
	}

	public Time() {
		// 获取系统时间
	}

	public boolean TimeGreater(Time object) {
		if (object.year > year) {
			return false;
		} else if (object.mouth > mouth) {
			return false;
		} else if (object.day > day) {
			return false;
		}
		return true;
	}

	public int getYear() {
		return year;
	}

	public int getMouth() {
		return mouth;
	}

	public int getDay() {
		return day;
	}

	public Time getTime(){
		Date date = new Date();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String time = format.format(date);
		String[] t=time.split("-");
		Time systemTime=new Time(Integer.parseInt(t[0]),Integer.parseInt(t[1]),Integer.parseInt(t[2]));
		return systemTime;
	}
	
	public void TimePlus(int day) {
		this.day += day;
	}

	public String toString() {
		return year + "/" + mouth + "/" + day;
	}

	public boolean timeLegalization() {
		switch (mouth) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			if (day > 31) {
				day -= 31;
				mouth++;
				return timeLegalization();
			}
			return true;
		case 4:
		case 6:
		case 9:
		case 11:
			if (day > 30) {
				day -= 30;
				mouth++;
				return timeLegalization();
			}
			return true;
		case 2:
			if (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0)) {
				if (day > 29) {
					day -= 29;
					mouth++;
				} else {
					day -= 28;
					mouth++;
				}
				return timeLegalization();
			}
			return true;
		default:
			mouth -= 12;
			year++;
			return timeLegalization();
		}
	}
}
