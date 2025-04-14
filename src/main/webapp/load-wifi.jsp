<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="javax.servlet.RequestDispatcher" %>
<%@ page import="java.util.*" %>
<%@ page import="dto.*" %>
<%@ page import="service.WifiApiService" %>

<%
	WifiApiService service = new WifiApiService();
	
	if ("POST".equalsIgnoreCase(request.getMethod())) {
	    service.getAllWifiData();
	    int totalCount = service.getTotalCount(); // 전체 개수 가져오기
	
	    // 메시지를 세션에 저장
	    session.setAttribute("message", "전체 와이파이 데이터를 가져와 저장했습니다. (" + totalCount + "개)");
	
	    // index.jsp로 리다이렉트
	    response.sendRedirect("index.jsp");
	    return;
	}

    try {
        double userLat = Double.parseDouble(request.getParameter("lat"));
        double userLnt = Double.parseDouble(request.getParameter("lnt"));

        List<Wifidto> wifiList = service.requestWifiData(userLat, userLnt);

        request.setAttribute("wifiList", wifiList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        dispatcher.forward(request, response);
    } catch (Exception e) {
%>
        <p style="color: red;">잘못된 위도/경도 입력입니다.</p>
<%
    }
%>
