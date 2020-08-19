package com.good.framework.http.entity;

import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class City implements Serializable {

	private static final long serialVersionUID = 7480703274636918019L;
	@Id(autoincrement = true)
	private Long dbkey;
	@Property
	private int id;

	@Property
	private int code;

	@Property
	private int type;

	@Property
	@SerializedName(value = "name",alternate = {"cityname"})
	private String name;

	@Property
	private int pcode;

	@Generated(hash = 1049825854)
	public City(Long dbkey, int id, int code, int type, String name, int pcode) {
		this.dbkey = dbkey;
		this.id = id;
		this.code = code;
		this.type = type;
		this.name = name;
		this.pcode = pcode;
	}
	@Generated(hash = 750791287)
	public City() {
	}
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPcode() {
		return pcode;
	}
	public void setPcode(int pcode) {
		this.pcode = pcode;
	}
	public Long getDbkey() {
		return this.dbkey;
	}
	public void setDbkey(Long dbkey) {
		this.dbkey = dbkey;
	}
    
    
	
}
