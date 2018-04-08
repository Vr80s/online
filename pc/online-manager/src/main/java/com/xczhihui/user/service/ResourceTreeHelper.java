package com.xczhihui.user.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.util.StringUtils;

import com.xczhihui.bxg.online.common.domain.Resource;

public class ResourceTreeHelper {

	/**
	 * 将给定的资源列表组织成树状结构。
	 * 
	 * @param resources
	 * @return
	 */
	public static List<Resource> genHierarchyTree(List<Resource> resources) {
		List<Resource> result = new ArrayList<>();
		for (Resource resource : resources) {
			String pid = resource.getParentId();
			if (StringUtils.hasText(pid)) {
				Resource parent = findParent(resources, pid);
				if (parent != null) {
					addChild(parent, resource);
				} else {// 父节点不符合条件的也直接显示出来。
					result.add(resource);
				}
			} else {
				result.add(resource);
			}
		}
		orderByDisplayOrder(result);
		return result;
	}

	/**
	 * 将resource树按照DisplayOrder排序
	 * 
	 * @param resources
	 */
	private static void orderByDisplayOrder(List<Resource> resources) {
		ResourceComparator com = new ResourceComparator();
		 resources.sort(com);//yuruixin_2017-0816
		Collections.sort(resources, com);
		for (Resource resource : resources) {
			List<Resource> children = resource.getChildren();
			if (children != null && children.size() > 0) {
				children.sort(com);
			}
		}
	}

	private static void addChild(Resource parent, Resource child) {
		if (parent != null && child != null) {
			List<Resource> children = parent.getChildren();
			if (children == null) {
				children = new ArrayList<>();
				parent.setChildren(children);
			}
			children.add(child);
		}
	}

	private static Resource findParent(List<Resource> resources, String parentId) {
		Resource resource = null;
		for (Resource res : resources) {
			if (res.getId().equals(parentId)) {
				resource = res;
				break;
			}
		}
		return resource;
	}
}

class ResourceComparator implements Comparator<Resource> {

	@Override
	public int compare(Resource o1, Resource o2) {
		 return o2.getDisplayOrder() - o1.getDisplayOrder();
	}

}
