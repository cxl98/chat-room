<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<HTML>
<HEAD>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
	function addProduct() {
		//跳转页面
		window.location.href = "${pageContext.request.contextPath}/admin/products/add.jsp";
	}
	
	//删除书js代码
	function deleteBook(bookName,bookId) {
		//确认提示框
		if(confirm('是否要删除【' + bookName + '】 这本书?')){
			//确定，要删除
			location.href = '${pageContext.request.contextPath}/DeleteBookByIdServlet?id=' + bookId;
		}else{
			//不删除
		}
	}
	
	//批量删除
	function batchDelete(){
		//BatchDeleteBookSevlet?ids=1001,1002,1003
		//BatchDeleteBookSevlet?ids=1001-1002-1003【如果UUID有杠，就不能用这种方式】
		//1.拼接ids
		//遍历全选下面的复选框
		var ckBookIds = document.getElementsByName('ckBookId');
		var ids = '';
		for(var i=0;i<ckBookIds.length;i++){
			if(ckBookIds[i].checked == true){
				ids += ckBookIds[i].value + ',';
			}
			
		}
		
		//2.去除最后一个逗号
		ids = ids.substring(0,ids.length - 1);
		alert(ids);
		
		//3.访问servlet
		location.href = '${pageContext.request.contextPath}/BatchDeleteBookServlet?ids=' + ids;
	}
	
	function checkAll() {
		//1.获取全选的标签
		var ckAllTag = document.getElementById('ckAll');
		
		
		//状态
		var checkState = ckAllTag.checked;
		
		//2.遍历全选下面的复选框
		var ckBookIds = document.getElementsByName('ckBookId');
		
		//alert(ckBookIds.length);
		for(var i=0;i<ckBookIds.length;i++){
			ckBookIds[i].checked = checkState;
		} 
	}
