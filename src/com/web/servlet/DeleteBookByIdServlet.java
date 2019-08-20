package com.web.servlet;

import com.service.BookServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/DeleteBookByIdServlet")
public class DeleteBookByIdServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1.获取id
		String id = request.getParameter("id");
		
		//2.查找书
		BookServiceImpl bookService= new BookServiceImpl();
		bookService.deleteBookById(id);
		
		//3.加到list.jsp
		request.setAttribute("books", bookService.findAllBooks());
		request.getRequestDispatcher("/admin/products/list.jsp").forward(request, response);
	}
}
