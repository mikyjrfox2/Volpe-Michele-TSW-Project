package model;

import java.io.Serializable;

public class Genre implements Serializable{
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
