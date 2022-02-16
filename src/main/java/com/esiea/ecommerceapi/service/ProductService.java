package com.esiea.ecommerceapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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

	public ProductFull create(Product product) throws NotAllowedException {
		if (product.getId() == null) {
			return upsert(product);
		} else {
			throw new NotAllowedException();
		}
	}	
	
	public ProductFull update(Product product) throws NotFoundException {
		getProduct(product.getId());
		return upsert(product);
	}
	
	public ProductFull update(ProductFull productF) throws NotFoundException {
		getProduct(productF.getId());
		return upsert(productF);
	}
	
	private ProductFull upsert(Product product) {
		return productTransformer.transform(productRepository.save(product));
	}

	private ProductFull upsert(ProductFull product) {
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

	public void deleteProduct(Integer id) throws NotFoundException {
		try {
			productRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new NotFoundException();
		}
	}

	public Iterable<ProductFull> getProductsByName(String name) throws NotFoundException {
		Iterable<ProductFull> list = productTransformer.transform(productRepository.findByName(name));
		if (list.iterator().hasNext()) {
			return list;
		} else {
			throw new NotFoundException();
		}
	}

}
