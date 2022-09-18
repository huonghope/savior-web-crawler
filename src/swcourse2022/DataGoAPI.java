package swcourse2022;


public class DataGoAPI {
 	public static void main(String[] args) {
 		
 		// Open API Key don't work
 		EvInfoServiceV2 evInfoServiceV2 = new EvInfoServiceV2();
 		//evInfoServiceV2.callOpenAPI();		
 		//evInfoServiceV2.saveToExcel();
 		
 		//EvCharger evchanger = new EvCharger();
 		//evchanger.callOpenAPI();
 		//evchanger.saveToExcel();
 		
// 		AbandonmentPublicSrvc abandonmentPublicSrvc = new AbandonmentPublicSrvc();
// 		abandonmentPublicSrvc.callOpenAPI();
// 		abandonmentPublicSrvc.saveToExcel();
// 		
// 		PigJejuGrade pigJejuGrade = new PigJejuGrade();
// 		pigJejuGrade.callOpenAPI();
// 		pigJejuGrade.saveToExcel();
// 		
 		OrgPriceAuctionService orgPriceAuctionService = new OrgPriceAuctionService();
 		orgPriceAuctionService.callOpenAPI();
 		orgPriceAuctionService.saveToExcel();
 		
	}	
	
	
}
