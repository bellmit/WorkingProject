package com.smart.homewifi.mesh.es;


import com.smart.homewifi.mesh.config.EsConfig;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ESClient {
	private static final Logger logger = LoggerFactory.getLogger(ESClient.class);
    private static ESClient es = null;
    private static TransportClient client = null;
    private static BulkProcessor gwOnlineUpdateBulkProcessor = null;
    private ESClient() {

    }

    public static ESClient getEsClient() {
        if (es == null) {
            synchronized (ESClient.class) {
                if (es == null||client == null) {
                    EsConfig esConfig = SpringUtil.getBean(EsConfig.class);
                    String port = esConfig.getEsZenPort();
                    Settings settings = Settings.builder()
                            .put("cluster.name", esConfig.getEsCluseterName())
                            .put("client.transport.sniff", true).build();
                    client = new PreBuiltTransportClient(settings);
                    try {
                        String address = esConfig.getEsAddress();
                        if (address != null && address.length() > 0) {
                            String[] str = address.split(",");
                            for (String tmp : str) {
                                client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(tmp), new Integer(port)));
                            }
                        }
                        es = new ESClient();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return es;
    }

    public void closeClient(TransportClient client) {
        if (client != null) {
            client.close();
        }
    }

    public TransportClient getTransportClient() {
        return client;
    }


    public BulkProcessor getGwOnlineUpdateBulkProcessor() {
        if(gwOnlineUpdateBulkProcessor==null){
            gwOnlineUpdateBulkProcessor = BulkProcessor.builder(client, new BulkProcessor.Listener() {
                public void beforeBulk(long l, BulkRequest bulkRequest) {
                    logger.info("---尝试更新{}条数据---", bulkRequest.numberOfActions());
                }

                public void afterBulk(long l, BulkRequest bulkRequest, BulkResponse bulkResponse) {
                    String msg = bulkResponse.buildFailureMessage();
                    int failCount = msg.split("index").length - 1;
                    logger.info(("-gwOnlineUpdateBulkProcessor-提交" + bulkResponse.getItems().length + "个文档，用时"+ bulkResponse.getTookInMillis() + "ms" + (bulkResponse.hasFailures() ? " 有"+failCount+"个文档提交失败:"+ msg : "")));
                }

                public void afterBulk(long l, BulkRequest bulkRequest, Throwable throwable) {
                    logger.info(("-gwOnlineUpdateBulkProcessor-有文档提交失败！after failure=" + throwable));
                }
            })
                    .setBulkActions(100000)
                    .setBulkSize(new ByteSizeValue(50, ByteSizeUnit.MB))
                    .setFlushInterval(TimeValue.timeValueSeconds(30))
                    .setConcurrentRequests(1)
                    .build();
        }
        return gwOnlineUpdateBulkProcessor;
    }


    /**
     * 创建索引库
     */
    public static void createIndex(String indexName) {
        if(isIndexExist(indexName)){
            //logger.info("索引:"+indexName+"已存在");
            return;
        } else {
            getEsClient().getTransportClient().admin().indices().prepareCreate(indexName)
                    .setSettings(Settings.builder()
                            .put("index.number_of_shards", 5)
                            .put("index.number_of_replicas", 1)
                    )
                    .get();
            logger.info("索引:"+indexName+"创建成功");
        }
    }


    /**
     * 判断索引库是否存在
     */
    public static boolean isIndexExist(String indexName) {
        IndicesExistsRequest inExistsRequest = new IndicesExistsRequest(indexName);
        IndicesExistsResponse inExistsResponse = getEsClient().getTransportClient().admin().indices()
                .exists(inExistsRequest).actionGet();
        if(inExistsResponse.isExists()) {
            return true;
        } else {
            return false;
        }
    }



}


