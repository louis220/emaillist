package com.bit.emaillist.dao;

import java.util.List;

import com.bit.emaillist.vo.EmailVO;

public interface EmaillistDao {

		public List<EmailVO> getList();
		public boolean insert(EmailVO vo);
		public boolean delete(Long no);
	

}
