package model;

import org.json.simple.JSONObject;

public final class UserJSON {
	
	private UserJSON() {}
	
	public static JSONObject map(User u) {
		JSONObject obj = new JSONObject();
        obj.put("nick", u.getNickname());
        obj.put("email", u.getEmail());
        obj.put("name", u.getName());
        obj.put("points", u.getPoints());
        obj.put("bio", u.getBio());
        obj.put("wallet", u.getWallet());
        return obj;
	}
}
