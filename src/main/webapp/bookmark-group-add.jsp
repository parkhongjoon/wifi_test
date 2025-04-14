<%@ page import="dao.BookmarkGroupDao" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%
    request.setCharacterEncoding("UTF-8");

    String name = request.getParameter("name");
    String orderStr = request.getParameter("order");

    boolean submitted = name != null && orderStr != null;
    boolean success = false;
    boolean duplicate = false;

    if (submitted) {
        int order = Integer.parseInt(orderStr);

        BookmarkGroupDao dao = new BookmarkGroupDao();
        success = dao.insertBookmarkGroup(name, order);
        duplicate = !success;
    }
%>
<html>
<head>
    <title>북마크 그룹 추가</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f9;
            margin: 0;
            padding: 20px;
        }

        h2 {
            text-align: center;
            color: #333;
        }

        .form-container {
            background-color: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            width: 400px;
            margin: 20px auto;
        }

        label {
            font-size: 14px;
            color: #555;
            margin-bottom: 8px;
            display: block;
        }

        input[type="text"], input[type="number"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 14px;
        }

        input[type="submit"] {
            width: 100%;
            padding: 10px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        input[type="submit"]:hover {
            background-color: #45a049;
        }

        .link-container {
            text-align: center;
            margin-top: 20px;
        }

        .link-container a {
            text-decoration: none;
            color: #007BFF;
            margin: 0 10px;
            font-size: 14px;
        }

        .link-container a:hover {
            text-decoration: underline;
        }

        .success-message {
            background-color: #dff0d8;
            color: #3c763d;
            padding: 15px;
            border-radius: 5px;
            text-align: center;
            font-size: 16px;
            margin: 20px auto;
            width: 400px;
        }
    </style>
</head>
<body>
    <h2>북마크 그룹 추가</h2>

    <% if (!submitted) { %>
        <div class="form-container">
            <form method="post">
                <label for="name">북마크 이름:</label>
                <input type="text" id="name" name="name" required><br><br>

                <label for="order">순서:</label>
                <input type="number" id="order" name="order" required><br><br>

                <input type="submit" value="추가">
            </form>
        </div>
        
        <div class="link-container">
            <a href="index.jsp">홈으로</a>
            <a href="bookmark-group-list.jsp">목록으로</a>
        </div>
        
  <% } else if (success) { %>
    <div class="success-message">
        <strong>북마크 그룹이 성공적으로 추가되었습니다!</strong>
    </div>
    <div class="link-container">
        <a href="index.jsp">홈으로</a>
        <a href="bookmark-group-list.jsp">목록으로</a>
    </div>
<% } else if (duplicate) { %>
    <div class="success-message" style="background-color: #f8d7da; color: #721c24;">
        <strong>중복된 이름입니다. 다른 이름을 입력해주세요.</strong>
    </div>
    <div class="form-container">
        <form method="post">
            <label for="name">북마크 이름:</label>
            <input type="text" id="name" name="name" value="<%= name %>" required><br><br>

            <label for="order">순서:</label>
            <input type="number" id="order" name="order" value="<%= orderStr %>" required><br><br>

            <input type="submit" value="추가">
        </form>
    </div>
    <div class="link-container">
        <a href="index.jsp">홈으로</a>
        <a href="bookmark-group-list.jsp">목록으로</a>
    </div>
<% } %>
</body>
</html>
