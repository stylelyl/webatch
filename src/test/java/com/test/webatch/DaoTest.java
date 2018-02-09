package com.test.webatch;

import java.util.List;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.test.webatch.dao.Page;
import com.test.webatch.dao.TmCardInfoDao;
import com.test.webatch.domain.TmCardInfo;

@ContextConfiguration(locations = { "/launch-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class DaoTest {

	@Autowired
	private TmCardInfoDao<TmCardInfo> tmCardInfoDao;

	public void testTmCardInfoDao() {
		Page<TmCardInfo> page = new Page<TmCardInfo>();
		page.setPageNumber(5);
		page.setPageSize(10);
		// TmCardInfoDao tmCardInfoDao = (TmCardInfoDao)
		// context.getBean("tmCardInfoDao");
		tmCardInfoDao.queryForPage(TmCardInfo.class.getCanonicalName(), null, page);
		List<TmCardInfo> list = page.getRows();
		for (TmCardInfo c : list) {
			System.out.println(c);
		}
	}
}
