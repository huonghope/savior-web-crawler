package swcourse2022;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class EvInfoServiceV2 implements OpenAPI {
	/**
	 * 한국전력공사_전기차 충전소 운영정보
	 * Documents : https://www.data.go.kr/tcs/dss/selectApiDataDetailView.do?publicDataPk=3068728
	 * URL: http://openapi.kepco.co.kr/service/EvInfoServiceV2
	 * Result: XML
	 * */

	public static Map<String, Object> map;
	public EvInfoServiceV2() {
		map = new HashMap<>(); 
	}
	@Override
	public void callOpenAPI() {
		// TODO Auto-generated method stub
		StringBuilder urlBuilder = new StringBuilder("http://openapi.kepco.co.kr/service/EvInfoServiceV2/getEvSearchList"); /*URL*/
		try {	
	    urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + Utils.getAPIKey()); /*Service Key*/
	    urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
	    urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
	    urlBuilder.append("&" + URLEncoder.encode("addr","UTF-8") + "=" + URLEncoder.encode("전라남도 나주시 전력로 55", "UTF-8")); /*검색대상 충전소주소*/
	    URL url = new URL(urlBuilder.toString());
	    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	    conn.setRequestMethod("GET");
	    conn.setRequestProperty("Content-type", "application/json");
	    System.out.println("Response code: " + conn.getResponseCode());
	    BufferedReader rd;
	    if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
	        rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	    } else {
	        rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
	    }
	    StringBuilder sb = new StringBuilder();
	    String line;
	    while ((line = rd.readLine()) != null) {
	        sb.append(line);
	    }
	    rd.close();
	    conn.disconnect();
	    	   
	    Document doc = Utils.createDocumentByURL(urlBuilder.toString());        
        NodeList nList = doc.getElementsByTagName("item");
  		System.out.println(nList.getLength());
  		for(int temp = 0; temp < nList.getLength(); temp++){
			Node nNode = nList.item(temp);
			Element eElement = (Element) nNode;
			map.put("busiId",Utils.getTagValue("busiId", eElement));
			map.put("statId",Utils.getTagValue("statId", eElement));
			map.put("chgerId",Utils.getTagValue("chgerId", eElement));
			map.put("stat",Utils.getTagValue("stat", eElement));
			map.put("statUpdDt",Utils.getTagValue("statUpdDt", eElement));
			map.put("lastTsdt",Utils.getTagValue("lastTsdt", eElement));
			map.put("lastTedt",Utils.getTagValue("lastTedt", eElement));
			
			System.out.println("busiId : " + Utils.getTagValue("busiId", eElement));
			System.out.println("statId : " + Utils.getTagValue("statId", eElement));
			System.out.println("chgerId : " + Utils.getTagValue("chgerId", eElement));
			System.out.println("stat " + Utils.getTagValue("stat", eElement));
			System.out.println("statUpdDt " + Utils.getTagValue("statUpdDt", eElement));
			System.out.println("lastTsdt " + Utils.getTagValue("lastTsdt", eElement));
			System.out.println("lastTedt " + Utils.getTagValue("lastTedt", eElement));
		}
		}catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public void saveToExcel() {
		// TODO Auto-generated method stub
		
	}
	
}
