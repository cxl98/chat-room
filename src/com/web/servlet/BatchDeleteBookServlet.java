package com.web.servlet;
import com.service.BookServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/BatchDeleteBookServlet")
public class BatchDeleteBookServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	
		//1.获取请求参数
		String ids = request.getParameter("ids");
		
		//2.调用service
		BookServiceImpl bookService = new BookServiceImpl();
		bookService.deleteBookByIds(ids);
		
		//3.回到 list.jsp页面
		request.setAttribute("books", bookService.findAllBooks());
		request.getRequestDispatcher("/admin/products/list.jsp").forward(request, response);
	}
}
