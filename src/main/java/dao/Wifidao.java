package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import dto.Wifidto;

public class Wifidao {

    private final String DB_URL = "jdbc:mariadb://localhost:3306/test_db";
    private final String DB_USER = "root";
    private final String DB_PASS = "zerobase";

    // 한건씩 넣는 경우
    public void insertWifi(Wifidto wifi) {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

            String sql = "INSERT IGNORE INTO wifi (mgr_no, distance, gu, name, address, detail_address, floor, type, service, install_year, organization, lat, lnt) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, wifi.getMgrNo());
            pstmt.setDouble(2, wifi.getDistance());
            pstmt.setString(3, wifi.getGu());
            pstmt.setString(4, wifi.getName());
            pstmt.setString(5, wifi.getAddress());
            pstmt.setString(6, wifi.getDetailAddress());
            pstmt.setString(7, wifi.getFloor());
            pstmt.setString(8, wifi.getType());
            pstmt.setString(9, wifi.getService());
            pstmt.setString(10, wifi.getInstallYear());
            pstmt.setString(11, wifi.getOrganization());
            pstmt.setDouble(12, wifi.getLat());
            pstmt.setDouble(13, wifi.getLnt());
            pstmt.executeUpdate();

            pstmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void insertWifiList(List<Wifidto> wifiList) {
        String sql = "INSERT IGNORE INTO wifi (mgr_no, distance, gu, name, address, detail_address, floor, type, service, install_year, organization, lat, lnt) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            Class.forName("org.mariadb.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(sql);

            for (Wifidto wifi : wifiList) {
                pstmt.setString(1, wifi.getMgrNo());
                pstmt.setDouble(2, wifi.getDistance());
                pstmt.setString(3, wifi.getGu());
                pstmt.setString(4, wifi.getName());
                pstmt.setString(5, wifi.getAddress());
                pstmt.setString(6, wifi.getDetailAddress());
                pstmt.setString(7, wifi.getFloor());
                pstmt.setString(8, wifi.getType());
                pstmt.setString(9, wifi.getService());
                pstmt.setString(10, wifi.getInstallYear());
                pstmt.setString(11, wifi.getOrganization());
                pstmt.setDouble(12, wifi.getLat());
                pstmt.setDouble(13, wifi.getLnt());

                pstmt.addBatch();
            }

            pstmt.executeBatch();
            conn.commit();

            pstmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    
    public Wifidto getWifiByMgrNo(String mgrNo) {
        Wifidto wifi = null;
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

            String sql = "SELECT * FROM wifi WHERE mgr_no = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, mgrNo);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                wifi = new Wifidto();
                wifi.setMgrNo(rs.getString("mgr_no"));
                wifi.setGu(rs.getString("gu"));
                wifi.setName(rs.getString("name"));
                wifi.setAddress(rs.getString("address"));
                wifi.setDetailAddress(rs.getString("detail_address"));
                wifi.setFloor(rs.getString("floor"));
                wifi.setType(rs.getString("type"));
                wifi.setService(rs.getString("service"));
                wifi.setInstallYear(rs.getString("install_year"));
                wifi.setOrganization(rs.getString("organization"));
                wifi.setLat(rs.getDouble("lat"));
                wifi.setLnt(rs.getDouble("lnt"));
            }

            rs.close();
            pstmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return wifi;
    }

}
