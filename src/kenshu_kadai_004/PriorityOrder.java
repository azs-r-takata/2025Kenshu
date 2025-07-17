package kenshu_kadai_004;

import java.util.Date;

public class PriorityOrder {
	String name;
	int priority;
	String deadline;
	Date date;
	
	public PriorityOrder(String name, int priority, String deadline, Date date) {
		this.name = name;
		this.priority = priority;
		this.deadline = deadline;
		this.date = date;
	}
}
