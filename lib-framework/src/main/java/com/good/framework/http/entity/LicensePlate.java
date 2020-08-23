/**
 *
 */
package com.good.framework.http.entity;

import java.io.Serializable;
import java.util.List;

import com.good.framework.commons.StringConverter;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Transient;

/**
 * @author hux
 * @version 1.0.0
 * @createTime 2020年8月23日 上午2:28:03
 * @updateTime 2020年8月23日
 * @updateContext
 * 		version:1.0.0
 *      1:初始创建
 */
@Entity
public class LicensePlate implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = -1752061299205533146L;

	@Id(autoincrement = true)
	private Long dbkey;

	@Property
	private int id;

	@Property
	private String abbreviation;

	@SerializedName("alphabetics")
	@Convert(converter = StringConverter.class, columnType = String.class)
	private List<String> alphabeticList;

	@Generated(hash = 233237200)
	public LicensePlate(Long dbkey, int id, String abbreviation,
			List<String> alphabeticList) {
		this.dbkey = dbkey;
		this.id = id;
		this.abbreviation = abbreviation;
		this.alphabeticList = alphabeticList;
	}

	@Generated(hash = 1647398507)
	public LicensePlate() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public List<String> getAlphabeticList() {
		return alphabeticList;
	}

	public void setAlphabeticList(List<String> alphabeticList) {
		this.alphabeticList = alphabeticList;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Long getDbkey() {
		return this.dbkey;
	}

	public void setDbkey(Long dbkey) {
		this.dbkey = dbkey;
	}

	public class Abbreviation{

		private int id;

		private String abbreviation;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getAbbreviation() {
			return abbreviation;
		}

		public void setAbbreviation(String abbreviation) {
			this.abbreviation = abbreviation;
		}
	}
}


