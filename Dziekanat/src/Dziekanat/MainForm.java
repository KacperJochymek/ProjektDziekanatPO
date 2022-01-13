package Dziekanat;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainForm {
	private ArrayList<Student> students;
	private ArrayList<Course> courses;

	private JPanel panel;
	private JComboBox comboBoxIndeks1;
	private JComboBox comboBoxOcena;
	private JButton dodajOceneButton;
	private JComboBox comboBoxIndeks2;
	private JComboBox comboBoxKierunek2;
	private JTable tablePrzedmioty;
	private JLabel labelImie;
	private JLabel labelNazwisko;
	private JLabel labelKierunek;
	private JLabel labelSrednia;
	private JLabel labelZaliczenie;
	private JTextField textFieldImie;
	private JTextField textFieldNazwisko;
	private JTextField textFieldIndeks;
	private JButton dodajStudentaButton;
	private JButton dodajKierunekButton;
	private JTextField textFieldKierunek1;
	private JButton dodajPrzedmiotButton;
	private JTextField textFieldPrzedmiot;
	private JComboBox comboBoxForma;
	private JComboBox comboBoxKierunek1;
	private JLabel labelSredniaKierunku;

	public MainForm() {
		students = new ArrayList<>();
		courses = new ArrayList<>();

		comboBoxOcena.addItem("2.0");
		comboBoxOcena.addItem("3.0");
		comboBoxOcena.addItem("3.5");
		comboBoxOcena.addItem("4.0");
		comboBoxOcena.addItem("4.5");
		comboBoxOcena.addItem("5.0");
		comboBoxOcena.addItem("5.5");

		comboBoxForma.addItem("Wykład");
		comboBoxForma.addItem("Ćwiczenia");
		comboBoxForma.addItem("Laboratorium");
		comboBoxForma.addItem("Projekt");

		DefaultTableModel dtm = new DefaultTableModel(0, 0);
		String headers[] = new String[] { "Przedmioty", "Forma zajęć"};
		dtm.setColumnIdentifiers(headers);
		tablePrzedmioty.setModel(dtm);

		dodajStudentaButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = textFieldImie.getText();
				String surname = textFieldNazwisko.getText();
				String indexS = textFieldIndeks.getText();
				String course = comboBoxKierunek1.getSelectedItem().toString();

				if(!name.isEmpty() && !surname.isEmpty() && !indexS.isEmpty() && !course.isEmpty()) {
					int index = Integer.parseInt(indexS);
					students.add(new Student(name, surname, index, course));

					comboBoxIndeks1.addItem(indexS);
					comboBoxIndeks2.addItem(indexS);
				}
			}
		});

		dodajKierunekButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String newCourse = textFieldKierunek1.getText();
				if(!newCourse.isEmpty()) {
					courses.add(new Course(newCourse));
					comboBoxKierunek1.addItem(newCourse);
					comboBoxKierunek2.addItem(newCourse);
				}
			}
		});

		dodajPrzedmiotButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String courseC = comboBoxKierunek2.getSelectedItem().toString();
				String subjectName = textFieldPrzedmiot.getText();
				String subjectForm = comboBoxForma.getSelectedItem().toString();

				if(!subjectName.isEmpty() && !subjectForm.isEmpty()) {
					for(Course course : courses) {
						if(course.code.equals(courseC)) {
							course.subjects.add(subjectName);
							course.subjectTypes.add(subjectForm);
							comboBoxKierunek2.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
							return;
						}
					}
				}
			}
		});

		dodajOceneButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String index = comboBoxIndeks1.getSelectedItem().toString();
				int indexValue = Integer.parseInt(index);
				String grade = comboBoxOcena.getSelectedItem().toString();
				double gradeValue = Double.parseDouble(grade);

				int dialogResult = JOptionPane.showConfirmDialog (null,
						"Czy na pewno chcesz dodać tę ocenę?","Dodawanie oceny", JOptionPane.YES_NO_OPTION);
				if(dialogResult == JOptionPane.YES_OPTION){
					for (Student student : students) {
						if (student.index == indexValue) {
							student.grades.add(gradeValue);

							comboBoxIndeks2.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
							comboBoxKierunek2.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
							return;
						}
					}
				}
			}
		});

		comboBoxIndeks2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String indexS = comboBoxIndeks2.getSelectedItem().toString();
				int indexValue = Integer.parseInt(indexS);

				for(Student s : students) {
					if(s.index == indexValue) {
						labelImie.setText(s.name);
						labelNazwisko.setText(s.surname);
						labelKierunek.setText(s.degreeCourse);

						double average = 0;
						boolean positiveGrades = true;
						for(double grade : s.grades) {
							average += grade;
							if(grade < 3.0) {
								positiveGrades = false;
							}
						}
						average /= s.grades.size();

						labelSrednia.setText(String.valueOf(average));
						labelZaliczenie.setText(positiveGrades ? "Tak" : "Nie");
					}
				}
			}
		});

		comboBoxKierunek2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String courseS = comboBoxKierunek2.getSelectedItem().toString();

				for(Course course : courses) {
					if(course.code.equals(courseS)) {
						DefaultTableModel dtm = new DefaultTableModel(0, 0);
						String headers[] = new String[] { "Przedmioty", "Forma zajęć"};
						dtm.setColumnIdentifiers(headers);
						tablePrzedmioty.setModel(dtm);

						for (int i = 0; i < course.subjects.size(); i++) {
							dtm.addRow(new Object[] {course.subjects.get(i), course.subjectTypes.get(i)});
						}

						double sum = 0;
						double gradeCounter = 0;
						for(Student s : students) {
							if(s.degreeCourse.equals(course.code)) {
								for(double grade : s.grades) {
									sum += grade;
									gradeCounter++;
								}
							}
						}

						if(gradeCounter == 0) {
							labelSredniaKierunku.setText("-");
						}
						else {
							labelSredniaKierunku.setText(String.valueOf(sum / (double)gradeCounter));
						}

						return;
					}
				}
			}
		});
	}


	public static void main(String[] args) {
		JFrame frame = new JFrame("Dziekanat");
		frame.setContentPane(new MainForm().panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
}
