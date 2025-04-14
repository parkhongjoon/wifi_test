<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="dao.BookmarkGroupDao, dto.BookmarkGroupDto" %>
<%
    request.setCharacterEncoding("UTF-8");  // 요청 파라미터 인코딩 설정

    String idStr = request.getParameter("id");
    int id = Integer.parseInt(idStr);

    BookmarkGroupDao dao = new BookmarkGroupDao();
    BookmarkGroupDto group = dao.getBookmarkGroupById(id);

    String name = request.getParameter("name");
    String sortOrderStr = request.getParameter("sortOrder");
    boolean success = false;

    if (name != null && sortOrderStr != null) {
        int sortOrder = Integer.parseInt(sortOrderStr);
        dao.updateBookmarkGroup(id, name, sortOrder); // 수정된 값으로 업데이트
        success = true;
        group = dao.getBookmarkGroupById(id); // 업데이트된 값을 다시 가져오기
    }
%>

<html>
<head>
    <title>북마크 그룹 수정</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f9;
            margin: 0;
            padding: 20px;
        }

        h1 {
            text-align: center;
            color: #333;
        }

        .form-container {
            background-color: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            width: 400px;
            margin: 0 auto;
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

    <h1>북마크 그룹 수정</h1>

    <% if (success) { %>
        <div class="success-message">
            <strong>북마크 그룹이 성공적으로 수정되었습니다!</strong>
        </div>

    <% } else { %>
        <div class="form-container">
            <!-- 수정 폼 -->
            <form action="bookmark-group-edit.jsp" method="post">
                <input type="hidden" name="id" value="<%= group.getId() %>">

                <label for="name">그룹 이름:</label>
                <input type="text" id="name" name="name" value="<%= group.getName() %>" required>

                <label for="sortOrder">순서:</label>
                <input type="number" id="sortOrder" name="sortOrder" value="<%= group.getSortOrder() %>" required>

                <input type="submit" value="수정 완료">
            </form>
        </div>
    <% } %>

    <div class="link-container">
        <a href="index.jsp">홈으로</a>
		<a href="bookmark-group-list.jsp">목록으로</a>
    </div>

</body>
</html>
