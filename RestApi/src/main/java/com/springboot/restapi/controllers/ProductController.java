package com.springboot.restapi.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.restapi.entities.Product;
import com.springboot.restapi.repositories.ProductRepository;

@RestController
public class ProductController {
	
	@Autowired
	ProductRepository repository;
	
	@RequestMapping(value="/getProducts", method=RequestMethod.GET)
	private List<Product>getProducts(){
		return 	repository.findAll();
	}
	
	@RequestMapping(value="/getProducts/{id}",method=RequestMethod.GET)
	private Product getProduct(@PathVariable("id") Long id) {
		return repository.findById(id).get();
	}
	
	@RequestMapping(value="/product",method=RequestMethod.POST)
	private Product updateProducts(@RequestBody Product product) {
		return repository.save(product);
	}
	
	@RequestMapping(value="/deleteProduct/{id}", method=RequestMethod.POST)
	private String deleteProduct(@PathVariable("id") Long id) {
		 repository.deleteById(id);
		 Map<String, String> map = new HashMap<String, String>();
		 return map.put("status", "deleted");
	}
}
