package com.xczhihui.common.solr.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;

/**
 * Description：solr工厂类
 * creed: Talk is cheap,show me the code
 *
 * @author name：yuxin
 * @Date: 2018/6/21 0021 下午 3:37
 **/
public class SolrClientFactory {

    /**
     * 线程安全的
     */
    public static Map<String, SolrClient> solrClientMap = Collections.synchronizedMap(new HashMap<String, SolrClient>());


    public static void build(String solrUrl, String coreName) {
        SolrClient solrClient = new HttpSolrClient.Builder(solrUrl + "/" + coreName).build();
        solrClientMap.put(coreName, solrClient);
    }

    public synchronized static SolrClient getSolrClient(String solrUrl, String coreName) {
        if (!solrClientMap.containsKey(coreName)) {
            build(solrUrl, coreName);
        }
        return solrClientMap.get(coreName);
    }


}
