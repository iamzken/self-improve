package com.gupaoedu.vip.spring.transaction.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_member")
public class Member implements Serializable {

	@Id private Long id;
	private String name;
	private String addr;
	private Integer age;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) { this.name = name; }

	public String getAddr() { return addr; }
	public void setAddr(String addr) { this.addr = addr; }

	public Integer getAge() { return age; }
	public void setAge(Integer age) { this.age = age; }

	public Member(String name, String addr, Integer age) {
		this.name = name;
		this.addr = addr;
		this.age = age;
	}

	public Member() {
	}

	@Override
	public String toString() {
		return "Member{" +
				"id=" + id +
				", name='" + name + '\'' +
				", addr='" + addr + '\'' +
				", age=" + age +
				'}';
	}
}
