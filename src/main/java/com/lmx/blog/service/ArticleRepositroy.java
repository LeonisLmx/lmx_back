package com.lmx.blog.service;

import com.lmx.blog.model.ArticleDescription;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ArticleRepositroy  extends ElasticsearchRepository<ArticleDescription,Long> {
}
