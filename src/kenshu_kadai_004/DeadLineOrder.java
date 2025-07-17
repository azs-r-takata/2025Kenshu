package kenshu_kadai_004;

import java.util.Date;

public class DeadLineOrder {
	String name;
	int priority;
	String deadline;
	Date date;
	
	public DeadLineOrder(String name, int priority, String deadline, Date date) {
		this.name = name;
		this.priority = priority;
		this.deadline = deadline;
		this.date = date;
	}
}
