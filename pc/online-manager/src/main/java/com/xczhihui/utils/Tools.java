package com.xczhihui.utils;

import java.text.ParseException;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Tools {

	public static Groups filterGroup(String params) {
		Gson gson = new Gson();
		List<Group> searchGroups = gson.fromJson(params,
				new TypeToken<List<Group>>() {
				}.getType());
		Groups groups = new Groups();
		try {
			if (searchGroups != null && !searchGroups.isEmpty()) {
				for (Group group : searchGroups) {
					group.setTempMatchType(group.getTempMatchType());
					group.setTempType(group.getTempType());
					if (group.getPropertyValue1() != null) {
						group.setPropertyValue1(group.getPropertyValue1()
								.toString().trim());
					}
					groups.Add(group);
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return groups;
	}

}
