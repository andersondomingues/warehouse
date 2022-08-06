package com.warehouse.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "hello_world", uniqueConstraints = {
  @UniqueConstraint(columnNames = "id")
}) public class HelloWorld {

	@Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", unique = true, nullable = false)
	private int id; 

  @Column(name = "hello", unique = true, nullable = false, length = 50)
	private String hello;

  @Column(name = "world", unique = true, nullable = false, length = 50)
	private String world;

	public HelloWorld(String hello, String world) {
		this.hello = hello;
		this.world = world;
	}

	public HelloWorld(){
		this.hello = "hello";
		this.world = "world";
	}

	public int getId(){
		return this.id;
	}

	public void setId(int id){
		this.id = id;
	}

	public String getHello(){
		return this.hello;
	}

	public void setHello(String hello){
		this.hello = hello;
	}

	public String getWorld(){
		return this.world;
	}

	public void setWorld(String world){
		this.world = world;
	}

	@Override
	public String toString(){
		return this.hello + " " + this.world;
	}

}
