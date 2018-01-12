package com.xczhihui.bxg.online.manager.utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.xczhihui.bxg.online.manager.utils.PropertyFilter.MatchType;
/**
 * 一个Groups对应一条复杂语句，不再用多个Groups来完成 方便操作
 * Groups 与group 类 初定 验证之类的还未完善
 * 
 */
public class Groups {	
 
	private List<Group> groupList = new LinkedList<Group>();
	
	//子句与　本身的关系
	private MatchType childrelation = null;
	
	private Groups childGroups= null;
	
	private List<Groups> childGroups2;
	
	
	
	/**
	 * 别名解析 查询方式　
	 *  为true表示需要解析;为 false 不需要解析
	 * 
	 */
	private boolean alias = false;
	
	/**
	 * 排序字段　默认为 times
	 */
	private String orderby = "";
	
	private String groupby = "";
	
	private String[] orderbys;
	
	private boolean[] orders;
	
	/**
	 * 排序方式 
	 * true 表示　升序
	 * false 表示降序
	 * 默认为降序
	 */
	private boolean order = false;
	
	
	public Groups()
	{
	}
	
	public Groups(Groups groups)
	{
		if(groupList==null){
			groupList=new ArrayList<Group>();
		}
		groupList.addAll(groups.groupList);
	}
	
	public Groups(String fieldName,Object obj)
	{
		if(groupList==null){
			groupList=new ArrayList<Group>();
		}
		Group group = new Group(fieldName, obj);
		groupList.add(group);
	}
	
	//只传一 个group构建
	public Groups(Group group)
	{
		if(groupList==null){
			groupList=new ArrayList<Group>();
		}
		if (group != null) {
            groupList.add(group);
        }
	}
	
	public Groups(List<Group> groupList){
		if(groupList==null){
			groupList=new ArrayList<Group>();
		}
			this.groupList = groupList;
	}
	
	public void RemoveChild(){
		childGroups = null;
	}
	
	public void RemoveAll(){
		childGroups = null;
		groupList = null;
		childGroups2 = null;
	}
	public void RemoveOders(Groups groups){
		if(groups.getOrderby()!=null && !groups.getOrderby().equals("")){
			groups.setOrderby(null);
		}
		if(groups.getOrderbys()!=null && groups.getOrderbys().length>0){
			groups.setOrderbys(null);
		}
	}
	
	public void transferGroupsOrders(Groups groups, Groups newGroups){
		if(groups.getOrderby()!=null && !groups.getOrderby().equals("")){
			newGroups.setOrderby(groups.getOrderby());
			newGroups.setOrder(groups.isOrder());
		}
		if(groups.getOrderbys()!=null && groups.getOrderbys().length>0){
			newGroups.setOrderbys(groups.getOrderbys());
			newGroups.setOrders(groups.getOrders());
		}
	}
	
	public void Add(String fieldName,Object obj){
		if(groupList==null){
			groupList=new ArrayList<Group>();
		}
		Group group = new Group(fieldName,obj);
		groupList.add(group);
	}
	
	public void Add(String fieldName,Object obj,MatchType matchType, MatchType relation){
		if(groupList==null){
			groupList=new ArrayList<Group>();
		}
		Group group = new Group(fieldName,obj,matchType,relation);
		groupList.add(group);
	}
	
	public void Add(String fieldName,Object obj,MatchType matchType){
		if(groupList==null){
			groupList=new ArrayList<Group>();
		}
		Group group = new Group(fieldName,obj,matchType);
		groupList.add(group);
	}
	
	public void Add(String fieldName,MatchType matchType, MatchType relation){
		if(groupList==null){
			groupList=new ArrayList<Group>();
		}
		Group group = new Group(fieldName,matchType,relation);
		groupList.add(group);
	}
	
	public void Add(Group group){
		if(groupList==null){
			groupList=new ArrayList<Group>();
		}
		groupList.add(group);
	}
	
	public void Add(Groups groups){
		if(groupList==null){
			groupList=new ArrayList<Group>();
		}
		groupList.addAll(groups.getGroupList());
	}
	
	public void Add(List<Group> groups){
		if(groupList==null){
			groupList=new ArrayList<Group>();
		}
		groupList.addAll(groups);
	}
	
	public void Remove(Group group){
		if (groupList != null && !groupList.isEmpty() && group != null) {
            groupList.remove(group);
        }
	}
	
	/**
	 * 移除指定的grouplist内元素 
	 * @param index 索引位置　
	 * @remark 没有检查 index越界　使用时请注意　应保证不越界
	 */
	public void Remove(int index)
	{
		if (!groupList.isEmpty() && index < groupList.size()) {
            groupList.remove(index);
        }
		
	}
	
	/**
	 * 检查group条件字段名propertyName 是否存在 
	 * @param name
	 * @return
	 */
	public Group findByName(String name)
	{
		if (null != groupList && !groupList.isEmpty() && null != name)
		{
			for(Group g: groupList)
			{
				if (g.getPropertyName().equals(name.trim())) {
                    return g;
                }
			}
		}
		
		return null;
	}
	
	/**
	 * 根据 group条件字段名propertyName　删除 group
	 * @param name
	 */
	public void RemoveByName(String name)
	{
		if (!groupList.isEmpty() && null != name)
		{
			List<Group> groups = new ArrayList<Group>();
			for (Group group:groupList)
			{
				if (group.getPropertyName().equals(name.trim()))
				{
					groups.add(group);
				
				}
			}
			for (Group group : groups) {
				this.Remove(group);
			}
		}
	}
	
	public List<Group> getGroupList() {
		if(groupList==null){
			groupList=new ArrayList<Group>();
		}
		return groupList;
	}

	public void setGroupList(List<Group> groupList) {
		this.groupList = groupList;
	}
	public Groups getChildGroups() {
		return childGroups;
	}

	public void setChildGroups(Groups childGroups) {
		this.childGroups = childGroups;
	}

	public boolean isAlias() {
		return alias;
	}

	public void setAlias(boolean alias) {
		this.alias = alias;
	}
	public String getOrderby() {
		return orderby;
	}

	public void setOrderby(String orderby) {
		this.orderby = orderby;
	}
	public boolean isOrder() {
		return order;
	}

	public void setOrder(boolean order) {
		this.order = order;
	}
	public String[] getOrderbys() {
		return orderbys;
	}

	public void setOrderbys(String[] orderbys) {
		this.orderbys = orderbys;
	}
	public boolean[] getOrders() {
		return orders;
	}

	public void setOrders(boolean[] orders) {
		this.orders = orders;
	}
	public MatchType getChildrelation() {
		return childrelation;
	}

	public void setChildrelation(MatchType childrelation) {
		this.childrelation = childrelation;
	}

	public List<Groups> getChildGroups2() {
		if(childGroups2==null){
			childGroups2=new LinkedList<Groups>();
		}
		return childGroups2;
	}

	public void setChildGroups2(List<Groups> childGroups2) {
		this.childGroups2 = childGroups2;
	}

	public String getGroupby() {
		return groupby;
	}

	public void setGroupby(String groupby) {
		this.groupby = groupby;
	}

	
	

}
