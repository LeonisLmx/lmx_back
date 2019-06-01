package com.lmx.blog.service;

import com.lmx.blog.model.MapEsData;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface MapEsDataRepository extends ElasticsearchRepository<MapEsData,Long> {
}
