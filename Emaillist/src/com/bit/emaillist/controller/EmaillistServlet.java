package com.bit.emaillist.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bit.emaillist.dao.EmaillistDao;
import com.bit.emaillist.dao.EmaillistDaoImpl;
import com.bit.emaillist.vo.EmailVO;

// @WebServlet 어노테이션을 이용한 서블릿 매핑
//@Webservlet("/el") -> urlpattern만 필요할 경우 
@WebServlet(name = "EmaillistServlet", urlPatterns = "/el")
public class EmaillistServlet extends BaseServlet {
	// Model 2에서의 servlet은 controller의 역할 수행
	// model 1의 jsp가 요청 처리, 로직 처리를 모두 담당했던 역할을
	// 대신 수행
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// action parameter를 이용한 조건 분기
		String action = req.getParameter("a");
		//action이 null일수 있기에 form 이 앞쪽에 나온다
		if ("form".equals(action)) {
			// a= form 일 경우 
			RequestDispatcher rd = getServletContext()
								.getRequestDispatcher("/WEB-INF/views/emaillist/form.jsp");
			rd.forward(req, resp);
			
		}else {
			//list 페이지 처리
			EmaillistDao dao = new EmaillistDaoImpl(dbuser, dbpass);
			List<EmailVO> list = dao.getList();
			// 사용자로부터 받은 요청 객체 + 로직을 수행하고 난 후의 model을 싣고
			// jsp로 제어권을 넘긴다
			req.setAttribute("list", list);  //"list"에 list 싣고 전달
			RequestDispatcher rd = getServletContext().getRequestDispatcher
					("/WEB-INF/views/emaillist/index.jsp"); //요청전달객체
			// 요청과 응답의 제어권을 jsp로 전달
			rd.forward(req, resp);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getParameter("a");
		if("insert".equals(action)) {
			// a hidden field가 insert일 경우의 처리
			String lastName = req.getParameter("last_name");
			String firstName = req.getParameter("first_name");
			String email = req.getParameter("email");
			
			EmailVO vo = new EmailVO(lastName,
									firstName,
									email);
			EmaillistDao dao = new EmaillistDaoImpl(dbuser,dbpass);
			
			boolean isSuccess = dao.insert(vo);
			if (isSuccess) {
				System.out.println("INSERT SUCCESS");
			} else {
				System.err.println("INSERT FAILED");
			}
			resp.sendRedirect(req.getContextPath() + "/el");
		} else if ("delete".equals(action)) {
			// a hidden 필드가 delete일 경우의 처리
			String no = req.getParameter("no");
			EmaillistDao dao = new EmaillistDaoImpl(dbuser, dbpass);
			boolean isSuccess = dao.delete(Long.valueOf(no));
			if(isSuccess) {
				System.out.println("DELETE SUCCESS!");
			} else {
				System.out.println("DELETE FAILED!");
			}
			resp.sendRedirect(req.getContextPath() + "/el");
		} else {
			resp.sendError(405);
		}
		
	}
	
	

}








