package it.polito.tdp.porto.model;

import java.util.ArrayList;
import java.util.List;

public class Article{

	private long eprintid;
	private int year;
	private String title;
	private List<Creator> creatorList;
	
	public Article(long eprintid, int year, String title) {
		super();
		this.eprintid = eprintid;
		this.year = year;
		this.title = title;
		creatorList=new ArrayList<Creator>();
	}

	public long getEprintid() {
		return eprintid;
	}

	public void setEprintid(long eprintid) {
		this.eprintid = eprintid;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setCreatorList(List<Creator> creatorList) {
		this.creatorList = creatorList;
	}
	
	public void addCreator(Creator creator) {
		this.creatorList.add(creator);
	}

	public List<Creator> getCreatorList() {
		return creatorList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (eprintid ^ (eprintid >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Article other = (Article) obj;
		if (eprintid != other.eprintid)
			return false;
		return true;
	}

	public String toString(){
		return eprintid+" "+title;
	}

	
	
}
