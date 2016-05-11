package it.polito.tdp.porto.model;

import java.util.ArrayList;
import java.util.List;

public class Creator implements Comparable<Creator>{

	private int id_creator;
	private String family_name;
	private String given_name;
	
	private List<Article> articleList;
	
	public Creator(int id_creator, String family_name, String given_name) {
		super();
		this.id_creator = id_creator;
		this.family_name = family_name;
		this.given_name = given_name;
		articleList=new ArrayList<Article>();
	}

	public int getId_creator() {
		return id_creator;
	}

	public void setId_creator(int id_creator) {
		this.id_creator = id_creator;
	}

	public String getFamily_name() {
		return family_name;
	}

	public void setFamily_name(String family_name) {
		this.family_name = family_name;
	}

	public String getGiven_name() {
		return given_name;
	}

	public void setGiven_name(String given_name) {
		this.given_name = given_name;
	}

	public List<Article> getArticleList() {
		return articleList;
	}

	public void setArticleList(List<Article> articleList) {
		this.articleList = articleList;
	}
	
	public void addArticle(Article article) {
		this.articleList.add(article);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id_creator;
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
		Creator other = (Creator) obj;
		if (id_creator != other.id_creator)
			return false;
		return true;
	}
	
	public String toString(){
		return family_name+" "+given_name;
	}

	@Override
	public int compareTo(Creator o) {
		if(this.family_name.compareTo(o.family_name)==0)
			
			return this.given_name.compareTo(o.given_name);
		
		return this.family_name.compareTo(o.family_name);
	}
		
	
	
}
