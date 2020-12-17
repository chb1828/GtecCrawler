package com.example.demo.elasticsearch.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.example.demo.object.CollectObject;


public interface CollectRepository extends ElasticsearchRepository<CollectObject, Long> {

}
