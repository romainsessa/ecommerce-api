package com.esiea.ecommerceapi.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.esiea.ecommerceapi.service.NotAllowedException;
import com.esiea.ecommerceapi.service.NotFoundException;
import com.esiea.ecommerceapi.service.ProductService;
import com.esiea.ecommerceapi.transformer.product.ProductFull;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("api/private/product")
public class ProductController {

	@Autowired
	private ProductService productService;

	@GetMapping("")
	public List<ProductFull> getProducts() {
		return productService.getProducts();
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProductFull> getProduct(@PathVariable("id") Integer id) {
		try {
			ProductFull p = productService.getProduct(id);
			return new ResponseEntity<ProductFull>(p, HttpStatus.OK);
		} catch (NotFoundException e) {
			return new ResponseEntity<ProductFull>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("")
	public ResponseEntity<ProductFull> addProduct(@RequestBody Product product) {
		try {
			ProductFull productF = productService.create(product);
			return new ResponseEntity<>(productF, HttpStatus.OK);
		} catch (NotAllowedException e) {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable("id") Integer id) {
		try {
			productService.deleteProduct(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (NotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("")
	public ResponseEntity<ProductFull> replaceProduct(@RequestBody Product product) {
		try {
			ProductFull productF =  productService.update(product);
			return new ResponseEntity<ProductFull>(productF, HttpStatus.OK);
		} catch (NotFoundException e) {
			return new ResponseEntity<ProductFull>(HttpStatus.NOT_FOUND);
		}
	}

	@PatchMapping("")
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
			existingProduct = productService.update(existingProduct);
			return new ResponseEntity<ProductFull>(existingProduct, HttpStatus.OK);
		} catch (NotFoundException e) {
			return new ResponseEntity<ProductFull>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/name/{name}")
	public ResponseEntity<Iterable<ProductFull>> getProductsByName(@PathVariable("name") String name) {
		try {
			Iterable<ProductFull> products = productService.getProductsByName(name);
			return new ResponseEntity<Iterable<ProductFull>>(products, HttpStatus.OK);
		} catch (NotFoundException e) {
			return new ResponseEntity<Iterable<ProductFull>>(HttpStatus.NOT_FOUND);
		}
	}

}
