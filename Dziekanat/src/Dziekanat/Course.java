package Dziekanat;

import java.util.ArrayList;

public class Course {
	public String code;
	public ArrayList<String> subjects;
	public ArrayList<String> subjectTypes;

	public Course(String code) {
		this.code = code;
		this.subjects = new ArrayList<>();
		this.subjectTypes = new ArrayList<>();
	}
}
