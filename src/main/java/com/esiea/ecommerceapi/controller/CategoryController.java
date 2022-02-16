package com.esiea.ecommerceapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.esiea.ecommerceapi.model.Category;
import com.esiea.ecommerceapi.model.Product;
import com.esiea.ecommerceapi.service.CategoryService;
import com.esiea.ecommerceapi.service.ProductService;
import com.esiea.ecommerceapi.transformer.category.CategoryFull;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("api/private/category")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ProductService productService;
	
	@GetMapping("")
	public List<CategoryFull> getCategories() {
		return categoryService.getCategories();
	}
	
	@PostMapping("/{idCategory}/{idProduct}")
	public void addProductToCategory(
			@PathVariable(name = "idCategory") Integer idCategory,
			@PathVariable(name = "idProduct") Integer idProduct) {
		
		Category category = categoryService.getEntityCategory(idCategory).get();
		Product product = productService.getEntityProduct(idProduct).get();
		
		category.addProduct(product);
		
		categoryService.saveCategory(category);
	
	}
	
}
