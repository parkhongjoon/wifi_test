<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="dto.Wifidto" %>
<%@ page import="service.*" %>
<%
    List<Wifidto> wifiList = (List<Wifidto>) request.getAttribute("wifiList");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>근처 와이파이 찾기</title>
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

    <form action="load-wifi.jsp" method="post">
        <button type="submit">전체 와이파이 데이터 불러오기</button>
    </form>

	<%
	    String message = (String) session.getAttribute("message");
	    if (message != null) {
	%>
	    <p style="color: green; font-weight: bold;"><%= message %></p>
	<%
	        session.removeAttribute("message"); 
	    }
	%>
    <h2>내 위치 입력</h2>
   
    <form action="load-wifi.jsp" method="get">
        위도: <input type="text" name="lat" required>
        경도: <input type="text" name="lnt" required>
        <button type="submit">근처 Wi-Fi 찾기</button>
    </form>

	<a href="index.jsp">홈으로</a>
	<a href="bookmark-group-add.jsp?">북마크 그룹생성</a>
	<a href="bookmark-group-list.jsp">북마크 그룹 보기</a>
	

    <h2>근처 공공 와이파이 리스트 (TOP 20)</h2>

    <table>
        <tr>
            <th>거리 (KM)</th>
            <th>관리번호</th>
            <th>와이파이명</th>
            <th>주소</th>
            <th>상세주소</th>
            <th>설치위치</th>
            <th>설치유형</th>
            <th>서비스구분</th>
            <th>설치년도</th>
            <th>설치기관</th>
            <th>위도</th>
            <th>경도</th>
        </tr>
        <%
            if (wifiList != null && !wifiList.isEmpty()) {
                for (Wifidto wifi : wifiList) {
        %>
            <tr>
                <td><%= String.format("%.4f", wifi.getDistance()) %></td>
                <td><%= wifi.getMgrNo() %></td>
				<td><a href="detail.jsp?mgrNo=<%= wifi.getMgrNo() %>"><%= wifi.getName() %></a></td>                
				<td><%= wifi.getAddress() %></td>
                <td><%= wifi.getDetailAddress() %></td>
                <td><%= wifi.getFloor() %></td>
                <td><%= wifi.getType() %></td>
                <td><%= wifi.getService() %></td>
                <td><%= wifi.getInstallYear() %></td>
                <td><%= wifi.getOrganization() %></td>
                <td><%= wifi.getLat() %></td>
                <td><%= wifi.getLnt() %></td>
            </tr>
        <%
                }
            } else {
        %>
            <tr><td colspan="12">와이파이 정보가 없습니다.</td></tr>
        <%
            }
        %>
    </table>

</body>
</html>
