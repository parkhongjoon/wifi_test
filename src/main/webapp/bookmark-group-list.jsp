<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page
	import="java.util.*, dao.BookmarkGroupDao, dto.BookmarkGroupDto"%>
<%
BookmarkGroupDao dao = new BookmarkGroupDao();
List<BookmarkGroupDto> groupList = dao.getAllBookmarkGroups();

// 삭제 처리 부분
String deleteIdStr = request.getParameter("deleteId");
if (deleteIdStr != null) {
	int deleteId = Integer.parseInt(deleteIdStr);
	dao.deleteBookmarkGroup(deleteId); // 삭제 메소드 호출
	response.sendRedirect("bookmark-group-list.jsp"); // 삭제 후 목록 페이지로 리디렉션
	return; // 리디렉션 후 추가 실행 방지
}
%>
<html>
<head>
<title>북마크 그룹 목록</title>
<style>
body {
	font-family: Arial, sans-serif;
	padding: 20px;
}

h2 {
	margin-bottom: 10px;
}

form {
	margin-bottom: 20px;
}

input[type="text"] {
	margin-right: 10px;
	padding: 5px;
}

button {
	padding: 6px 12px;
}

table {
	width: 100%;
	border-collapse: collapse;
	margin-top: 16px;
}

th, td {
	border: 1px solid #ccc;
	padding: 8px;
	text-align: center;
}

th {
	background-color: #f8f8f8;
}
</style>
</head>
<body>
	<h1>북마크 그룹 목록</h1>
	<table border="1" cellpadding="10">
		<thead>
			<tr>
				<th>이름</th>
				<th>순서</th>
				<th>생성일</th>
				<th>수정일</th>
				<th>관리</th>
			</tr>
		</thead>
		<tbody>
			<%
			for (BookmarkGroupDto group : groupList) {
			%>
			<tr>
			    <td><a href="bookmark-detail.jsp?groupId=<%=group.getId()%>">
			        <%=group.getName()%>
			    </a></td>
			
			    <td><%=group.getSortOrder()%></td>  <!-- ✅ 수정된 부분 -->
			    <td><%=group.getCreatedAt()%></td>
			    <td><%=group.getUpdatedAt()%></td>
			    <td>
			        <a href="bookmark-group-edit.jsp?id=<%=group.getId()%>">수정</a> |
			        <a href="bookmark-group-list.jsp?deleteId=<%=group.getId()%>"
			           onclick="return confirm('정말 삭제하시겠습니까?');">삭제</a>
			    </td>
			</tr>

			<%
			}
			%>
		</tbody>
	</table>
	<br>
	<a href="bookmark-group-add.jsp">새 그룹 추가</a>
	<a href="index.jsp">홈으로</a>
</body>
</html>
