package com.smart.homewifi.mesh.es;

import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

public class ElasticSearchOperations {

	private static final Logger logger = LogManager.getLogger(ElasticSearchOperations.class);


	private static boolean isSuccess(Object response) {
//		System.out.println(JSONObject.toJSONString(response));
		/*
		 * if (response instanceof IndexResponse) {
		 * //返回状态有两种情况-第一:该文档id不存在,进行创建写入RestStatus.CREATED ;
		 * 第二,文档id已经存在,进行更新操作返回RestStatus.OK return
		 * RestStatus.CREATED.equals(((IndexResponse)response).) ||
		 * RestStatus.OK.equals(((IndexResponse) response).status()); } else if
		 * (response instanceof DeleteResponse) { return
		 * RestStatus.OK.equals(((DeleteResponse) response).status()); }
		 */
		return false;
	}

	public static boolean delete(String index, String type, String id) {
		try {
			DeleteResponse deleteResponse = ESClient.getEsClient().getTransportClient().prepareDelete(index, type, id).get();
			return isSuccess(deleteResponse);
		} catch (Exception ex) {
			logger.error("delete elasticSearch exception", ex);
		}
		return false;

	}

	public static void prepareSearch(String index, String type) {
		SearchResponse searchResponse = ESClient.getEsClient().getTransportClient().prepareSearch(index).setTypes(type)
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH).setSize(20)
				.setQuery(QueryBuilders.matchPhraseQuery("provName",
						"湖北省")/* .matchAllQuery() *//*
													 * .matchQuery("mac",
													 * "48A74E812000")
													 */)
				.get();
		SearchHits shs = searchResponse.getHits();
		for (SearchHit sh : shs) {
			System.out.println("this is " + sh.getSourceAsString());
		}
	}


	public static JSONObject queryByMac(String index, String type, String mac) {
		JSONObject obj = null;
		try {
			QueryBuilder qb = QueryBuilders.matchQuery("mac", mac);
			SearchResponse sr = ESClient.getEsClient().getTransportClient().prepareSearch(index).setTypes(type).setQuery(qb)
					.execute().actionGet();
			SearchHits hits = sr.getHits();
			if (null != hits && hits.totalHits > 0) {
				for (SearchHit hit : hits) {
					String jsonstr = hit.getSourceAsString();
					obj = JSONObject.parseObject(jsonstr);
				}
			}
		} catch (Exception e) {
			logger.error("query elasticSearchMAC exception", e);
		}
		return obj;
	}

	/**
	 * 根据Id查询
	 * @param index
	 * @param type
	 * @param id
	 * @return
	 */
	public static JSONObject queryById(String index, String type, String id) {
		JSONObject jsonob = null;
		try {
			// 检索
			QueryBuilder qb = QueryBuilders.idsQuery(type).addIds(id);

			SearchResponse sr = ESClient.getEsClient().getTransportClient().prepareSearch(index).setTypes(type).setQuery(qb)
					.execute().actionGet();
			SearchHits hits = sr.getHits();

			if (null != hits && hits.totalHits > 0) {
				for (SearchHit hit : hits) {
					String jsonstr = hit.getSourceAsString();
					jsonob = JSONObject.parseObject(jsonstr);
				}
			}
		} catch (Exception ex) {
			logger.error("query elasticSearchID exception", ex);
		}
		return jsonob;
	}




	/*
	 * 更新网关上线信息
	 */
	public static void bulkGwOnlineUpdate(JSONObject json, String index, String type, String id){
		BulkProcessor bulkprocessor = ESClient.getEsClient().getGwOnlineUpdateBulkProcessor();
		bulkprocessor.add(updateRequest(index, type, id, json.toJSONString()));
	}


	public static UpdateRequest updateRequest(String index, String indexType, String indexId, String jsonString) {
		UpdateRequest updateRequest = null;
		try {
			IndexRequest indexRequest = new IndexRequest(index)
					.type(indexType)
					.id(indexId)
					.source(jsonString, XContentType.JSON);
			if (indexId != null && indexId.length() > 0) {
				updateRequest = new UpdateRequest(index, indexType, indexId).doc(jsonString, XContentType.JSON).upsert(indexRequest);
			} else {
			}
		} catch (Exception e) {
			logger.error("updateRequest:"+e.getMessage(),e);
		}
		return updateRequest;
	}

}
