package com.example.demo.elasticsearch.repository;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.example.demo.object.ItemObject;


public interface WebSummaryRepository extends ElasticsearchRepository<ItemObject, Long>{
	
	List<ItemObject> findByTitle(String title);
}
