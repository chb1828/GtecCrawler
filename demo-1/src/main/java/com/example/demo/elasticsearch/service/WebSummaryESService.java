package com.example.demo.elasticsearch.service;

import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

import java.util.List;
import java.util.Optional;

import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.client.ElasticsearchClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.index.reindex.DeleteByQueryRequestBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.DeleteQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import com.example.demo.elasticsearch.repository.WebSummaryRepository;
import com.example.demo.object.CollectObject;
import com.example.demo.object.ItemObject;



@Service
public class WebSummaryESService{
    private WebSummaryRepository repository;
	@Autowired
	private ElasticsearchTemplate esTemplate;
    
    @Autowired
    public void setWebSummaryRepository(WebSummaryRepository repository){
        this.repository = repository;
    }
    
    public ItemObject save(ItemObject item) {
//    	Optional<ItemObject> obj = this.findByBase64(item.getBase64());
//    	if(!obj.isEmpty()) {
//    		return null;
//    	}
    	
    	//PageImpl<ItemObject> tes = (PageImpl<ItemObject>) this.findAll();
		item.setId(System.currentTimeMillis());
		
		
        return repository.save(item);
    }

    public void delete(ItemObject item) {
        repository.delete(item);
    }

    public Iterable<ItemObject> findAll() {
        // TODO Auto-generated method stub
        return repository.findAll();
    }


	public List<ItemObject> findByTitle(String title) {
		// TODO Auto-generated method stub
		return repository.findByTitle(title);
	}

	public Long getTotalCount(Long id) {
		// TODO Auto-generated method stub
		SearchQuery searchQuery = new NativeSearchQueryBuilder()
				.withQuery(matchQuery("collectId", id))
				.withIndices("web_items")
				.withTypes("web_item")
				.build();
		return esTemplate.count(searchQuery,CollectObject.class);
		
	}
	
	public void resetDashBoard() {
		new DeleteByQueryRequestBuilder(esTemplate.getClient(), DeleteByQueryAction.INSTANCE)
		.filter(QueryBuilders.matchQuery("_type", "item"))
		.source("items")
		.execute();
		
	}
}