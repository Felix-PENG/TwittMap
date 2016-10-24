package com.cc.twittmap.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;

import com.cc.twittmap.model.Tweet;
import com.cc.twittmap.thread.FetchTwittsThread;

import net.sf.json.JSONArray;

/**
 * Servlet implementation class KeywordServlet
 */
@WebServlet(urlPatterns="/Keyword",loadOnStartup=1)
public class KeywordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	public void init(){
		Thread t = new Thread(new FetchTwittsThread());
		t.start();
	}
    /**
     * @see HttpServlet#HttpServlet()
     */
    public KeywordServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		String keyword = request.getParameter("keyword");
		
		TransportClient client = TransportClient.builder().build()
		        .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));
		SearchResponse res;
		if(keyword.equals("All")){
			res = client.prepareSearch("twitter")
					.setTypes("tweet")
			        .setQuery(QueryBuilders.matchAllQuery())
			        .setSize(10000)
			        .execute()
			        .actionGet();
		}else{
			res = client.prepareSearch("twitter")
					.setTypes("tweet")
			        .setQuery(QueryBuilders.queryStringQuery(keyword))
			        .setSize(10000)
			        .execute()
			        .actionGet();
		}

		List<Tweet> tweetList = new ArrayList<Tweet>();
		SearchHit[] hits = res.getHits().getHits();
		System.out.println(keyword);
		System.out.println(hits.length);
		for(int i = 0;i < hits.length;i++){
			Tweet t = new Tweet();
			t.setUser(hits[i].getSource().get("user").toString());
			t.setText(hits[i].getSource().get("text").toString());
			t.setLongitude(Float.parseFloat(hits[i].getSource().get("longitude").toString()));
			t.setLatitude(Float.parseFloat(hits[i].getSource().get("latitude").toString()));
			t.setTime(hits[i].getSource().get("time").toString());
			tweetList.add(t);
		}
	
		JSONArray jsonList = JSONArray.fromObject(tweetList);
		out.println(jsonList);
	}
}
