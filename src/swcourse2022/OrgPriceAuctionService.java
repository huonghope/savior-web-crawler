package swcourse2022;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class OrgPriceAuctionService implements OpenAPI{
	/**
	 * '농경락'은 농산물의 실시간 경매 가격 정보 도매시장 도매법인 산지별 정보를 제공
	 * Documents: https://www.data.go.kr/tcs/dss/selectApiDataDetailView.do?publicDataPk=15012297
	 * URL: http://apis.data.go.kr/B552895/openapi/service/OrgPriceAuctionService/getExactProdPriceList
	 * - 원천정산경락가격품목목록조회: .../getExactProdPriceList
	 * - 원천정산경락가격도매시장목록조회: .../getExactMarketPriceList
	 * - 원천실시간경락가격품목목록조회: .../getRealProdPriceList
	 * - 원천실시간경락가격도매시장목록조회: .../getRealMarketPriceList
	 * Result: JSON+XML
	 * 
	 * */
	static ArrayList<Map> resultSet;
	static Boolean isGetChargerStatus = false;
	public OrgPriceAuctionService() {
		resultSet = new ArrayList<>();
	}

	@Override
	public void callOpenAPI() {
		// TODO Auto-generated method stub
			StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B552895/openapi/service/OrgPriceAuctionService/getExactProdPriceList"); /*URL*/
			try {
			urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "="+ Utils.getAPIKey()); /*Service Key*/
			urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지 번호*/
	        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("99", "UTF-8")); 
	        urlBuilder.append("&" + URLEncoder.encode("delngDe","UTF-8") + "=" + URLEncoder.encode("20151202", "UTF-8")); //경락일자
	        urlBuilder.append("&" + URLEncoder.encode("prdlstCd","UTF-8") + "=" + URLEncoder.encode("1001", "UTF-8")); //경락일자
	        urlBuilder.append("&" + URLEncoder.encode("_type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*xml(기본값) 또는 json*/
	        
			URL url = new URL(urlBuilder.toString());
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        try {
				conn.setRequestMethod("GET");
			} catch (ProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
	     		map.put("aucSeCode",data.get("aucSeCode"));
	     		map.put("aucSeNm",data.get("aucSeNm"));
	     		map.put("catgoryCode",data.get("catgoryCode"));
	     		map.put("catgoryNewCode",data.get("catgoryNewCode"));
	     		map.put("catgoryNewNm",data.get("catgoryNewNm"));
	     		map.put("catgoryNm",data.get("catgoryNm"));
	     		map.put("cprInsttCode",data.get("cprInsttCode"));
	     		map.put("cprInsttNewCode",data.get("cprInsttNewCode"));
	     		map.put("cprMtcCode",data.get("cprMtcCode"));
	     		map.put("cprMtcNm",data.get("cprMtcNm"));
	     		map.put("cprUsePrdlstCode",data.get("cprUsePrdlstCode"));
	     		map.put("cprUsePrdlstNm",data.get("cprUsePrdlstNm"));
	     		map.put("delngDe",data.get("delngDe"));
	     		map.put("delngPrut",data.get("delngPrut"));
	     		map.put("delngQy",data.get("delngQy"));
	     		map.put("insttNewNm",data.get("insttNewNm"));
	     		map.put("ledgNo",data.get("ledgNo"));
	     		map.put("rnum",data.get("rnum"));
	     		map.put("sbidPric",data.get("sbidPric"));
	     		map.put("shipmntSeCode",data.get("shipmntSeCode"));
	     		map.put("shipmntSeNm",data.get("shipmntSeNm"));
	     		map.put("sleSeqn",data.get("sleSeqn"));
	     		map.put("stdFrmlcNewCode",data.get("stdFrmlcNewCode"));
	     		map.put("stdFrmlcNewNm",data.get("stdFrmlcNewNm"));
	     		map.put("stdMgNewCode",data.get("stdMgNewCode"));
	     		map.put("stdMgNewNm",data.get("stdMgNewNm"));
	     		map.put("stdMtcNewCode",data.get("stdMtcNewCode"));
	     		map.put("stdMtcNewNm",data.get("stdMtcNewNm"));
	     		map.put("stdPrdlstCode",data.get("stdPrdlstCode"));
	     		map.put("stdPrdlstNewCode",data.get("stdPrdlstNewCode"));
	     		map.put("stdPrdlstNewNm",data.get("stdPrdlstNewNm"));
	     		map.put("stdPrdlstNm",data.get("stdPrdlstNm"));
	     		map.put("stdQlityNewCode",data.get("stdQlityNewCode"));
	     		map.put("stdQlityNewNm",data.get("stdQlityNewNm"));
	     		map.put("stdSpciesCode",data.get("stdSpciesCode"));
	     		map.put("stdSpciesNewCode",data.get("stdSpciesNewCode"));
	     		map.put("stdSpciesNm",data.get("stdSpciesNm"));
	     		map.put("stdUnitNewCode",data.get("stdUnitNewCode"));
	     		map.put("stdUnitNewNm",data.get("stdUnitNewNm"));
	     		map.put("whsalMrktCode",data.get("whsalMrktCode"));
	     		map.put("whsalMrktNewCode",data.get("whsalMrktNewCode"));
	     		map.put("whsalMrktNewNm",data.get("whsalMrktNewNm"));
	     		map.put("whsalMrktNm",data.get("whsalMrktNm"));
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
		String fileName = "OrgPriceAuctionService.xlsx";
    	Utils.saveDataToExcelFile(resultSet, fileName);
		
	}

}
