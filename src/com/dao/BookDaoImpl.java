package com.dao;

import com.model.Book;
import com.model.PageResult;
import com.utils.C3P0Utils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class BookDaoImpl {

	public List<Book> findAllBooks() throws SQLException{
		//1.创建queryrunner
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		return qr.query("select * from books", new BeanListHandler<Book>(Book.class));
	}
	
	public void addBook(Book book)throws SQLException{
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		
		String sql = "insert into books (id,name,price,pnum,category,description) values (?,?,?,?,?,?)";
		
		//定义一个数组参数
		Object[] params = new Object[6];
		//id必须是惟一，UUID-字符串根据当前电脑或者应用的一些数据生成一个惟一字符串
		params[0] = UUID.randomUUID().toString();
		params[1] = book.getName();
		params[2] = book.getPrice();
		params[3] = book.getPnum();
		params[4] = book.getCategory();
		params[5] = book.getDescription();
		qr.update(sql,params);
		
		//qr.update(sql,UUID.randomUUID().toString(), book.getName(),book.getPrice(),book.getPnum(),book.getCategory(),book.getDescription());
	}
	
	/**
	 * 通过id找到书商品
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public Book findBookById(String id) throws SQLException{
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		
		String sql = "select * from books where id=?";
		
		return qr.query(sql, new BeanHandler<Book>(Book.class),id);
		
	}
	
	/**
	 * 更新书的数据
	 * @throws SQLException 
	 */
	public void updateBook(Book book) throws SQLException{
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		
		String sql = "update books set name = ?, price = ? , pnum = ? , category = ? , description = ? where id = ?";
		
		
		
		qr.update(sql, book.getName(),book.getPrice(),book.getPnum(),book.getCategory(),book.getDescription(),book.getId());
	}

	public void deleteBookById(String id) throws SQLException {
		// TODO Auto-generated method stub
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		
		String sql = "delete from books where id = ?";
		
		qr.update(sql,id); 
	}
	
	/**
	 * 使用批处理删除
	 * @param ids
	 * @throws SQLException
	 */
	public void deleteBookByIds(String ids) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		
		//1.拆分ids = 1001,1002,10003
		String[] idArr = ids.split(",");
		
		
		//2.写个sql 
		String sql = "delete from books where id = ?";
	
		//3.封装参数
		//假有3个id
		/**
		 * [[1001,aa],[1002,bb],[1003,cc]]
		 * [[1001],[1002],[1003]]
		 * [1001,1002,1003]
		 */
		Object[][] params = new Object[idArr.length][];
		for(int i = 0;i<idArr.length;i++){
			params[i] = new Object[]{idArr[i]};
		}
		
		//4.执行批处理
		qr.batch(sql, params);
	}
	
	/**
	 * 这种方式执行多次sql语句
	 */
	public void deleteBookByIds1(String ids) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		
		//1.拆分ids = 1001,1002,10003
		String[] idArr = ids.split(",");
		
		/**
		 * 这种方式执行多次sql语句
		 */
		//2.写个sql 
		String sql = "delete from books where id = ?";
		for(String id : idArr){
			qr.update(sql,id);
		}
	}

	public List<Book> findBook(String id, String name, String category, String minprice, String maxprice) throws SQLException {
		
		//查询
		//1.拼接sql语句
		String sql = "select * from books where 1=1";
		
		if(!"".equals(id)){
			sql += " and id = '" + id +"'";
		}
		
		if(!"".equals(name)){
			sql += " and name like '%" + name +"%'";
		}
		
		if(!"".equals(category)){
			sql += " and category = '" + category +"'";
		}
		
		
		//价格
		if(!"".equals(minprice)){
			sql += " and price >= " + minprice;
		}
		
		if(!"".equals(maxprice)){
			sql += " and price <= " + maxprice;
		}
		
		System.out.println(sql);
		
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		
		return qr.query(sql, new BeanListHandler<Book>(Book.class));
	}
	
	
	/**
	 * 根据当前页返回数据
	 * @param page 查询的页码
	 * @return
	 * @throws SQLException 
	 */
	public PageResult<Book> findBooksByPage(int page) throws SQLException{
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		
		//1.创建PageResult对象
		PageResult<Book> pr = new PageResult<Book>();
		
		//2.设置totalCount【总记录数】
		Long totalCount = (Long) qr.query("select count(*) from books", new ScalarHandler());
		pr.setTotalCount(totalCount);
		
		//3.设置总页数 10/5
		int totalPage = (int)(totalCount % pr.getPageCount() == 0 ? totalCount / pr.getPageCount() : totalCount / pr.getPageCount() + 1);
		pr.setTotalPage(totalPage);
		
		//4.设置 当前页
		pr.setCurrentPage(page);
		
		//5.设置pageresult里的list数据【库存排序】
		String sql = "select * from books order by pnum limit ?,?";
		int start = (page - 1) * pr.getPageCount();
		List<Book> books = qr.query(sql, new BeanListHandler<Book>(Book.class),start,pr.getPageCount());
		pr.setList(books);
		
		return pr;
	}
}
