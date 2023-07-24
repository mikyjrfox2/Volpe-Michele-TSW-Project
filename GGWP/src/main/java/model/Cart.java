package model;

import java.io.Serializable;
import java.util.Date;


public class Cart implements Serializable{
	
	private String vg,email,img;
	private boolean purchased;
	private Date purchaseDate;
	private double actualCost,cost;
	
	public boolean isPurchased() {
		return purchased;
	}
	
	public void setPurchased(boolean purchased) {
		this.purchased = purchased;
	}
	
	public Date getPurchaseDate() {
		return purchaseDate;
	}
	
	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	
	public double getActualCost() {
		return actualCost;
	}
	
	public void setActualCost(double actualCost) {
		this.actualCost = actualCost;
	}

	public String getVg() {
		return vg;
	}

	public void setVg(String vg) {
		this.vg = vg;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}
}
