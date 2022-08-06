package com.warehouse.models;

public class HelloWorldModel {

	private final String hello;
	private final String world;

	public HelloWorldModel(String hello, String world) {
		this.hello = hello;
		this.world = world;
	}

	public String getHello(){
		return this.hello;
	}

	public String getWorld(){
		return this.world;
	}

	@Override
	public String toString(){
		return this.hello + " " + this.world;
	}

}
