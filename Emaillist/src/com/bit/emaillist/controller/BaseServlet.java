package com.bit.emaillist.controller;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
//초기화 작업을 수행할 기반 추상 클래스
public class BaseServlet extends HttpServlet {
	protected String dbuser = null;
	protected String dbpass = null;
	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config); //절대 지우지 맙시다
		
		// 서블릿 초기화 파라미터 불러오기
		ServletContext context = getServletContext();
		dbuser = context.getInitParameter("dbuser");
		dbpass = context.getInitParameter("dbpass");
	}

}
