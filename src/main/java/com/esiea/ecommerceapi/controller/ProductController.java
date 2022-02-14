package com.esiea.ecommerceapi.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.esiea.ecommerceapi.model.Product;
import com.esiea.ecommerceapi.service.NotFoundException;
import com.esiea.ecommerceapi.service.ProductService;
import com.esiea.ecommerceapi.transformer.product.ProductFull;

@RestController
@RequestMapping("api/private")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@GetMapping("/product")
	public List<ProductFull> getProducts() {
		return productService.getProducts();
	}

	@GetMapping("/product/{id}")
	public ResponseEntity<ProductFull> getProduct(@PathVariable("id") Integer id) {
		try {
			ProductFull p = productService.getProduct(id);
			return new ResponseEntity<ProductFull>(p, HttpStatus.OK);
		} catch (NotFoundException e) {
			return new ResponseEntity<ProductFull>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/product")
	public ProductFull addProduct(@RequestBody Product product) {
		return productService.upsert(product);
	}

	@DeleteMapping("/product/{id}")
	public void deleteProduct(@PathVariable("id") Integer id) {
		productService.deleteProduct(id);
	}

	@PutMapping("/product")
	public ProductFull replaceProduct(@RequestBody Product product) {
		return productService.upsert(product);
	}

	@PatchMapping("/product")
	public ResponseEntity<ProductFull> partialReplaceProduct(@RequestBody Product product) {
		try {
			ProductFull existingProduct = productService.getProduct(product.getId());
			if (product.getName() != null && !product.getName().equals(existingProduct.getName())) {
				existingProduct.setName(product.getName());
			}
			if (product.getDescription() != null
					&& !product.getDescription().equals(existingProduct.getDescription())) {
				existingProduct.setDescription(product.getDescription());
			}
			if (product.getCost() != null && !product.getCost().equals(existingProduct.getCost())) {
				existingProduct.setCost(product.getCost());
			}
			existingProduct = productService.upsert(existingProduct);
			return new ResponseEntity<ProductFull>(existingProduct, HttpStatus.OK);
		} catch (NotFoundException e) {
			return new ResponseEntity<ProductFull>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/product/filter/{name}")
	public Iterable<ProductFull> getProductsByName(@PathVariable("name") String name) {
		return productService.getProductsByName(name);
	}

}
