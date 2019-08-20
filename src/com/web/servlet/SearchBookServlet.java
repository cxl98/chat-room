package com.web.servlet;
import com.model.Book;
import com.service.BookServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/SearchBookServlet")
public class SearchBookServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		

		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String category = request.getParameter("category");
		String minprice = request.getParameter("minprice");
		String maxprice = request.getParameter("maxprice");
		

		BookServiceImpl bookService = new BookServiceImpl();
		List<Book> books = bookService.findBook(id,name,category,minprice,maxprice);


		request.setAttribute("books", books);
		System.out.println(books);
		request.getRequestDispatcher("/admin/products/list1.jsp").forward(request, response);
		
	}
}
