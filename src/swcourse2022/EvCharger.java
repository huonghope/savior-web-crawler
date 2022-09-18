package swcourse2022;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class EvCharger implements OpenAPI{
	/**
	 * 한국환경공단_전기자동차 충전소 정보
	 * Documents : https://www.data.go.kr/tcs/dss/selectApiDataDetailView.do?publicDataPk=15076352
	 * URL: http://apis.data.go.kr/B552584/EvCharger/getChargerStatus
	 * Result: XML/JSON
	 * */
	
	static ArrayList<Map> resultSet;
	static Boolean isGetChargerStatus = false;
	public EvCharger() {
		resultSet = new ArrayList<>();
	}
				
	@Override
	public void callOpenAPI() {
		// TODO Auto-generated method stub
		
		/*
		 * 충전기 상태 조회
		 * 전기자동차 보급 활성화 및 전기자동차 사용자를 위한 전국 공공 및 민간 충전기 상태 실시간 제공(충전소ID, 충전기ID, 상태정보)
		 * */
		String urlGetChargerStatus = "http://apis.data.go.kr/B552584/EvCharger/getChargerStatus";
		
			

		/*
		 * 충전기 정보  조회
		 * 전기자동차 보급 활성화 및 전기자동차 사용자를 위한 전국 공공 및 민간 충전기 운영현황 실시간 제공(충전소명, 위치, 상태정보, 운영시간, 운영기관명,충전기 용량)
		 * */
		String urlGetChargerInfo = "http://apis.data.go.kr/B552584/EvCharger/getChargerInfo";
		
		StringBuilder urlBuilder = isGetChargerStatus ? new StringBuilder(urlGetChargerStatus) : 
			new StringBuilder(urlGetChargerInfo); /*URL*/
	
		try {
			System.out.println(urlBuilder.toString());
			urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "="+ Utils.getAPIKey()); /*Service Key*/
	        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지 번호*/
	        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("99", "UTF-8")); /*한 페이지 결과 수 (최소 10, 최대 9999)*/
	        if(isGetChargerStatus) {
	        	urlBuilder.append("&" + URLEncoder.encode("period","UTF-8") + "=" + URLEncoder.encode("5", "UTF-8")); /*상태갱신 조회 범위(분) (기본값 5, 최소 1, 최대 10)*/
	        }
	        urlBuilder.append("&" + URLEncoder.encode("zcode","UTF-8") + "=" + URLEncoder.encode("11", "UTF-8")); /*시도 코드 (행정구역코드 앞 2자리)*/
	        
	        // 공공데이터 포털 제공하는 Simple Code
	        URL url = new URL(urlBuilder.toString());
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("GET");
	        conn.setRequestProperty("Content-type", "application/json");
	        System.out.println("Response code: " + conn.getResponseCode());
	        BufferedReader rd;
	        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
	        	 rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));	        	 
	        } else {
	        	rd = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"));
	        }
	        StringBuilder sb = new StringBuilder();
	        String line;
	        while ((line = rd.readLine()) != null) {
	            sb.append(line);
	        }
	        rd.close();
	        conn.disconnect();
	        System.out.println("Result" + sb.toString());
      		
      		if(isGetChargerStatus){
      			Document doc = Utils.createDocumentByURL(urlBuilder.toString());        
    	        NodeList nList = doc.getElementsByTagName("item");
    	        System.out.println(nList.getLength());
	      		for(int temp = 0; temp < nList.getLength(); temp++){
					Node nNode = nList.item(temp);
					Element eElement = (Element) nNode;
					Map<String, Object> map = new LinkedHashMap<>(); 
					map.put("busiId",Utils.getTagValue("busiId", eElement));
					map.put("statId",Utils.getTagValue("statId", eElement));
					map.put("chgerId",Utils.getTagValue("chgerId", eElement));
					map.put("stat",Utils.getTagValue("stat", eElement));
					map.put("statUpdDt",Utils.getTagValue("statUpdDt", eElement));
					map.put("lastTsdt",Utils.getTagValue("lastTsdt", eElement));
					map.put("lastTedt",Utils.getTagValue("lastTedt", eElement));
					map.put("nowTsdt",Utils.getTagValue("nowTsdt", eElement));
					resultSet.add(map);
				}
	      		for(int i = 0; i < resultSet.size(); i++) {
    	        	System.out.println("====== Item : " + (i + 1) + " ======");
    	        	Map<String, Object> item = resultSet.get(i);
    	        	item.forEach((key, value) -> System.out.println(key + ":" + value));	
    	        	System.out.println("");
    	        }
      		}else {
      			Document doc = Utils.createDocumentByURL(urlBuilder.toString());        
    	        NodeList nList = doc.getElementsByTagName("item");
    	        System.out.println(nList.getLength());
    	        for(int temp = 0; temp < nList.getLength(); temp++){
	      			
					Node nNode = nList.item(temp);
					if(nNode.getNodeType() == Node.ELEMENT_NODE){
						Element eElement = (Element) nNode;			
						Map<String, Object> map = new LinkedHashMap<>(); 
						map.put("statNm",Utils.getTagValue("statNm", eElement));
						map.put("statId",Utils.getTagValue("statId", eElement));
						map.put("chgerId",Utils.getTagValue("chgerId", eElement));
						map.put("chgerType",Utils.getTagValue("chgerType", eElement));
						map.put("addr",Utils.getTagValue("addr", eElement));
						map.put("location",Utils.getTagValue("location", eElement));
						map.put("lat",Utils.getTagValue("lat", eElement));
						map.put("lng",Utils.getTagValue("lng", eElement));
						map.put("useTime",Utils.getTagValue("useTime", eElement));
						map.put("busiId",Utils.getTagValue("busiId", eElement));
						map.put("bnm",Utils.getTagValue("bnm", eElement));
						map.put("busiNm",Utils.getTagValue("busiNm", eElement));
						map.put("busiCall",Utils.getTagValue("busiCall", eElement));
						map.put("stat",Utils.getTagValue("stat", eElement));
						map.put("statUpdDt",Utils.getTagValue("statUpdDt", eElement));
						map.put("lastTsdt",Utils.getTagValue("lastTsdt", eElement));
						map.put("lastTedt",Utils.getTagValue("lastTedt", eElement));
						map.put("nowTsdt",Utils.getTagValue("nowTsdt", eElement));
						map.put("powerType",Utils.getTagValue("powerType", eElement));
						map.put("output",Utils.getTagValue("output", eElement));
						map.put("method",Utils.getTagValue("method", eElement));
						map.put("zcode",Utils.getTagValue("zcode", eElement));
						map.put("zscode",Utils.getTagValue("zscode", eElement));
						map.put("kind",Utils.getTagValue("kind", eElement));
						map.put("kindDetail",Utils.getTagValue("kindDetail", eElement));
						map.put("parkingFree",Utils.getTagValue("parkingFree", eElement));
						map.put("note",Utils.getTagValue("note", eElement));
						map.put("limitYn",Utils.getTagValue("limitYn", eElement));
						map.put("limitDetail",Utils.getTagValue("limitDetail", eElement));
						map.put("delYn",Utils.getTagValue("delYn", eElement));
						map.put("delDetail",Utils.getTagValue("delDetail", eElement));
						resultSet.add(map);
					}
				}
    	        for(int i = 0; i < resultSet.size(); i++) {
    	        	System.out.println("====== Item : " + (i + 1) + " ======");
    	        	Map<String, Object> item = resultSet.get(i);
    	        	item.forEach((key, value) -> System.out.println(key + ":" + value));	
    	        	System.out.println("");
    	        }
    	       
      		}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
	}

	@Override
	public void saveToExcel() {
		// TODO Auto-generated method stub
    	String fileName = isGetChargerStatus ? "EvChargerStatus.xlsx" : "EvChargerInfo.xlsx";
    	Utils.saveDataToExcelFile(resultSet, fileName);
	}

}
