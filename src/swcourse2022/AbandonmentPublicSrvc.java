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

public class AbandonmentPublicSrvc implements OpenAPI {
	/**
	 * 유기견보호: 농림축산식품부 농림축산검역본부 동물보호관리시스템 유기동물 조회 서비스 동물보호관리시스템의 유기동물 정보를 조회할 수 있다
	 * Documents: https://www.data.go.kr/data/15098931/openapi.do
	 * URL: 
	 * - 시도 조회:  http://apis.data.go.kr/1543061/abandonmentPublicSrvc/sido
	 * - 시군구 조회: http://apis.data.go.kr/1543061/abandonmentPublicSrvc/sigungu
	 * - 보호소 조회: http://apis.data.go.kr/1543061/abandonmentPublicSrvc/shelter
	 * - 품종 조회: http://apis.data.go.kr/1543061/abandonmentPublicSrvc/kind
	 * - 유기동물 조회: http://apis.data.go.kr/1543061/abandonmentPublicSrvc/abandonmentPublic
	 * Result: JSON+XML
	 */
	static ArrayList<Map> resultSet;
	static Boolean isGetChargerStatus = false;
	public AbandonmentPublicSrvc() {
		resultSet = new ArrayList<>();
	}
			

	@Override
	public void callOpenAPI() {
		try {
		// TODO Auto-generated method stub
		StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1543061/abandonmentPublicSrvc/sido"); /*URL*/
		urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "="+ Utils.getAPIKey()); /*Service Key*/
		urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("999", "UTF-8")); /*한 페이지 결과 수(1,000 이하)*/
		urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지 번호*/
		urlBuilder.append("&" + URLEncoder.encode("_type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*xml(기본값) 또는 json*/
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
		System.out.println(sb.toString());
		
		JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject)jsonParser.parse(sb.toString());
        JSONObject response = (JSONObject)jsonObject.get("response");
        JSONObject body = (JSONObject)response.get("body");
        JSONObject items = (JSONObject)body.get("items");
        JSONArray dataArr = (JSONArray) items.get("item");
        System.out.println("size" + dataArr.size());
     	for(int i = 0; i < dataArr.size(); i++) {
     		JSONObject data = (JSONObject)dataArr.get(i);
     		Map<String, Object> map = new LinkedHashMap<>(); 
     		map.put("orgCd",data.get("orgCd"));
     		map.put("orgdownNm",data.get("orgdownNm"));
			resultSet.add(map);
		}
     	for(int i = 0; i < resultSet.size(); i++) {
        	System.out.println("====== Item : " + (i + 1) + " ======");
        	Map<String, Object> item = resultSet.get(i);
        	item.forEach((key, value) -> System.out.println(key + ":" + value));	
        	System.out.println("");
        }
	     	
		} catch (Exception e) {
			System.out.println(e);
			// TODO: handle exception
		}
	}

	@Override
	public void saveToExcel() {
		// TODO Auto-generated method stub
		String fileName = "AbandonmentPublicSrvc.xlsx";
    	Utils.saveDataToExcelFile(resultSet, fileName);
	}

}
