package it.unisa.model;

import java.io.Serializable;

public class Carrello implements Serializable {

	private static final long serialVersionUID = 1L;
	


	private String name;
    private boolean acquistato;
    private int price;
    private String data;
    private String username;
    
    public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isAcquistato() {
		return acquistato;
	}

	public void setAcquistato(boolean acquistato) {
		this.acquistato = acquistato;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

   
	
	@Override
	public boolean equals (Object other) {
		return this.getUsername() == ((Carrello) other).getUsername() && this.getName() == ((Carrello) other).getName();
	}

	@Override
	public String toString() {
		return "Carrello [name=" + name + ", acquistato=" + acquistato + ", prezzo=" + price + ", data=" + data
				+ ", username=" + username + "]";
	}
	
	
}
