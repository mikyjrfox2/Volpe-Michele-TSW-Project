package it.unisa.model;

import java.io.Serializable;

public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	
    private String username;
    private String password;
    private String tipo;
    private int portafoglio;

    public int getPortafoglio() {
        return portafoglio;
    }

    public void setPortafoglio(int portafoglio) {
        this.portafoglio = portafoglio;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
        this.tipo = tipo;
		
	}
	
	@Override
	public boolean equals (Object other) {
		return this.getUsername() == ((User) other).getUsername();
	}
	
	@Override
	public String toString() {
		return username +","+password+", "+tipo+", portafoglio: " + portafoglio;
	}
}
