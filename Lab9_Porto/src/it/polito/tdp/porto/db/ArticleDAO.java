package it.polito.tdp.porto.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.porto.model.Article;
import it.polito.tdp.porto.model.Creator;

public class ArticleDAO {

	private Map<Long,Article> articleMap;
	
	public Map<Long,Article> getAllArticle(){
		
		articleMap=new HashMap<Long,Article>();
		
		Connection conn=DBConnect.getConnection();
		
		
		String sql="select * from article a";
		
		PreparedStatement st;
		
		try {
			st=conn.prepareStatement(sql);
			ResultSet res=st.executeQuery();
			
			while(res.next()){
				Article a=new Article(res.getLong("eprintid"),res.getInt("year"),res.getString("title"));
				articleMap.put(a.getEprintid(),a);
			}
			res.close();
			conn.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		
		return articleMap;
	}

	public List<Article> getArticleList(Creator a){
		
		List<Article> l =new ArrayList<Article>();
		
		Connection conn=DBConnect.getConnection();
		
		
		String sql="select eprintid from authorship a,creator aa where a.id_creator=aa.id_creator and a.id_creator=?";
		
		PreparedStatement st;
		
		try {
			st=conn.prepareStatement(sql);
			
			st.setInt(1,a.getId_creator());
			
			ResultSet res=st.executeQuery();
			
			while(res.next()){
				l.add(articleMap.get(res.getLong("eprintid")));
			}
			res.close();
			conn.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		
		return l;
	}

	
	
	
	
	
	
}
