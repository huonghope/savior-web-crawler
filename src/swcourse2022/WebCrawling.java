package swcourse2022;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebCrawling {
	public static final String jdbcDriver	= "com.mysql.cj.jdbc.Driver";

	public static final String dbConUrl		= "jdbc:mysql://210.94.194.63:3306/savior?characterEncoding=UTF-8&serverTimezone=UTC";
	
	public static final String id			= "plass";
	
	public static final String passwd		= "plass3343##$#";
		    
	public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
		
			
		//Set the URL to connect
		String Domain = "https://www.saramin.co.kr";
		String URL = "https://www.saramin.co.kr/zf_user/";
		
		//Fetch and parse HTML file from the web into a Document object
		Document docs = Jsoup.connect(URL).timeout(6000).get();
		Elements contents = docs.select("ul.list_product");
		Elements productListLink = contents.select("a[href]");
		
		Class.forName(jdbcDriver);
		Connection con = DriverManager.getConnection(dbConUrl,id,passwd);
		
		String query ="INSERT INTO `job`(`company`,`title`,`exp`,`education`,`worktype`,`salary`,`address`,`link`) VALUES (?,?,?,?,?,?,?,?)";
		PreparedStatement pStmt = con.prepareStatement(query);
		
	
		for (Element element : productListLink) {
			String link = element.attr("href");
			String endLink = link.substring(link.length() - 5, link.length());
			
			//Only matching with URL sarami
			if(endLink.matches("[0-9].*")){
				System.out.println("=================================");
				String linkFull = Domain + link;
				Document subDocs = Jsoup.connect(linkFull).timeout(6000).get();
				Elements wrapContent = subDocs.select("div.wrap_jv_cont");
				Elements wrapContentHeader = wrapContent.select("div.wrap_jv_header");
				Elements jvHeader = wrapContentHeader.select("div.jv_header");
				
				Elements company = jvHeader.select("a.company");
				Elements titJob = jvHeader.select("h1.tit_job");
				
				String companyName = company.text();
				String titJobDesc = titJob.text();
				
				Elements jobSummary = wrapContent.select("div.jv_summary");
				Elements jobSummaryConten = jobSummary.select("div.cont");
				Elements jobSubElementsContentCol  = jobSummaryConten.select("div.col");
				Elements firstCol = jobSubElementsContentCol.get(0).select("dl > dd > strong");
				Elements secondCol = jobSubElementsContentCol.get(1).select("dl > dd");
				
				String exp = firstCol.get(0).text();
				String education = firstCol.get(1).text();
				String workType = firstCol.get(2).text();
				String salary = secondCol.get(0).text();
				String address = secondCol.get(1).text();
				executeUpdate(pStmt, company.text(), titJob.text(), exp, education, workType, salary, address, linkFull);
				System.out.println("=================================");
				
			}
			
			
	
		}

	}
	public static int executeUpdate(PreparedStatement pStmt, String company,String title, String exp, String education, String workType, String salary, String address, String url) throws SQLException{
		int updatedCnt = 0;
		int i = 1;
		pStmt.setString(i++,company);
		pStmt.setString(i++,title);
		pStmt.setString(i++,exp);
		pStmt.setString(i++,education);
		pStmt.setString(i++,workType);
		pStmt.setString(i++,salary);
		pStmt.setString(i++,address);
		pStmt.setString(i++,url);
		updatedCnt = pStmt.executeUpdate() ;
		return updatedCnt;
	}
}
