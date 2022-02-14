package com.esiea.ecommerceapi.transformer.product;

import java.util.ArrayList;
import java.util.List;

import com.esiea.ecommerceapi.transformer.category.CategoryLight;

public class ProductFull extends ProductLight {
	
	private List<CategoryLight> categories = new ArrayList<>();
	
	public List<CategoryLight> getCategories() {
		return categories;
	}
	public void setCategories(List<CategoryLight> categories) {
		this.categories = categories;
	}
	
	
}
