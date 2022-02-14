package com.esiea.ecommerceapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esiea.ecommerceapi.model.Category;
import com.esiea.ecommerceapi.repository.CategoryRepository;
import com.esiea.ecommerceapi.transformer.category.CategoryFull;
import com.esiea.ecommerceapi.transformer.category.CategoryTransformer;

@Service
public class CategoryService {

	@Autowired
	private CategoryTransformer categoryTransformer;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	public List<CategoryFull> getCategories() {
		return categoryTransformer.transform(categoryRepository.findAll());
	}

	public Optional<Category> getEntityCategory(Integer idCategory) {
		return categoryRepository.findById(idCategory);
	}
	
	public Category saveCategory(Category category) {
		return categoryRepository.save(category);
	}
	
}
