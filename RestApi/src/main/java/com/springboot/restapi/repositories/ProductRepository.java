package com.springboot.restapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.restapi.entities.Product;

public interface ProductRepository extends JpaRepository<Product,Long>{

}
