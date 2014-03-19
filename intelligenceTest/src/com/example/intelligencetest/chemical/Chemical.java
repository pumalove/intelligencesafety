package com.example.intelligencetest.chemical;

public class Chemical {
	
	String chemicalId;
	String name;
	String type; //liquid - //gas
	String emergencyPhone;
	
	
	
	
	
	public Chemical() {
	}
	
	public String getChemicalId() {
		return chemicalId;
	}

	public void setChemicalId(String chemicalId) {
		this.chemicalId = chemicalId;
	}

	public String getName() {
		return name;
	}
	
	public String getType() {
		return type;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setType(String type) {
		this.type = type;
	}



	public String getEmergencyPhone() {
		return emergencyPhone;
	}

	public void setEmergencyPhone(String emergencyPhone) {
		this.emergencyPhone = emergencyPhone;
	}
	
}
