package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import dto.BookmarkGroupWifiDto;

public class BookmarkGroupWifiDao {
	
	private final String DB_URL = "jdbc:mariadb://localhost:3306/test_db";
	private final String DB_USER = "root";
	private final String DB_PASS = "zerobase";

    public void addBookmarkGroupWifi(BookmarkGroupWifiDto bookmark) {
        String sql = "INSERT INTO bookmark_group_wifi (group_id, wifi_mgr_no, created_at) VALUES (?, ?, NOW())";
        
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, bookmark.getGroupId());
            pstmt.setString(2, bookmark.getWifiMgrNo());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void addWifiToGroup(int groupId, String wifiMgrNo) {
        String sql = "INSERT INTO bookmark_group_wifi (group_id, wifi_mgr_no, created_at) " +
                     "SELECT ?, ?, NOW() FROM DUAL " +
                     "WHERE NOT EXISTS (" +
                     "  SELECT 1 FROM bookmark_group_wifi WHERE group_id = ? AND wifi_mgr_no = ?" +
                     ")";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, groupId);
            pstmt.setString(2, wifiMgrNo);
            pstmt.setInt(3, groupId);
            pstmt.setString(4, wifiMgrNo);

            pstmt.executeUpdate();
            System.out.println("북마크 그룹에 와이파이 추가 완료 (중복 검사 포함)");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

   
    
    public List<BookmarkGroupWifiDto> getBookmarkGroupWifis(int groupId) {
        List<BookmarkGroupWifiDto> list = new ArrayList<>();
        String sql = "SELECT b.group_id, b.wifi_mgr_no, w.name AS wifi_name, w.address, b.created_at " +
                "FROM bookmark_group_wifi b " +
                "JOIN wifi w ON b.wifi_mgr_no = w.mgr_no " +
                "WHERE b.group_id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, groupId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    BookmarkGroupWifiDto dto = new BookmarkGroupWifiDto();
                    dto.setGroupId(rs.getInt("group_id"));
                    dto.setWifiMgrNo(rs.getString("wifi_mgr_no"));
                    dto.setWifiName(rs.getString("wifi_name"));  // alias 사용한 것 주의
                    dto.setAddress(rs.getString("address"));
                    dto.setCreatedAt(rs.getTimestamp("created_at"));

                    list.add(dto);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public void removeWifiFromGroup(int groupId, String wifiMgrNo) {
        String sql = "DELETE FROM bookmark_group_wifi WHERE group_id = ? AND wifi_mgr_no = ?";

        try (
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setInt(1, groupId);
            pstmt.setString(2, wifiMgrNo);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
}
