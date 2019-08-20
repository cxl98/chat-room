package com.web.servlet;

import com.model.Book;
import com.service.BookServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/UpdateBookServlet")
public class UpdateBookServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		//设置请求的编码类型来解决post请求的乱码问题
		request.setCharacterEncoding("utf-8");
		
		//1.把表单的数据封装成模型
		Book book = new Book();
		try {
			BeanUtils.populate(book, request.getParameterMap());
			System.out.println(book);
			
			//2.更新数据
			BookServiceImpl bookService = new BookServiceImpl();
			bookService.updateBook(book);
			
			//3.回到list页面
			List<Book> books = bookService.findAllBooks();
			request.setAttribute("books", books);
			request.getRequestDispatcher("/admin/products/list.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}
}
