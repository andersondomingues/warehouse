package com.warehouse.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "storage_item", uniqueConstraints = {
  @UniqueConstraint(columnNames = "id")
}) public class StorageItem {

	@Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", unique = true, nullable = false)
	private Integer id; 

  @Column(name = "name", unique = true, nullable = false, length = 50)
	private String name;

  @Column(name = "quantity", unique = true, nullable = false)
	private Integer quantity;

	@Column(name = "weight", unique = true, nullable = false)
	private Integer weight;

	public StorageItem(String name, int quantity, int weight) {
		this.name = name;
		this.quantity = quantity;
		this.weight = weight;
	}

	public StorageItem(){
		
	}

	public Integer getId(){
		return this.id;
	}

	public void setId(Integer id){
		this.id = id;
	}

	public String getName(){
		return this.name;
	}

	public void setName(String name){
		this.name = name;
	}

	public Integer getQuantity(){
		return this.quantity;
	}

	public void setQuantity(Integer quantity){
		this.quantity = quantity;
	}

	public Integer getweight(){
		return this.weight;
	}

	public void setweight(Integer weight){
		this.weight = weight;
	}

}
