package model;

import org.json.simple.JSONObject;

public final class GenreJSON {
	
	private GenreJSON() {}

	public static JSONObject map(Genre g) {
		JSONObject obj = new JSONObject();
		obj.put("tipo", g.getType());
		return obj;
	}
}
