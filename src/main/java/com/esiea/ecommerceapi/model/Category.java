package com.esiea.ecommerceapi.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "categories")
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "category_id")
	private Integer categoryId;
	private String name;

//	@JsonIgnore // fix la récupération cyclique des éléments 
	@ManyToMany(
			fetch = FetchType.LAZY, // performance
			cascade = { 
					CascadeType.PERSIST, // création
					CascadeType.MERGE }) // modification
	@JoinTable(
			// nom de la table de jointure
			name = "category_product",  
			// clé étrangère dans la table de jointure correspondant à la clé primaire 
			// de la classe courante (category)
			joinColumns = @JoinColumn(name = "category_id"), 
			// clé étrangère dans la table de jointure correspondant à la clé primaire
			// de la classe en relation (product)
			inverseJoinColumns = @JoinColumn(name = "product_id"))
	private List<Product> products = new ArrayList<>();

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}
	
	// Helper methods
	public void addProduct(Product product) {
		this.products.add(product);
		product.getCategories().add(this);
	}
	
	public void removeProduct(Product product) {
		this.products.remove(product);
		product.getCategories().remove(this);
	}	

}