package it.polito.tdp.porto.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.Multigraph;

import it.polito.tdp.porto.db.ArticleDAO;
import it.polito.tdp.porto.db.CreatorDAO;

public class PortoModel {

	private Map<Integer,Creator> creators;
	private Map<Long,Article> articles;
	
	private Multigraph<Creator,DefaultEdge> grafo;
	
	private CreatorDAO cDAO=new CreatorDAO();
	private ArticleDAO aDAO=new ArticleDAO();
	
	private int numVertici=0;
	private int numArchi=0;
	
	public void generaGrafo(){
		
		creators=cDAO.getAllCreator();
		articles=aDAO.getAllArticle();
		
		grafo=new Multigraph<Creator,DefaultEdge>(DefaultEdge.class);
		
		//aggiungere nodi
		for(Creator c:creators.values()){
			grafo.addVertex(c);
			numVertici++;
		}
		System.out.println(numVertici);
		
		//aggiungere gli autori negli articoli e viceversa
		for(Creator c:creators.values()){
			c.setArticleList(aDAO.getArticleList(c));
		}
		for(Article a:articles.values()){
			a.setCreatorList(cDAO.getCreatorList(a));
		}
		
		//aggiungere archi
		for(Article a:articles.values()){
			for(Creator c:a.getCreatorList()){
				for(Creator c2:a.getCreatorList()){
						if(grafo.containsEdge(c, c2)==false && c!=c2){
							grafo.addEdge(c,c2);
							numArchi++;
						}
				}
			}
		}
		System.out.println(numArchi);
	}
	
	public List<Creator> getCreators(){
		List<Creator> l=new ArrayList<Creator>(creators.values());
		Collections.sort(l);
		return l;
	}
	
	public StringBuilder getCoautori(Creator c){
		
		StringBuilder s=new StringBuilder();
		
		s.append("Coautori di "+c.toString()+":\n");
		
		for(Creator cc:Graphs.neighborListOf(grafo, c)){
			
			s.append("\n"+cc.toString()+";");
			
		}
		
		
		return s;
	}
	
	
	
}
