package Dziekanat;

import java.util.ArrayList;

public class Student {
	public String name;
	public String surname;
	public double index;
	public String degreeCourse;
	public ArrayList<Double> grades;

	public Student(String name, String surname, double index, String degreeCourse) {
		this.name = name;
		this.surname = surname;
		this.index = index;
		this.degreeCourse = degreeCourse;
		this.grades = new ArrayList<Double>();
	}
}
