package com.web.servlet;


import com.model.Book;
import com.service.BookServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/FindBookByIdServlet")
public class FindBookByIdServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1.获取id
		String id = request.getParameter("id");
		
		//2.查找书
		BookServiceImpl bookService= new BookServiceImpl();
		Book book = bookService.findBookById(id);
		
		//3.放在请求对象
		request.setAttribute("book", book);
		request.getRequestDispatcher("/admin/products/edit.jsp").forward(request, response);
	}
}
