package com.globalis.entities;

import java.util.List;

public class Permission {

	private String name;
	private List<TypeOfPermission> typesOfPermission;
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public List<TypeOfPermission> getTypesOfPermission() {
		return typesOfPermission;
	}

	public void setTypesOfPermission(List<TypeOfPermission> typesOfPermission) {
		this.typesOfPermission = typesOfPermission;
	}
	
	public Permission(){}
	
	public Permission(String name, List<TypeOfPermission> typesOfPermission){
		this.name = name;
		this.typesOfPermission = typesOfPermission;
	}


	
	
}
