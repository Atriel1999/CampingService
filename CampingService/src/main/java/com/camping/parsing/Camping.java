package com.camping.parsing;

import java.util.List;

public class Camping {
	int siteID;
	String siteCOMPANY;
	String siteLOCATE;
	String siteINDUTY;
	String siteFACILITY;
	String siteSTATUS;
	int siteWATERAMOUNT;
	int siteTOILETAMOUNT;
	int siteSHOWERAMOUNT;
	String siteFEATURE;
	String siteADDR;
	double siteX;
	double siteY;
	String siteTEL;
	String siteURL;
	String siteTERM;
	String siteOPER;
	String siteANIMAL;
	String siteIMAGE;
	
	public Camping() {
		super();
	}
	
	public Camping(int siteID, String siteCOMPANY, String siteLOCATE, String siteINDUTY, String siteFACILITY,
			String siteSTATUS, int siteWATERAMOUNT, int siteTOILETAMOUNT, int siteSHOWERAMOUNT, String siteFEATURE,
			String siteADDR, double siteX, double siteY, String siteTEL, String siteURL, String siteTERM,
			String siteOPER, String siteANIMAL, String siteIMAGE) {
		super();
		this.siteID = siteID;
		this.siteCOMPANY = siteCOMPANY;
		this.siteLOCATE = siteLOCATE;
		this.siteINDUTY = siteINDUTY;
		this.siteFACILITY = siteFACILITY;
		this.siteSTATUS = siteSTATUS;
		this.siteWATERAMOUNT = siteWATERAMOUNT;
		this.siteTOILETAMOUNT = siteTOILETAMOUNT;
		this.siteSHOWERAMOUNT = siteSHOWERAMOUNT;
		this.siteFEATURE = siteFEATURE;
		this.siteADDR = siteADDR;
		this.siteX = siteX;
		this.siteY = siteY;
		this.siteTEL = siteTEL;
		this.siteURL = siteURL;
		this.siteTERM = siteTERM;
		this.siteOPER = siteOPER;
		this.siteANIMAL = siteANIMAL;
		this.siteIMAGE = siteIMAGE;
	}
	@Override
	public String toString() {
		return "Camping [siteID=" + siteID + ", siteCOMPANY=" + siteCOMPANY + ", siteLOCATE=" + siteLOCATE
				+ ", siteINDUTY=" + siteINDUTY + ", siteFACILITY=" + siteFACILITY + ", siteSTATUS=" + siteSTATUS
				+ ", siteWATERAMOUNT=" + siteWATERAMOUNT + ", siteTOILETAMOUNT=" + siteTOILETAMOUNT
				+ ", siteSHOWERAMOUNT=" + siteSHOWERAMOUNT + ", siteFEATURE=" + siteFEATURE + ", siteADDR=" + siteADDR
				+ ", siteX=" + siteX + ", siteY=" + siteY + ", siteTEL=" + siteTEL + ", siteURL=" + siteURL
				+ ", siteTERM=" + siteTERM + ", siteOPER=" + siteOPER + ", siteANIMAL=" + siteANIMAL + ", siteIMAGE="
				+ siteIMAGE + "]";
	}
	public int getSiteID() {
		return siteID;
	}
	public void setSiteID(int siteID) {
		this.siteID = siteID;
	}
	public String getSiteCOMPANY() {
		return siteCOMPANY;
	}
	public void setSiteCOMPANY(String siteCOMPANY) {
		this.siteCOMPANY = siteCOMPANY;
	}
	public String getSiteLOCATE() {
		return siteLOCATE;
	}
	public void setSiteLOCATE(String siteLOCATE) {
		this.siteLOCATE = siteLOCATE;
	}
	public String getSiteINDUTY() {
		return siteINDUTY;
	}
	public void setSiteINDUTY(String siteINDUTY) {
		this.siteINDUTY = siteINDUTY;
	}
	public String getSiteFACILITY() {
		return siteFACILITY;
	}
	public void setSiteFACILITY(String siteFACILITY) {
		this.siteFACILITY = siteFACILITY;
	}
	public String getSiteSTATUS() {
		return siteSTATUS;
	}
	public void setSiteSTATUS(String siteSTATUS) {
		this.siteSTATUS = siteSTATUS;
	}
	public int getSiteWATERAMOUNT() {
		return siteWATERAMOUNT;
	}
	public void setSiteWATERAMOUNT(int siteWATERAMOUNT) {
		this.siteWATERAMOUNT = siteWATERAMOUNT;
	}
	public int getSiteTOILETAMOUNT() {
		return siteTOILETAMOUNT;
	}
	public void setSiteTOILETAMOUNT(int siteTOILETAMOUNT) {
		this.siteTOILETAMOUNT = siteTOILETAMOUNT;
	}
	public int getSiteSHOWERAMOUNT() {
		return siteSHOWERAMOUNT;
	}
	public void setSiteSHOWERAMOUNT(int siteSHOWERAMOUNT) {
		this.siteSHOWERAMOUNT = siteSHOWERAMOUNT;
	}
	public String getSiteFEATURE() {
		return siteFEATURE;
	}
	public void setSiteFEATURE(String siteFEATURE) {
		this.siteFEATURE = siteFEATURE;
	}
	public String getSiteADDR() {
		return siteADDR;
	}
	public void setSiteADDR(String siteADDR) {
		this.siteADDR = siteADDR;
	}
	public double getSiteX() {
		return siteX;
	}
	public void setSiteX(double siteX) {
		this.siteX = siteX;
	}
	public double getSiteY() {
		return siteY;
	}
	public void setSiteY(double siteY) {
		this.siteY = siteY;
	}
	public String getSiteTEL() {
		return siteTEL;
	}
	public void setSiteTEL(String siteTEL) {
		this.siteTEL = siteTEL;
	}
	public String getSiteURL() {
		return siteURL;
	}
	public void setSiteURL(String siteURL) {
		this.siteURL = siteURL;
	}
	public String getSiteTERM() {
		return siteTERM;
	}
	public void setSiteTERM(String siteTERM) {
		this.siteTERM = siteTERM;
	}
	public String getSiteOPER() {
		return siteOPER;
	}
	public void setSiteOPER(String siteOPER) {
		this.siteOPER = siteOPER;
	}
	public String getSiteANIMAL() {
		return siteANIMAL;
	}
	public void setSiteANIMAL(String siteANIMAL) {
		this.siteANIMAL = siteANIMAL;
	}
	public String getSiteIMAGE() {
		return siteIMAGE;
	}
	public void setSiteIMAGE(String siteIMAGE) {
		this.siteIMAGE = siteIMAGE;
	}
}
