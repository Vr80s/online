package com.xczh.consumer.market.wxmessage.resp;

import java.util.List;

/** 
 * 多图文消息， 
 * 单图文的时候 Articles 只放一个就行了 
 * @author Caspar.chen 
 */  
public class NewsMessage extends BaseMessage {  
    /** 
     * 图文消息个数，限制为10条以内 
     */  
    private int ArticleCount;  
    /** 
     * 多条图文消息信息，默认第一个item为大图 
     */  
    private List<Article> Articles;  
    
    
    /** 
     * 发送图文消息（点击跳转到图文消息页面）
     */  
    private String media_id;  
  
    public int getArticleCount() {  
        return ArticleCount;  
    }  
  
    public void setArticleCount(int articleCount) {  
        ArticleCount = articleCount;  
    }  
  
    public List<Article> getArticles() {  
        return Articles;  
    }  
  
    public void setArticles(List<Article> articles) {  
        Articles = articles;  
    }

	public String getMedia_id() {
		return media_id;
	}

	public void setMedia_id(String media_id) {
		this.media_id = media_id;
	}  
}  