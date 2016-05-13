package it.polito.tdp.porto.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.Multigraph;
import org.jgrapht.traverse.DepthFirstIterator;
import org.jgrapht.traverse.GraphIterator;

import it.polito.tdp.porto.db.ArticleDAO;
import it.polito.tdp.porto.db.CreatorDAO;

public class PortoModel {

	private Map<Integer,Creator> creators;
	private Map<Long,Article> articles;
	
	private Multigraph<Creator,DefaultEdge> grafo;
	
	private Set<Article> listaArticoliInComune;
	
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
	
	
	public StringBuilder getCluster(){
		
		StringBuilder s=new StringBuilder();
		int conta=0;
		
		List<Creator> l=new ArrayList<Creator>();
		
		for(Creator c:grafo.vertexSet()){
			if(!l.contains(c)){
				s.append("Cluster partendo da "+c.toString()+":\n");
				
				GraphIterator<Creator, DefaultEdge> dfv = new DepthFirstIterator<Creator, DefaultEdge>(grafo,c);
				
				while(dfv.hasNext()){
					Creator t=dfv.next();
					
					l.add(t);
					
					s.append("\n"+t.toString()+";");
				}
				s.append("\n\n");
				conta++;
			}
		}
		s.append("\nNumero totale di Cluster: "+conta);
		return s;
	}

	
	public StringBuilder getArticoliAutori(Creator c1,Creator c2){
		
		listaArticoliInComune=new HashSet<Article>();
		
		StringBuilder s=new StringBuilder();
		
		if(this.isCoautori(c1, c2)==false){
			//algoritmo di ricerca
			if(this.isCluster(c1, c2)==true){
				
				ricorsione(c1,c2,new ArrayList<Article>(),new ArrayList<Creator>());
				System.out.println(listaArticoliInComune.size());
				s.append("Articoli che collegano l'autore "+c1.toString()+" e l'autore "+c2.toString()+":");
				
				if(listaArticoliInComune.isEmpty()==true){
					s.append("\nNESSUNO!");
				}else{
					for(Article a:listaArticoliInComune){
						s.append("\n"+a.toString()+";");
					}
				}
				return s;
			}else{
				s.append("L'autore "+c1.toString()+" e l'autore "+c2.toString()+" non sono collegati!");
				return s;
			}
		}else{
			s.append("L'autore "+c1.toString()+" e l'autore "+c2.toString()+" sono coautori!");
			return s;
		}
	}
	
	public boolean isCoautori(Creator c1,Creator c2){
		
		boolean ris=false;
		
		for(Creator c:Graphs.neighborListOf(grafo,c1)){
			if(c.equals(c2)){
				ris=true;
			}
		}
		return ris;
	}
	
	public boolean isCluster(Creator c1,Creator c2){
		
		boolean ris=false;
				
		GraphIterator<Creator, DefaultEdge> dfv = new DepthFirstIterator<Creator, DefaultEdge>(grafo,c1);
				
		while(dfv.hasNext()){
			Creator t=dfv.next();
			if(c2.equals(t))
				ris=true;
			
		}
		
		return ris;
	}
	
	public void ricorsione(Creator c1,Creator c2,List<Article> tempRis, List<Creator> visited){

		if(c1.equals(c2)==true){
			//copiare lista nel riultato
			listaArticoliInComune.addAll(new ArrayList<Article>(tempRis));
			return;
		}
		
		List<Article> articoliPartenza=c1.getArticleList();
		
		List<Article> articoliVicino;
		
		List<Creator> l=Graphs.neighborListOf(grafo, c1);
		
		for(Creator c:l){
			if(visited.contains(c)==false){
				//break;
				articoliVicino=c.getArticleList();
				for(Article a:articoliPartenza){
					if(articoliVicino.contains(a)==true){
						tempRis.add(a);
					}
				}
				visited.add(c);
				ricorsione(c,c2,tempRis,visited);
			}
		}
		
	}
	
}
