package com.test.webatch.domain;

import java.math.BigDecimal;
import java.util.Date;

public class TmCardInfo {
	private static final long serialVersionUID = 1L;

	public TmCardInfo() {
		super();
	}

	private Integer id;

	private String productName;

	private BigDecimal currBal;

	private Integer cardStatus;

	private Date createDate;

	private Date updateDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public BigDecimal getCurrBal() {
		return currBal;
	}

	public void setCurrBal(BigDecimal currBal) {
		this.currBal = currBal;
	}

	public Integer getCardStatus() {
		return cardStatus;
	}

	public void setCardStatus(Integer cardStatus) {
		this.cardStatus = cardStatus;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Override
	public String toString() {
		return String
				.format("TmCardInfo [id=%s, productName=%s, currBal=%s, cardStatus=%s, createDate=%s, updateDate=%s]",
						id, productName, currBal, cardStatus, createDate,
						updateDate);
	}

}
