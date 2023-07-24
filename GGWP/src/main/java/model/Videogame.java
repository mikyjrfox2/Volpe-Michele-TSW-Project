package model;

import java.io.Serializable;
import java.sql.Blob;
import java.sql.Date;

public class Videogame implements Serializable{
	public String title, developer, publisher, description, image, video;
	private Date releaseDate, addDate;
	private double price;
	private int nAcq;
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDeveloper() {
		return developer;
	}
	
	public void setDeveloper(String developer) {
		this.developer = developer;
	}
	
	public String getPublisher() {
		return publisher;
	}
	
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Date getReleaseDate() {
		return releaseDate;
	}
	
	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public String getImage() {
		return image;
	}
	
	public void setImage(String image) {
		this.image = image;
	}
	
	public String getVideo() {
		return video;
	}
	
	public void setVideo(String video) {
		this.video=video;
	}
	
	public boolean equals(Object o) {
		return this.getTitle()==((Videogame)o).getTitle();
	}

	public int getnAcq() {
		return nAcq;
	}

	public void setnAcq(int nAcq) {
		this.nAcq = nAcq;
	}

	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}
		
}
