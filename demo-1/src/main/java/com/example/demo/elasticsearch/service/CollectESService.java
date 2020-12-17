package com.example.demo.elasticsearch.service;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;

import java.util.List;
import java.util.Optional;

import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import com.example.demo.elasticsearch.repository.CollectRepository;
import com.example.demo.object.CollectObject;

@Service
public class CollectESService {
	@Autowired
	private CollectRepository repository;
	@Autowired
	private ElasticsearchTemplate esTemplate;
	
//	@Autowired
//	public void setReqCollectRepository(CollectRepository repository) {
//		this.repository = repository;
//	}
	
	public CollectObject save(CollectObject collectInfo) {
		//reqCollect.setId(System.currentTimeMillis());
		return repository.save(collectInfo);
	}

	public Optional<CollectObject> findById(Long id) {
		return repository.findById(id);
	}

	public Iterable<CollectObject> findAll() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

	public List<CollectObject> findAllSortById(int page, int row) {
		// TODO Auto-generated method stub
		SearchQuery searchQuery = new NativeSearchQueryBuilder().withIndices("collects")
			      .withTypes("collect").withQuery(matchAllQuery())
			      .withSort(new FieldSortBuilder("id").order(SortOrder.DESC))
			      .withPageable(PageRequest.of(page, row)).build();
		
		return esTemplate.queryForList(searchQuery, CollectObject.class);
	}
	
	public Long getTotalCount() {
		SearchQuery searchQuery = new NativeSearchQueryBuilder()
				.withQuery(matchAllQuery())
				.withIndices("collects")
				.withTypes("collect")
				.build();
		return esTemplate.count(searchQuery,CollectObject.class);
	}
}
