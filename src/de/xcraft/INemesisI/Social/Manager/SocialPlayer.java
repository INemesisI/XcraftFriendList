package de.xcraft.INemesisI.Social.Manager;

import java.util.List;

public class SocialPlayer {
	private String name;
	private String ActiveGroup;
	private List<String> groups;
	private List<String> friends;

	public SocialPlayer(String name, List<String> friends, List<String> groups, String activeGroup) {
		this.name = name;
		this.ActiveGroup = activeGroup;
		this.groups = groups;
		this.friends = friends;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getFriends() {
		return friends;
	}

	public void setFriends(List<String> friends) {
		this.friends = friends;
	}
	public List<String> getGroups() {
		return groups;
	}
	public void setGroups(List<String> groups) {
		this.groups = groups;
	}

	public String getActiveGroup() {
		return ActiveGroup;
	}

	public void setActiveGroup(String activeGroup) {
		ActiveGroup = activeGroup;
	}
}
