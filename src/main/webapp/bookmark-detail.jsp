<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="dao.*"%>
<%@ page import="dto.*"%>
<%@ page import="java.util.List"%>
<%
request.setCharacterEncoding("UTF-8");

String mgrNo = request.getParameter("wifiMgrNo");
String groupIdStr = request.getParameter("groupId");
String action = request.getParameter("action"); // 삭제 요청 여부

List<BookmarkGroupWifiDto> wifiList = null;
String groupName = "";

if (groupIdStr != null) {
	int groupId = Integer.parseInt(groupIdStr);
	BookmarkGroupWifiDao wifiDao = new BookmarkGroupWifiDao();
	BookmarkGroupDao groupDao = new BookmarkGroupDao();

	if ("delete".equals(action) && mgrNo != null) {
		// 삭제 처리
		wifiDao.removeWifiFromGroup(groupId, mgrNo);
	} else if (mgrNo != null) {
		// 추가 처리
		wifiDao.addWifiToGroup(groupId, mgrNo);
	}

	// 목록 조회
	wifiList = wifiDao.getBookmarkGroupWifis(groupId);

	// 그룹 이름 조회
	BookmarkGroupDto group = groupDao.getGroupById(groupId);
	if (group != null) {
		groupName = group.getName();
	}
}
%>


<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>북마크 그룹 - 와이파이 목록</title>
<style>
body {
	font-family: Arial, sans-serif;
	padding: 20px;
}

table {
	width: 100%;
	border-collapse: collapse;
	margin-top: 20px;
}

th, td {
	border: 1px solid #ccc;
	padding: 8px;
	text-align: center;
}

th {
	background-color: #f8f8f8;
}

a {
	text-decoration: none;
	color: blue;
}

.back-btn {
	margin-top: 20px;
	display: inline-block;
	padding: 8px 16px;
	background-color: #f0f0f0;
	border: 1px solid #ccc;
	border-radius: 4px;
}
</style>
</head>
<body>
	<h2><%=groupName%>에 추가된 와이파이 목록
	</h2>

	<table>
		<thead>
			<tr>
				<th>와이파이명</th>
				<th>주소</th>
				<th>등록일자</th>
				<th>비고</th>
			</tr>
		</thead>
		<tbody>
			<%
			if (wifiList != null && !wifiList.isEmpty()) {
				for (BookmarkGroupWifiDto wifi : wifiList) {
			%>
			<tr>
				<td><%=wifi.getWifiName()%></td>
				<td><%=wifi.getAddress()%></td>
				<td><%=wifi.getCreatedAt()%></td>
				<td><a
					href="bookmark-detail.jsp?groupId=<%=wifi.getGroupId()%>&wifiMgrNo=<%=wifi.getWifiMgrNo()%>&action=delete">삭제</a>
				</td>
			</tr>
			<%
			}
			} else {
			%>
			<tr>
				<td colspan="4">등록된 와이파이가 없습니다.</td>
			</tr>
			<%
			}
			%>
		</tbody>
	</table>

	<div>
		<a href="index.jsp" class="back-btn">홈으로</a>

		<%
		if (!"delete".equals(action)) {
		%>
		<a href="javascript:history.back()" class="back-btn">이전으로</a>
		<%
		}
		%>
	</div>

</body>
</html>
