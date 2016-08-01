package com.sq.drogon.domain;

import org.hibernate.validator.constraints.NotBlank;
import javax.persistence.*;


/**
 * 源测点表
 * 
 * @User yaowenjie
 * @Date 2016-06-16
 * @Time 10:36
 */
@Entity
@Table(name = "t_mesuringpoint")
public class MesuringPoint {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false, precision = 10)
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 源code，DCS或者ECS系统上测点的编码
	 */
	@NotBlank
	private String sourceCode;

	/**
	 * 目标code, 接口对接方编码
	 */
	@NotBlank
	private String targetCode;

	/**
	 * 测点名称
	 */
	@NotBlank
	private String pointName;

	/**
	 * 测点计算类型，默认1不需要计算
	 */
	private int meaType = 1;

	/**
	 * 计算表达式
	 */
	private String calculateExp;

	/**
	 * 系统编号，表示opc服务的标示
	 */
	private int sysId;

	public String getSourceCode() {
		return sourceCode;
	}

	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}

	public String getTargetCode() {
		return targetCode;
	}

	public void setTargetCode(String targetCode) {
		this.targetCode = targetCode;
	}

	public String getPointName() {
		return pointName;
	}

	public void setPointName(String pointName) {
		this.pointName = pointName;
	}

	public int getMeaType() {
		return meaType;
	}

	public void setMeaType(int meaType) {
		this.meaType = meaType;
	}

	public String getCalculateExp() {
		return calculateExp;
	}

	public void setCalculateExp(String calculateExp) {
		this.calculateExp = calculateExp;
	}

	public int getSysId() {
		return sysId;
	}

	public void setSysId(int sysId) {
		this.sysId = sysId;
	}

	public MesuringPoint() {
	}

	public MesuringPoint(Long id) {
		setId(id);
	}
}
