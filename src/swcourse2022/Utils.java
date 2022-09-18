package swcourse2022;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

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

public class Utils {
		static String  API_KEY = "1oRqU9tV/mfK1hpdUgBtBIxWSzmlAd4juPaiCvrxfXckTDzh6k5mir9Nl9fEBgFJ0D1AYNySFppcB0ZJfiG2lA==";
		
		public static Document createDocumentByURL(String url) {
			try {
				DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
		   		DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
		   		Document doc = dBuilder.parse(url.toString());
		   		doc.getDocumentElement().normalize();
		   		return doc;
			} catch (Exception e) {
				System.out.println(e);
				// TODO: handle exception
			}
			return null;
		}
		
		public static String getTagValue(String tag, Element eElement) {
	            String result = "";
		    	NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
		        Node nValue = (Node) nlList.item(0);
			    if(nValue == null) 
			        return null;
			    return nValue.getNodeValue();
		}
		public static String getTagValue(String tag, String childTag, Element eElement) {
			String result = "";
			NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
			for(int i = 0; i < eElement.getElementsByTagName(childTag).getLength(); i++) {
				result += nlList.item(i).getChildNodes().item(0).getTextContent() + " ";
			}
			return result;
		}
		
		public static void saveDataToExcelFile(ArrayList<Map> resultSet, String fileName) {
			if(resultSet.size() == 0) return;
			
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet = workbook.createSheet("Data");
			
			Map<String, Object[]> columnName = new TreeMap<>();
			Map<String, Object> itemTemp = resultSet.get(0);
			ArrayList<String> columnNameTemp = new ArrayList<String>();
			itemTemp.forEach((key, value) -> columnNameTemp.add(key));
	    	columnName.put("columnName", columnNameTemp.toArray());	
			
			boolean wroteColumnName = true;		
			int rownum = 0;
			for(int i = 0; i < resultSet.size(); i++) {
				Row row = sheet.createRow(rownum++);
				int cellnum = 0;
				if(wroteColumnName) {
					Object[] objArr = columnName.get("columnName");
					for (Object obj : objArr) {
		                Cell cell = row.createCell(cellnum++);
		                cell.setCellValue((String)obj);
		            }	
					cellnum = 0;
					row = sheet.createRow(rownum++);
				}
				wroteColumnName = false;
				Map<String, Object> item = resultSet.get(i);
				for(Entry<String, Object> entry : item.entrySet()) {
					 Cell cell = row.createCell(cellnum++);
		                if ( entry.getValue() instanceof String) {
		                    cell.setCellValue((String)entry.getValue());
		                } else if ( entry.getValue() instanceof Long) {
		                    cell.setCellValue((Long)entry.getValue());
		                } else if ( entry.getValue() instanceof Integer) {
		                    cell.setCellValue((Integer)entry.getValue());
		                }
				}		
			}
			
			try {
	            FileOutputStream out = new FileOutputStream(fileName);
	            workbook.write(out);
	            out.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		}
		
		public static String getAPIKey() {
			return API_KEY;
		}
		
		
}
