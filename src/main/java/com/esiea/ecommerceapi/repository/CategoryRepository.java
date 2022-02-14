package com.esiea.ecommerceapi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.esiea.ecommerceapi.model.Category;


@Repository
public interface CategoryRepository extends CrudRepository<Category, Integer> {

}
