package eu.tanov.rentrooms.shared.model;

import java.io.Serializable;

public class RoomDTO implements Serializable {

	private static final long serialVersionUID = 2022083070181741546L;
	private String key;
	private String name;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
