package models;

public class Student {
    private int id;
    private String name;
    private int age;
    private String gender;
    private String city;
    private String branch;
    private int[] marks = new int[5];
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public int[] getMarks() {
		return marks;
	}
	public void setMarks(int[] marks) {
		this.marks = marks;
	}
	public Student(int id, String name, int age, String gender, String city, String branch, int[] marks) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.city = city;
		this.branch = branch;
		this.marks = marks;
	}
	@Override
	public String toString() {
	    return "Student ID: " + id +
	           ", student name: " + name;
	}


}
