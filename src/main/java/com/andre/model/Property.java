package com.andre.model;

import com.andre.json.annotations.JSON;
import com.andre.json.annotations.JSONField;

@JSON
public class Property {
	
	@JSONField(jsonFieldName="id")
	private String id;
	private String address;
	@JSONField(jsonFieldName="ListPrice")
	private Double price;
	@JSONField(jsonFieldName="LivingArea")
	private Double squareFootage;
	@JSONField(jsonFieldName="BedroomsTotal")
	private Double beds;
	@JSONField(jsonFieldName="BathroomsTotalInteger")
	private Double baths;
	private String openHouse;
	@JSONField(jsonFieldName="StandardStatus")
	private String standardStatus;
	
	
	@JSONField(jsonFieldName="DaysOnMarket", isEnrichment=true)
	private int daysOnMarket;
	@JSONField(jsonFieldName="ConstructionMaterials", isEnrichment=true)
	private String contructionMaterial;
	@JSONField(jsonFieldName="YearBuilt", isEnrichment=true)
	private Double yearBuilt;
	@JSONField(jsonFieldName="Flooring", isEnrichment=true)
	private String flooring;
	@JSONField(jsonFieldName="Heating", isEnrichment=true)
	private String heating;
	@JSONField(jsonFieldName="ListingTerms", isEnrichment=true)
	private String listingTerm;
	@JSONField(jsonFieldName="TaxAnnualAmount", isEnrichment=true)
	private Double tax;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getSquareFootage() {
		return squareFootage;
	}
	public void setSquareFootage(Double squareFootage) {
		this.squareFootage = squareFootage;
	}
	public Double getBeds() {
		return beds;
	}
	public void setBeds(Double beds) {
		this.beds = beds;
	}
	public Double getBaths() {
		return baths;
	}
	public void setBaths(Double baths) {
		this.baths = baths;
	}
	public String getOpenHouse() {
		return openHouse;
	}
	public void setOpenHouse(String openHouse) {
		this.openHouse = openHouse;
	}
	public String getStandardStatus() {
		return standardStatus;
	}
	public void setStandardStatus(String standardStatus) {
		this.standardStatus = standardStatus;
	}
	public int getDaysOnMarket() {
		return daysOnMarket;
	}
	public void setDaysOnMarket(int daysOnMarket) {
		this.daysOnMarket = daysOnMarket;
	}
	public String getContructionMaterial() {
		return contructionMaterial;
	}
	public void setContructionMaterial(String contructionMaterial) {
		this.contructionMaterial = contructionMaterial;
	}
	public Double getYearBuilt() {
		return yearBuilt;
	}
	public void setYearBuilt(Double yearBuilt) {
		this.yearBuilt = yearBuilt;
	}
	public String getFlooring() {
		return flooring;
	}
	public void setFlooring(String flooring) {
		this.flooring = flooring;
	}
	public String getHeating() {
		return heating;
	}
	public void setHeating(String heating) {
		this.heating = heating;
	}
	public String getListingTerm() {
		return listingTerm;
	}
	public void setListingTerm(String listingTerm) {
		this.listingTerm = listingTerm;
	}
	public Double getTax() {
		return tax;
	}
	public void setTax(Double tax) {
		this.tax = tax;
	}
	@Override
	public String toString() {
		return "Property [id=" + id + ", address=" + address + ", price=" + price + ", squareFootage=" + squareFootage
				+ ", beds=" + beds + ", baths=" + baths + ", openHouse=" + openHouse + ", standardStatus="
				+ standardStatus + ", daysOnMarket=" + daysOnMarket + ", contructionMaterial=" + contructionMaterial
				+ ", yearBuilt=" + yearBuilt + ", flooring=" + flooring + ", heating=" + heating + ", listingTerm="
				+ listingTerm + ", tax=" + tax + "]";
	}
	
}
