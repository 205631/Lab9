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

public class CreatorDAO {

	Map<Integer,Creator> creatorMap;
	
public Map<Integer,Creator> getAllCreator(){
		
		creatorMap=new HashMap<Integer,Creator>();
		
		Connection conn=DBConnect.getConnection();
		
		
		String sql="select * from creator";
		
		PreparedStatement st;
		
		try {
			st=conn.prepareStatement(sql);
			ResultSet res=st.executeQuery();
			
			while(res.next()){
				Creator c=new Creator(res.getInt("id_creator"),res.getString("family_name"),res.getString("given_name"));
				creatorMap.put(c.getId_creator(),c);
			}
			res.close();
			conn.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		
		return creatorMap;
	}


	public List<Creator> getCreatorList(Article a){
		
		List<Creator> l =new ArrayList<Creator>();
		
		Connection conn=DBConnect.getConnection();
		
		
		String sql="select id_creator from authorship a,article aa where a.eprintid=aa.eprintid and a.eprintid=?";
		
		PreparedStatement st;
		
		try {
			st=conn.prepareStatement(sql);
			
			st.setLong(1,a.getEprintid());
			
			ResultSet res=st.executeQuery();
			
			while(res.next()){
				l.add(creatorMap.get(res.getInt("id_creator")));
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
