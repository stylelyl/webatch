package com.test.webatch.data;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.test.webatch.domain.TmCardInfo;

public class CardRowMapper implements RowMapper<TmCardInfo> {
	public static final String ID_COLUMN = "id";
	public static final String PRODUCT_NAME = "PRODUCT_NAME";
	public static final String CURR_BAL = "CURR_BAL";

	public TmCardInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		TmCardInfo info = new TmCardInfo();
		info.setId(rs.getInt(ID_COLUMN));
		info.setProductName(rs.getString(PRODUCT_NAME));
		info.setCurrBal(rs.getBigDecimal(CURR_BAL));
		info.setCardStatus(rs.getInt("CARD_STATUS"));
		info.setCreateDate(rs.getDate("CREATE_DATE"));
		info.setUpdateDate(rs.getDate("UPDATE_DATE"));
		return info;
	}
}
