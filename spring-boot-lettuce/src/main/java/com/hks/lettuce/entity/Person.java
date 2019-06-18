package com.hks.lettuce.entity;

import java.io.Serializable;

public class Person implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private int age;
	public Person() { }
	public Person(int id, String name, int age) {
		this.id = id;
		this.name = name;
		this.age = age;
	}
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
	@Override
	public String toString() {
      return id +" - " + name + " - " + age;
	}
    @Override
    public boolean equals(final Object obj) {
      if (obj == null) {
         return false;
      }
      final Person person = (Person) obj;
      if (this == person) {
         return true;
      } else {
         return (this.name.equals(person.name) && this.age == person.age);
      }
    }
    @Override
    public int hashCode() {
      int hashno = 7;
      hashno = 13 * hashno + (name == null ? 0 : name.hashCode());
      return hashno;
    }
}
