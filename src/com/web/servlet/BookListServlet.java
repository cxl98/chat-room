package com.web.servlet;


import com.model.Book;
import com.model.PageResult;
import com.service.BookServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/BookListServlet")
public class BookListServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//BookListServlet?page=3
		//1.获取页码
		String page = request.getParameter("page");
		//如果没有传值就显示第1页的数据
		if(page == null || "".equals(page)){
			page = "1";
		}
		
		
		
		//调用业务方法
		BookServiceImpl bookService = new BookServiceImpl();
		PageResult<Book> pageResult = bookService.findBooksByPage(Integer.parseInt(page));
		
		//2.把数据放在请求对象中
		request.setAttribute("pageResult", pageResult);

		System.out.println(pageResult);
		//3.进入admin/products/list.jsp
		request.getRequestDispatcher("/admin/products/list.jsp").forward(request, response);
		
	}

	

}
