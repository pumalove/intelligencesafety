package com.example.intelligencetest.persons;

public class Person {
	
	//vars
	String lastname;
	String firstname;
	String position;
	String img;
	String phone;
	String email;
	int id;


	public Person(int id, String firstname, String lastname, String email,
			String img, String phone) {
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.img = img;
		this.phone = phone;
		this.email = email;
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}
