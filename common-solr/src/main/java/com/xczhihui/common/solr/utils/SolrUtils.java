package com.xczhihui.common.solr.utils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description：solr工具类
 * creed: Talk is cheap,show me the code
 * @author name：yuxin
 * @Date: 2018/6/21 0021 下午 3:36
 **/
public class SolrUtils {

    private static final Logger logger = LoggerFactory.getLogger(SolrUtils.class);
    private SolrClient solrClient;
    private String pre;
    private String post;

    public SolrUtils(String solrUrl,String coreName,String pre,String post) {
        this.pre = pre;
        this.post = post;
        solrClient = SolrClientFactory.getSolrClient(solrUrl,coreName);
    }

    /**
     * Description：添加单个文档
     * creed: Talk is cheap,show me the code
     * @author name：yuxin
     * @Date: 2018/6/16 0016 下午 5:57
     **/
    public void addBean(Object object) throws IOException, SolrServerException {
        solrClient.addBean(object);
        solrClient.commit(false, false);
    }

    /**
     * Description：根据id删除
     * creed: Talk is cheap,show me the code
     * @author name：yuxin
     * @Date: 2018/6/16 0016 下午 5:54
     **/
    public void deleteById(Object id) throws SolrServerException, IOException {
        solrClient.deleteByQuery("id:" + id.toString());
        solrClient.commit(false, false);
    }

    /**
     * Description：关键字分页查询
     * creed: Talk is cheap,show me the code
     * @author name：yuxin
     * @Date: 2018/6/16 0016 下午 7:27
     **/
    public <T> SolrPages getByPage(String searchStr, int pageNum, int pageSize, Class<T> clzz, Map<String, SolrQuery.ORDER> sortMap) throws IOException, SolrServerException {
        SolrQuery query = new SolrQuery();
        // 查询内容
        query.setQuery(searchStr)
                // 分页
                .setStart((pageNum - 1) * pageSize)
                .setRows(pageSize);
        if (sortMap != null && sortMap.size() > 0) {
            for (String key : sortMap.keySet()) {
                query.addSort(key, sortMap.get(key));
            }
        }
        //显示匹配度得分
        query.set("fl", "*,score");
        logger.info("solr query:{}",searchStr);
        logger.info("solr :{}",query.toString());
        QueryResponse response = solrClient.query(query);
        // 查询结果集
        SolrDocumentList results = response.getResults();
        // 获取对象
        List<T> beans = solrClient.getBinder().getBeans(clzz, results);
        // 总记录数
        int total = new Long(response.getResults().getNumFound()).intValue();
        return new SolrPages(beans, total, pageSize, pageNum);
    }

    /**
     * Description：带高亮的关键字查询
     * creed: Talk is cheap,show me the code
     * @author name：yuxin
     * @Date: 2018/6/16 0016 下午 7:25
     **/
    public <T> SolrPages getHighterByPage(String searchStr, int pageNum, int pageSize, Class<T> clzz, Map<String, SolrQuery.ORDER> sortMap,List<String> hlFields) throws IOException, SolrServerException {
        String preTag = pre;
        String postTag = post;

        SolrQuery query = new SolrQuery();
        //排序字段
        for (String key : sortMap.keySet()) {
                query.addSort(key, sortMap.get(key));
        }
        // 查询内容
        query.setQuery(searchStr)
                // 设置高亮显示
                .setHighlight(true)
                // 渲染头标签
                .setHighlightSimplePre(preTag)
                // 尾标签
                .setHighlightSimplePost(postTag)
                //返回语段数量
                .setHighlightFragsize(150)
                // 分页
                .setStart((pageNum - 1) * pageSize)
                .setRows(pageSize).setFilterQueries("");
        // 设置高亮区域
        for (String hl : hlFields) {
            query.addHighlightField(hl);
        }
        QueryResponse response = solrClient.query(query);
        SolrDocumentList results = response.getResults();
        // 总记录数
        int total = new Long(results.getNumFound()).intValue();
        // 查询结果集
        ArrayList<T> list = new ArrayList<T>();
        try {
            Object object = null;
            Method method = null;
            Class<?> fieldType = null;
            Map<String, Map<String, List<String>>> map = response.getHighlighting();
            for (SolrDocument solrDocument : results) {
                object = clzz.newInstance();
                // 得到所有属性名
                Collection<String> fieldNames = solrDocument.getFieldNames();
                for (String fieldName : fieldNames) {
                    Field[] fields = clzz.getDeclaredFields();
                    for (Field f : fields) {
                        // 如果实体属性名和查询返回集中的字段名一致，填充对应的set方法
                        if (f.getName().equals(fieldName)) {
                            f = clzz.getDeclaredField(fieldName);
                            fieldType = f.getType();
                            // 构造set方法名 setId
                            String dynamicSetMethod = dynamicMethodName(f.getName(), "set");
                            // 获取方法
                            method = clzz.getMethod(dynamicSetMethod, fieldType);
                            // 获取fieldType类型
                            // 获取到的属性
                            method.invoke(object, fieldType.cast(solrDocument.getFieldValue(fieldName)));
                            for (String hl : hlFields) {
                                if (hl.equals(fieldName)) {
                                    String idv = solrDocument.getFieldValue("id").toString();
                                    List<String> hfList = map.get(idv).get(fieldName);
                                    if (null != hfList && hfList.size() > 0) {
                                        // 高亮添加
                                        method.invoke(object, fieldType.cast(hfList.get(0)));
                                    } else {
                                        method.invoke(object, fieldType.cast(solrDocument.getFieldValue(fieldName)));
                                    }
                                }
                            }
                        }
                    }
                }
                list.add(clzz.cast(object));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return new SolrPages(list, total, pageSize, pageNum);
    }

    /**
     * @param @param  name
     * @param @param  string
     * @param @return
     * @return String
     * @throws
     * @Description: 动态生成方法名
     * @author kang
     * @创建时间 2015下午9:16:59
     */
    private String dynamicMethodName(String name, String string) {
        if (Character.isUpperCase(name.charAt(0))) {
            return string + name;
        } else {
            return (new StringBuilder()).append(string + Character.toUpperCase(name.charAt(0))).append(name.substring(1)).toString();
        }
    }

     /**
     * Description：
     * creed: Talk is cheap,show me the code
     * @author name：yuxin
     * @Date: 2018/6/16 0016 下午 5:32
     **/
    public <E> void init(List<E> lists) throws IOException, SolrServerException {
        solrClient.deleteByQuery("*:*");
        solrClient.addBeans(lists);
        solrClient.commit(false, false);
    }
}
