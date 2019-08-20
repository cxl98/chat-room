package com.test;

import com.model.Book;
import com.service.BookServiceImpl;
import org.junit.Test;

import java.util.List;

public class Demo {

	@Test
	public void test1(){
		BookServiceImpl bookService = new BookServiceImpl();
		List<Book> list = bookService.findAllBooks();
		System.out.println(list);
//		//遍历结果
//		for(Book book : list.getList()){
//			System.out.println(book);
//		}
	}
}
