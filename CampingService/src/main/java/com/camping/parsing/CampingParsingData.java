package com.camping.parsing;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Value;
import org.w3c.dom.Document;
import org.xml.sax.SAXParseException;

public class CampingParsingData {
	public static Connection conn = null;
	public static PreparedStatement pstmt = null;
	public static ResultSet rs = null;
	
	public static String driverClass = "com.mysql.cj.jdbc.Driver";
	public static String url = "jdbc:mysql://localhost:3306/camping";
	
	public static String user = "";
	public static String password = "";
	
//	@Value("${spring.datasource.username}")
//	private static String user;
//	
//	@Value("${spring.datasource.password}")
//	private static String password;
	
	public static final String KEY = "sHpLWgiDMLTAM87%2BhYNCvIMekwE38WDEWlRi14j7FuNOn7oRMkfFoVCne1d7TmSS3G1KtCBCq2wOjpbmkNNyzA%3D%3D";

    public static void main(String[] args) throws IOException {
    	try {
    		Class.forName(driverClass);
    		conn = DriverManager.getConnection(url, user, password);
    		conn.setAutoCommit(true);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	
        parseCamping();
    }

    public static void parseCamping() {
        try {
        
        Camping camp = new Camping();
        ArrayList<Camping> list = new ArrayList<>();
        
            String pageNo = "1";
            String numRow = "10";
            String OS = "WIN";
            String AppName = "camping";
            int num = Integer.parseInt(numRow);
          
	    	int totalPage = 4029 / num; //4029
	    	System.out.println("총페이지 수 : " + totalPage);
	    	int count =0; //페이지 수 카운트용
            System.out.println("현재 페이지 번호 : " + count);
            
            int debug = 0;
            
			int siteID = 0;
			String siteCOMPANY = new String();
			String siteLOCATE = new String();
			String siteINDUTY = new String();
			String siteFACILITY = new String();
			String siteSTATUS = new String();
			int siteWATERAMOUNT = 0;
			int siteTOILETAMOUNT = 0;
			int siteSHOWERAMOUNT = 0;
			String siteFEATURE = new String();
			String siteADDR = new String();
			double siteX = 0;
			double siteY = 0;
			String siteTEL = new String();
			String siteURL = new String();
			String siteTERM = new String();
			String siteOPER = new String();
			String siteANIMAL = new String();
			String siteIMAGE = new String();
			
            
            
			for(int i = 0; i < totalPage; i++) {
                count++;
                pageNo = String.valueOf(count);

                StringBuilder urlBuilder = new StringBuilder("https://apis.data.go.kr/B551011/GoCamping/basedList"); /* URL */
                urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + KEY); /* Service Key */
                urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode(numRow, "UTF-8")); /* 목록 건수 */
                urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode(pageNo, "UTF-8")); /* 페이지 번호 */
                urlBuilder.append("&" + URLEncoder.encode("MobileOS", "UTF-8") + "=" + URLEncoder.encode(OS, "UTF-8")); /* 운영체제 */
                urlBuilder.append("&" + URLEncoder.encode("MobileApp", "UTF-8") + "=" + URLEncoder.encode(AppName, "UTF-8")); /* 프로그램이름 */

                	
	            URL url = new URL(urlBuilder.toString());
	            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	            conn.setRequestMethod("GET");
	            conn.setRequestProperty("Content-type", "application/json");
//            	System.out.println("Response code: " + conn.getResponseCode());
	
	            if(conn.getResponseCode() < 200 || conn.getResponseCode() >=300) {
	            System.out.println("페이지를 찾을수 없습니다.");
	            return;
	            }
	            
	            DocumentBuilderFactory dbf;
	            DocumentBuilder db;
	            Document doc;
	            try {
		            dbf = DocumentBuilderFactory.newInstance();
		            db = dbf.newDocumentBuilder();
		            doc = db.parse(conn.getInputStream());
		            doc.normalizeDocument();
	            } catch (SAXParseException e) {
	            System.out.println("백스페이스 오류");
	            e.printStackTrace();
	            continue;
	            }
	            
	            
//            	System.out.println("Root element(=tag) : " + doc.getDocumentElement().getNodeName());
	            System.out.println("----------------------------------------------------------------");
	            System.out.println("페이지 수 : " + pageNo);
	            for(int j = 0; j < 10; j++) {
	            try {
	            	siteID = siteID + 1;
						try{ siteCOMPANY = doc.getElementsByTagName("facltNm").item(j).getTextContent();                         } catch(Exception e){debug++; continue;}
						try{ siteLOCATE = doc.getElementsByTagName("lctCl").item(j).getTextContent();                            } catch(Exception e){e.printStackTrace();}
						try{ siteINDUTY = doc.getElementsByTagName("induty").item(j).getTextContent();                           } catch(Exception e){e.printStackTrace();}
						try{ siteFACILITY = doc.getElementsByTagName("sbrsCl").item(j).getTextContent();                         } catch(Exception e){e.printStackTrace();}
						try{ siteSTATUS = doc.getElementsByTagName("manageSttus").item(j).getTextContent();                      } catch(Exception e){e.printStackTrace();}
						try{ siteWATERAMOUNT = Integer.parseInt(doc.getElementsByTagName("wtrplCo").item(j).getTextContent());   } catch(Exception e){e.printStackTrace();}
						try{ siteTOILETAMOUNT = Integer.parseInt(doc.getElementsByTagName("toiletCo").item(j).getTextContent()); } catch(Exception e){e.printStackTrace();}
						try{ siteSHOWERAMOUNT = Integer.parseInt(doc.getElementsByTagName("swrmCo").item(j).getTextContent());   } catch(Exception e){e.printStackTrace();}
						try{ siteFEATURE = doc.getElementsByTagName("featureNm").item(j).getTextContent();                       } catch(Exception e){}
						try{ siteADDR = doc.getElementsByTagName("addr1").item(j).getTextContent();                              } catch(Exception e){}
						try{ siteX = Double.parseDouble(doc.getElementsByTagName("mapX").item(j).getTextContent());                } catch(Exception e){}
						try{ siteY = Double.parseDouble(doc.getElementsByTagName("mapY").item(j).getTextContent());                } catch(Exception e){}
						try{ siteTEL = doc.getElementsByTagName("tel").item(j).getTextContent();                                 } catch(Exception e){}
						try{ siteURL = doc.getElementsByTagName("homepage").item(j).getTextContent();                            } catch(Exception e){}
						try{ siteTERM = doc.getElementsByTagName("operPdCl").item(j).getTextContent();                           } catch(Exception e){}
						try{ siteOPER = doc.getElementsByTagName("operDeCl").item(j).getTextContent();                           } catch(Exception e){}
						try{ siteANIMAL = doc.getElementsByTagName("animalCmgCl").item(j).getTextContent();                      } catch(Exception e){}
						try{ siteIMAGE = doc.getElementsByTagName("firstImageUrl").item(j).getTextContent();                     } catch(Exception e){}
	            } catch(Exception e) {e.printStackTrace();}	        
//            	int currentPageNo = Integer.parseInt(doc.getElementsByTagName("pageNo").item(j).getTextContent());
//            	int totalPage = Integer.parseInt(doc.getElementsByTagName("totalCount").item(j).getTextContent()) / Integer.parseInt(doc.getElementsByTagName("numofRows").item(j).getTextContent());
			        
            	try {
            		camp = new Camping();
						camp.setSiteID(siteID);
						camp.setSiteCOMPANY(siteCOMPANY);
						camp.setSiteLOCATE(siteLOCATE);
						camp.setSiteINDUTY(siteINDUTY);
						camp.setSiteFACILITY(siteFACILITY);
						camp.setSiteSTATUS(siteSTATUS);
						camp.setSiteWATERAMOUNT(siteWATERAMOUNT);
						camp.setSiteTOILETAMOUNT(siteTOILETAMOUNT);
						camp.setSiteSHOWERAMOUNT(siteSHOWERAMOUNT);
						camp.setSiteFEATURE(siteFEATURE);
						camp.setSiteADDR(siteADDR);
						camp.setSiteX(siteX);
						camp.setSiteY(siteY);
						camp.setSiteTEL(siteTEL);
						camp.setSiteURL(siteURL);
						camp.setSiteTERM(siteTERM);
						camp.setSiteOPER(siteOPER);
						camp.setSiteANIMAL(siteANIMAL);
						camp.setSiteIMAGE(siteIMAGE);
            	} catch(Exception e) {
            		e.printStackTrace();
            	}
            	
            	list.add(camp);
	            	
	            	//totalPage
			        if(count > totalPage) {
			        System.out.println("파싱 완료");
			        conn.disconnect();
			        }
	            }
	            try {
		        System.out.println("현재 파싱 갯수 : " + num * count + " /" + totalPage * 10 + "(" + String.format("%.3f",(((double)num * (double)count)/totalPage)*10) + "%)");
		        } catch(Exception e) {
		        e.printStackTrace();
		        }
	            
            }
			list.forEach(s -> System.out.println(s));
			System.out.println("이름없는 캠핑장 개수: " + debug);
			for(Camping c : list) {
        		insert(c);
        	}
            
	    }catch (Exception e) {
	    	e.printStackTrace();
		}
    }
    
	
	
	 
    
    private static void insert(Camping m) throws SQLException {
		System.out.println("Insert 시작!");
		int result = 0;
		
		try {
			String sql = "INSERT INTO CAMPING_SITE(siteID, siteCOMPANY, siteLOCATE, siteINDUTY, siteFACILITY, siteSTATUS, siteWATERAMOUNT, siteTOILETAMOUNT, "
					+ " siteSHOWERAMOUNT, siteFEATURE, siteADDR, siteX, siteY, siteTEL, siteURL, siteTERM, siteOPER, siteANIMAL, siteIMAGE) "
					+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1,m.siteID);
			pstmt.setString(2,m.siteCOMPANY);
			pstmt.setString(3,m.siteLOCATE);
			pstmt.setString(4,m.siteINDUTY);
			pstmt.setString(5,m.siteFACILITY);
			pstmt.setString(6,m.siteSTATUS);
			pstmt.setInt(7,m.siteWATERAMOUNT);
			pstmt.setInt(8,m.siteTOILETAMOUNT);
			pstmt.setInt(9,m.siteSHOWERAMOUNT);
			pstmt.setString(10,m.siteFEATURE);
			pstmt.setString(11,m.siteADDR);
			pstmt.setDouble(12,m.siteX);
			pstmt.setDouble(13,m.siteY);
			pstmt.setString(14,m.siteTEL);
			pstmt.setString(15,m.siteURL);
			pstmt.setString(16,m.siteTERM);
			pstmt.setString(17,m.siteOPER);
			pstmt.setString(18,m.siteANIMAL);
			pstmt.setString(19,m.siteIMAGE);
			System.out.println("중복검사:"+m.toString());
	
			
			result = pstmt.executeUpdate();
		} catch(SQLException e){
			e.printStackTrace();
		}
		
		if(result > 0) {
			System.out.println("insert 성공");
		} else {
			System.out.println("insert 실패");
		}
		System.out.println("Insert 종료\n");
	}
}