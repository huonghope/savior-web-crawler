package swcourse2022;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class PigJejuGrade implements OpenAPI{
	/**
	 * 한우 낙농 . 축산물품질평가원 축산물경락가격정보 : 축산물 소 · 돼지 · 닭 · 계란 · 오리 에 대한 등급판정 결과 고기 및 부산물 정보 도매시장 정보 경매 현황 도페 육질.
	 * Documents: https://www.data.go.kr/tcs/dss/selectApiDataDetailView.do?publicDataPk=15057912
	 * URL: 
	 * - 제주 돼지도체 등급별 경락가격 정보 조회: http://data.ekape.or.kr/openapi-data/service/user/grade/auct/pigJejuGrade
	 * - 쇠고기 부분육 경락가격 조회: http://data.ekape.or.kr/openapi-data/service/user/grade/auct/beefGrade
	 * Result: XML
	 **/
	
	
	static ArrayList<Map> resultSet;
	static Boolean isGetChargerStatus = false;
	public PigJejuGrade() {
		resultSet = new ArrayList<>();
	}
	
	@Override
	public void callOpenAPI() {
		// TODO Auto-generated method stub
		
		
		 StringBuilder urlBuilder = new StringBuilder("http://data.ekape.or.kr/openapi-data/service/user/grade/auct/pigJejuGrade"); /*URL*/
		 try {
		 urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "="+ Utils.getAPIKey()); /*Service Key*/
	        urlBuilder.append("&" + URLEncoder.encode("startYmd","UTF-8") + "=" + URLEncoder.encode("20160120", "UTF-8")); /*경매시작일받은 인증키*/
	        urlBuilder.append("&" + URLEncoder.encode("endYmd","UTF-8") + "=" + URLEncoder.encode("20160120", "UTF-8")); /*경매종료일*/
	        urlBuilder.append("&" + URLEncoder.encode("skinYn","UTF-8") + "=" + URLEncoder.encode("Y", "UTF-8")); /*N 박피 Y 탕박 Default 탕박 ※입력값이 Y/N이 아닌경우*/
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
				Map<String, Object> map = new LinkedHashMap<>(); 
				map.put("gradeCode",Utils.getTagValue("gradeCode", eElement));
				map.put("gradeName",Utils.getTagValue("gradeName", eElement));
				map.put("totPrice",Utils.getTagValue("totPrice", eElement));
				map.put("totCnt",Utils.getTagValue("totCnt", eElement));
				map.put("tot1Price",Utils.getTagValue("tot1Price", eElement));
				map.put("tot1Cnt",Utils.getTagValue("tot1Cnt", eElement));
				map.put("tot2Price",Utils.getTagValue("tot2Price", eElement));
				map.put("tot2Cnt",Utils.getTagValue("tot2Cnt", eElement));
				map.put("tot3Price",Utils.getTagValue("tot3Price", eElement));
				map.put("tot3Cnt",Utils.getTagValue("tot3Cnt", eElement));
				map.put("publicTotPrice",Utils.getTagValue("publicTotPrice", eElement));
				map.put("publicTotCnt",Utils.getTagValue("publicTotCnt", eElement));
				map.put("public1Price",Utils.getTagValue("public1Price", eElement));
				map.put("public1Cnt",Utils.getTagValue("public1Cnt", eElement));
				map.put("public2Price",Utils.getTagValue("public2Price", eElement));
				map.put("public2Cnt",Utils.getTagValue("public2Cnt", eElement));
				map.put("public3Price",Utils.getTagValue("public3Price", eElement));
				map.put("public3Cnt",Utils.getTagValue("public3Cnt", eElement));
				map.put("blackTotPrice",Utils.getTagValue("blackTotPrice", eElement));
				map.put("blackTotCnt",Utils.getTagValue("blackTotCnt", eElement));
				map.put("black1Price",Utils.getTagValue("black1Price", eElement));
				map.put("black1Cnt",Utils.getTagValue("black1Cnt", eElement));
				map.put("black2Price",Utils.getTagValue("black2Price", eElement));
				map.put("black2Cnt",Utils.getTagValue("black2Cnt", eElement));
				map.put("black3Price",Utils.getTagValue("black3Price", eElement));
				map.put("black3Cnt",Utils.getTagValue("black3Cnt", eElement));			
				resultSet.add(map);
			}
      		for(int i = 0; i < resultSet.size(); i++) {
	        	System.out.println("====== Item : " + (i + 1) + " ======");
	        	Map<String, Object> item = resultSet.get(i);
	        	item.forEach((key, value) -> System.out.println(key + ":" + value));	
	        	System.out.println("");
	        }
		}catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public void saveToExcel() {
		// TODO Auto-generated method stub
		String fileName = "PigJejuGrade.xlsx";
    	Utils.saveDataToExcelFile(resultSet, fileName);
	}

}
