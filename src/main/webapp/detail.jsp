<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="dao.Wifidao" %>
<%@ page import="dao.BookmarkGroupDao" %>
<%@ page import="dto.*" %>
<%@ page import="java.util.List" %>
<%
String mgrNo = request.getParameter("mgrNo");
Wifidto wifi = null;

if (mgrNo != null && !mgrNo.isEmpty()) {
    Wifidao dao = new Wifidao();
    wifi = dao.getWifiByMgrNo(mgrNo);
}

// 북마크 그룹 목록 가져오기
BookmarkGroupDao groupDao = new BookmarkGroupDao();
List<BookmarkGroupDto> groups = groupDao.getAllBookmarkGroups();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>와이파이 상세 정보</title>
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
    <h2>와이파이 상세 정보</h2>

    <% if (wifi != null) { %>
        <form action="bookmark-detail.jsp" method="post">
            <label for="group">북마크 그룹 선택:</label>
            <select id="group" name="groupId">
                <% for (BookmarkGroupDto group : groups) { %>
                    <option value="<%= group.getId() %>"><%= group.getName() %></option>
                <% } %>
            </select>

            <input type="hidden" name="wifiMgrNo" value="<%= wifi.getMgrNo() %>" />
            <button type="submit">추가하기</button>
        </form>

        <table>
            <tr><th>관리번호</th><td><%= wifi.getMgrNo() %></td></tr>
            <tr><th>와이파이명</th><td><%= wifi.getName() %></td></tr>
            <tr><th>주소</th><td><%= wifi.getAddress() %></td></tr>
            <tr><th>상세주소</th><td><%= wifi.getDetailAddress() %></td></tr>
            <tr><th>설치위치</th><td><%= wifi.getFloor() %></td></tr>
            <tr><th>설치유형</th><td><%= wifi.getType() %></td></tr>
            <tr><th>서비스구분</th><td><%= wifi.getService() %></td></tr>
            <tr><th>설치년도</th><td><%= wifi.getInstallYear() %></td></tr>
            <tr><th>설치기관</th><td><%= wifi.getOrganization() %></td></tr>
            <tr><th>위도</th><td><%= wifi.getLat() %></td></tr>
            <tr><th>경도</th><td><%= wifi.getLnt() %></td></tr>
        </table>
    <% } else { %>
        <p>해당 와이파이 정보를 찾을 수 없습니다.</p>
    <% } %>

    <div style="margin-top: 20px;">
        <a href="index.jsp">홈으로</a>
        <a href="javascript:history.back()" class="back-btn">목록으로</a>
    </div>
</body>
</html>
