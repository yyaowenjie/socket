package com.sq.drogon.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;

/**
 * 实时测点表
 * 
 * @User yaowenjie
 * @Date 2016-06-16
 * @Time 10:36
 */
@Entity
@Table(name = "t_originaldata")
public class OriginalData implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** 指标的CODE */
	private String itemCode;

	/** 指标的同步值 */
	private String itemValue;

	/** 获取指标实例的时间点 */
	private Calendar instanceTime;

	/** 数据获取批次号 */
	private Long batchNum;

	/** 系统编号 */
	private int sysId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemValue() {
		return itemValue;
	}

	public void setItemValue(String itemValue) {
		this.itemValue = itemValue;
	}

	public Calendar getInstanceTime() {
		return instanceTime;
	}

	public void setInstanceTime(Calendar instanceTime) {
		this.instanceTime = instanceTime;
	}

	public Long getBatchNum() {
		return batchNum;
	}

	public void setBatchNum(Long batchNum) {
		this.batchNum = batchNum;
	}

	public int getSysId() {
		return sysId;
	}

	public void setSysId(int sysId) {
		this.sysId = sysId;
	}

	@Override
	public String toString() {
		return "OriginalData{" + "sysId=" + sysId + ", itemCode='" + itemCode
				+ '\'' + ", itemValue='" + itemValue + '\'' + ", batchNum="
				+ batchNum + '}';
	}
}
