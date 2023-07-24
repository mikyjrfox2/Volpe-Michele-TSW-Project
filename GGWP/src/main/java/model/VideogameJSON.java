package model;

import org.json.simple.JSONObject;

public final class VideogameJSON {
	
	private VideogameJSON() {}
	
	public static JSONObject map(Videogame v) {
		JSONObject obj = new JSONObject();
		String title=v.getTitle().replaceAll(" ", "%20");
        obj.put("title", title.replaceAll("'", "%27"));
        obj.put("dev", v.getDeveloper());
        obj.put("pub", v.getPublisher());
        obj.put("desc", v.getDescription());
        obj.put("img", v.getImage());
        obj.put("vid", v.getVideo());
        obj.put("price", v.getPrice());
        return obj;
	}
}