</script>
</HEAD>
<body>
	<br>
	<form id="Form1" name="Form1"
		action="${pageContext.request.contextPath}/search" method="post">
		<table cellSpacing="1" cellPadding="0" width="100%" align="center"
			bgColor="#f5fafe" border="0">
			<TBODY>
				<tr>
					<td class="ta_01" align="center"><strong>查询条件</strong>
					</td>
				</tr>
				<tr>
					<td>
						<table cellpadding="0" cellspacing="0" border="0" width="100%">
							<tr>
								<td height="22" align="center" class="ta_01">
									商品编号</td>
								<td class="ta_01"><input type="text"
									name="id" size="15" value="" id="Form1_userName" class="bg" />
								</td>
								<td height="22" align="center" class="ta_01">
									类别：</td>
								<td class="ta_01" bgColor="#ffffff"><select name="category"
									id="category">
										<option value="" selected="selected">--选择商品类加--</option>
										<option value="文学">文学</option>
										<option value="生活">生活</option>
										<option value="计算机">计算机</option>
										<option value="外语">外语</option>
										<option value="经营">经营</option>
										<option value="励志">励志</option>
										<option value="社科">社科</option>
										<option value="学术">学术</option>
										<option value="少儿">少儿</option>
										<option value="艺术">艺术</option>

								</select></td>
							</tr>

							<tr>
								<td height="22" align="center" class="ta_01">商品名称：</td>
								<td class="ta_01" ><input type="text"
								name="name" size="15" value="" id="Form_userName" class="bg" />
								</td>
								<td height="22" align="center"  class="ta_01">
									价格区间(元)：</td>
								<td class="ta_01" ><input type="text"
									name="minprice" size="10" value="" />- <input type="text"
									name="maxprice" size="10" value="" /></td>
							</tr>

							<tr>
								<td width="100" height="22" align="center"
									class="ta_01"></td>
								<td class="ta_01"><font face="宋体"
									color="red"> &nbsp;</font>
								</td>
								<td align="right"class="ta_01"><br>
									<br></td>
								<td align="right" class="ta_01">
									<button type="submit" id="search" name="search"
										value="&#26597;&#35810;" class="button_view">
										&#26597;&#35810;</button> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input
									type="reset" name="reset" value="&#37325;&#32622;"
									class="button_view" />
								</td>
							</tr>
						</table>
					</td>

				</tr>
				<tr>
					<td class="ta_01" align="center"><strong>商品列表</strong>
					</TD>
				</tr>
				<tr>
					<td class="ta_01" align="right">
						<button type="button" id="add1" name="add" value="&#28155;&#21152;"
							class="button_add" onclick="batchDelete()">批量删除
						</button>
						<button type="button" id="add" name="add" value="&#28155;&#21152;"
							class="button_add" onclick="addProduct()">添加
						</button>
					</td>
				</tr>
				<tr>
					<td class="ta_01" align="center" >
						<table cellspacing="0" cellpadding="1" rules="all"
							bordercolor="gray" border="1" id="DataGrid1"
							style="BORDER-RIGHT: gray 1px solid; BORDER-TOP: gray 1px solid; BORDER-LEFT: gray 1px solid; WIDTH: 100%; WORD-BREAK: break-all; BORDER-BOTTOM: gray 1px solid; BORDER-COLLAPSE: collapse; BACKGROUND-COLOR: #f5fafe; WORD-WRAP: break-word">
							<tr
								style="FONT-WEIGHT: bold; FONT-SIZE: 12pt; HEIGHT: 25px; BACKGROUND-COLOR: #afd1f3">
								<td align="center" width="5%">
									<input type="checkbox" onclick="checkAll()" id='ckAll'>全选/全不选
								</td>
								<td align="center" width="24%">商品编号</td>
								<td align="center" width="18%">商品名称</td>
								<td align="center" width="9%">商品价格</td>
								<td align="center" width="9%">商品数量</td>
								<td width="8%" align="center">商品类别</td>
								<td width="8%" align="center">编辑</td>

								<td width="8%" align="center">删除</td>
							</tr>
								<c:forEach items="${pageResult.list}" var="book">
									<tr onmouseover="this.style.backgroundColor = 'white'"
										onmouseout="this.style.backgroundColor = '#F5FAFE';">
										<td align="center" width="5%">
											<input type="checkbox" name='ckBookId' value="${book.id}">
										</td>
										<td style="CURSOR: hand; HEIGHT: 22px" align="center"
											width="23">${book.id}</td>
										<td style="CURSOR: hand; HEIGHT: 22px" align="center"
											width="18%">${book.name}</td>
										<td style="CURSOR: hand; HEIGHT: 22px" align="center"
											width="8%">${book.price}</td>
										<td style="CURSOR: hand; HEIGHT: 22px" align="center"
											width="8%">${book.pnum}</td>
										<td style="CURSOR: hand; HEIGHT: 22px" align="center">${book.category}</td>
										<td align="center" style="HEIGHT: 22px" width="7%">
										<a href="${pageContext.request.contextPath}/FindBookByIdServlet?id=${book.id}">
												<img
												src="${pageContext.request.contextPath}/admin/images/i_edit.gif"
												border="0" style="CURSOR: hand"> </a>
										</td>
	
										<td align="center" style="HEIGHT: 22px" width="7%">
										<a href="javascript:deleteBook('${book.name}','${book.id}')">
												<img
												src="${pageContext.request.contextPath}/admin/images/i_del.gif"
												width="16" height="16" border="0" style="CURSOR: hand">
										</a>
										</td>
									</tr>
								</c:forEach>
							
								<tr>
									<td colspan="8" align="right">
										当前页&nbsp; ${pageResult.currentPage}/${pageResult.totalPage} &nbsp;总条数${pageResult.totalCount}&nbsp;&nbsp;&nbsp;
									</td>
								</tr>
								<tr>
									<td colspan="8" align="center">
										<c:if test="${pageResult.currentPage - 1 != 0}">
											<a href="${pageContext.request.contextPath}/BookListServlet?page=${pageResult.currentPage - 1}">上一页</a>
										</c:if>

										<c:forEach begin="1" end="${pageResult.totalPage}" var="p">
											<a href="${pageContext.request.contextPath}/BookListServlet?page=${p}">${p}</a>
											&nbsp;
										</c:forEach>
										
										<c:if test="${pageResult.currentPage + 1 <= pageResult.totalPage}">
											<a href="${pageContext.request.contextPath}/BookListServlet?page=${pageResult.currentPage + 1}">下一页</a>
										</c:if>
									</td>
								</tr>
						</table>
					</td>
				</tr>
			</TBODY>
		</table>
	</form>
</body>
</HTML>

