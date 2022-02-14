package com.esiea.ecommerceapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esiea.ecommerceapi.model.Product;
import com.esiea.ecommerceapi.repository.ProductRepository;
import com.esiea.ecommerceapi.transformer.product.ProductFull;
import com.esiea.ecommerceapi.transformer.product.ProductTransformer;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductTransformer productTransformer;

	public ProductFull upsert(Product product) {
		return productTransformer.transform(productRepository.save(product));
	}
	
	public ProductFull upsert(ProductFull product) {
		return upsert(productTransformer.untransform(product));
	}

	public ProductFull getProduct(Integer id) throws NotFoundException {
		Optional<Product> res = productRepository.findById(id);
		if (res.isPresent()) {
			return productTransformer.transform(res.get());
		} else {
			throw new NotFoundException();
		}
	}
	
	public Optional<Product> getEntityProduct(Integer id) {
		return productRepository.findById(id);
	}

	public List<ProductFull> getProducts() {
		return productTransformer.transform(productRepository.findAll());
	}

	public void deleteProduct(Integer id) {
		productRepository.deleteById(id);
	}

	public Iterable<ProductFull> getProductsByName(String name) {
		return productTransformer.transform(productRepository.findByName(name));
	}

}
