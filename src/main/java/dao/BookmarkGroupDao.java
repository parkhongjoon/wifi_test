package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import dto.BookmarkGroupDto;

public class BookmarkGroupDao {

	private final String DB_URL = "jdbc:mariadb://localhost:3306/test_db";
	private final String DB_USER = "root";
	private final String DB_PASS = "zerobase";

	// 생성
	public boolean insertBookmarkGroup(String name, int order) {
	    String sql = "INSERT INTO bookmark_group (name, sort_order, created_at, updated_at) VALUES (?, ?, NOW(), NOW())";

	    try {
	        Class.forName("org.mariadb.jdbc.Driver");

	        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
	             PreparedStatement pstmt = conn.prepareStatement(sql)) {

	            pstmt.setString(1, name);
	            pstmt.setInt(2, order);

	            pstmt.executeUpdate();
	            return true; // 성공

	        }
	    } catch (SQLIntegrityConstraintViolationException e) {
	        // 중복 name으로 인한 제약 조건 위반
	        System.out.println("중복된 이름입니다: " + e.getMessage());
	        return false;
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	        System.out.println("MariaDB JDBC 드라이버 로딩 실패");
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return false;
	}

	
	// 목록
	public List<BookmarkGroupDto> getAllBookmarkGroups() {
		List<BookmarkGroupDto> groupList = new ArrayList<>();

		String sql = "SELECT * FROM bookmark_group ORDER BY sort_order ASC";

		// DB 정보
		String DB_URL = "jdbc:mariadb://localhost:3306/test_db";
		String DB_USER = "root";
		String DB_PASS = "zerobase";

		try {
			Class.forName("org.mariadb.jdbc.Driver");

			try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
					PreparedStatement pstmt = conn.prepareStatement(sql);
					ResultSet rs = pstmt.executeQuery()) {

				while (rs.next()) {
					BookmarkGroupDto group = new BookmarkGroupDto();
					group.setId(rs.getInt("id"));
					group.setName(rs.getString("name"));
					group.setSortOrder(rs.getInt("sort_order"));
					group.setCreatedAt(rs.getTimestamp("created_at"));
					group.setUpdatedAt(rs.getTimestamp("updated_at"));
					groupList.add(group);
				}

			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("MariaDB JDBC 드라이버 로딩 실패");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return groupList;
	}

	// 수정
	public void updateBookmarkGroup(int id, String name, int sortOrder) {
		String sql = "UPDATE bookmark_group SET name = ?, sort_order = ?, updated_at = NOW() WHERE id = ?";

	    try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        
	        pstmt.setString(1, name);
	        pstmt.setInt(2, sortOrder);
	        pstmt.setInt(3, id);
	        
	        pstmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}


	// 삭제
	public void deleteBookmarkGroup(int id) {
	    String sql = "DELETE FROM bookmark_group WHERE id = ?";

	    try (
	        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
	        PreparedStatement pstmt = conn.prepareStatement(sql)
	    ) {
	        pstmt.setInt(1, id);
	        
	        int rowsDeleted = pstmt.executeUpdate();
	        
	        if (rowsDeleted > 0) {
	            System.out.println("Bookmark group deleted successfully.");
	        } else {
	            System.out.println("No bookmark group found with the given ID.");
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	public BookmarkGroupDto getGroupById(int groupId) {
	    BookmarkGroupDto group = null;
	    String sql = "SELECT * FROM bookmark_group WHERE id = ?";

	    try (
	        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
	        PreparedStatement pstmt = conn.prepareStatement(sql)
	    ) {
	        pstmt.setInt(1, groupId);
	        try (ResultSet rs = pstmt.executeQuery()) {
	            if (rs.next()) {
	                group = new BookmarkGroupDto();
	                group.setId(rs.getInt("id"));
	                group.setName(rs.getString("name"));
	                group.setCreatedAt(rs.getTimestamp("created_at"));
	                group.setUpdatedAt(rs.getTimestamp("updated_at"));
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return group;
	}

	// 정렬을 위해 추가
	public BookmarkGroupDto getBookmarkGroupById(int id) {
	    String sql = "SELECT * FROM bookmark_group WHERE id = ?";
	    BookmarkGroupDto group = null;

	    try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        
	        pstmt.setInt(1, id);
	        ResultSet rs = pstmt.executeQuery();
	        
	        if (rs.next()) {
	            group = new BookmarkGroupDto();
	            group.setId(rs.getInt("id"));
	            group.setName(rs.getString("name"));
	            group.setSortOrder(rs.getInt("sort_order"));
	            group.setCreatedAt(rs.getTimestamp("created_at"));
	            group.setUpdatedAt(rs.getTimestamp("updated_at"));
	        }
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    
	    return group;
	}
}
