package com.sq.drogon.domain;

import org.hibernate.validator.constraints.NotBlank;
import javax.persistence.*;


/**
 * Դ����
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
	 * Դcode��DCS����ECSϵͳ�ϲ��ı���
	 */
	@NotBlank
	private String sourceCode;

	/**
	 * Ŀ��code, �ӿڶԽӷ�����
	 */
	@NotBlank
	private String targetCode;

	/**
	 * �������
	 */
	@NotBlank
	private String pointName;

	/**
	 * ���������ͣ�Ĭ��1����Ҫ����
	 */
	private int meaType = 1;

	/**
	 * ������ʽ
	 */
	private String calculateExp;

	/**
	 * ϵͳ��ţ���ʾopc����ı�ʾ
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
