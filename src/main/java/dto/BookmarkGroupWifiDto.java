package dto;

import java.sql.Timestamp;

public class BookmarkGroupWifiDto {
    private int groupId;
    private String wifiMgrNo;
    private Timestamp createdAt;
    private String bookmarkGroupName; 
    private String wifiName;          
    private String address;
    private String floor;
    

    public BookmarkGroupWifiDto() {
    }

    public BookmarkGroupWifiDto(int groupId, String wifiMgrNo) {
        this.groupId = groupId;
        this.wifiMgrNo = wifiMgrNo;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getWifiMgrNo() {
        return wifiMgrNo;
    }

    public void setWifiMgrNo(String wifiMgrNo) {
        this.wifiMgrNo = wifiMgrNo;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getBookmarkGroupName() {
        return bookmarkGroupName;
    }

    public void setBookmarkGroupName(String bookmarkGroupName) {
        this.bookmarkGroupName = bookmarkGroupName;
    }

    public String getWifiName() {
        return wifiName;
    }

    public void setWifiName(String wifiName) {
        this.wifiName = wifiName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }
}
