package service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import dao.Wifidao;
import dto.Wifidto;

public class WifiApiService {
	
    // 거리 계산 메서드
    public static double getDistance(double lat1, double lnt1, double lat2, double lnt2) {
        double earthRadius = 6371.0;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lnt2 - lnt1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                   Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                   Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return earthRadius * c;
    }

    // 사용자 위치를 받아 가까운 20개 와이파이 조회 및 저장
    public List<Wifidto> requestWifiData(double userLat, double userLnt) {
        List<Wifidto> wifiList = new ArrayList<>();

        try {
            String apiKey = "6e6577626639303336366672784b59";
            int start = 1;
            int end = 1000;

            String apiUrl = "http://openapi.seoul.go.kr:8088/" +
                    URLEncoder.encode(apiKey, "UTF-8") + "/json/TbPublicWifiInfo/" +
                    start + "/" + end + "/";

            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            BufferedReader rd = (responseCode >= 200 && responseCode <= 300) ?
                    new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8")) :
                    new BufferedReader(new InputStreamReader(conn.getErrorStream()));

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            rd.close();
            conn.disconnect();

            String result = sb.toString();

            JSONObject json = new JSONObject(result);
            JSONObject data = json.getJSONObject("TbPublicWifiInfo");
            JSONArray rows = data.getJSONArray("row");

            for (int i = 0; i < rows.length(); i++) {
                JSONObject row = rows.getJSONObject(i);

                String mgrNo = row.getString("X_SWIFI_MGR_NO");
                String name = row.getString("X_SWIFI_MAIN_NM");
                String address = row.getString("X_SWIFI_ADRES1");
                String gu = row.getString("X_SWIFI_WRDOFC");
                String detailAddress = row.getString("X_SWIFI_ADRES2");
                String floor = row.getString("X_SWIFI_INSTL_FLOOR");
                String type = row.getString("X_SWIFI_INSTL_TY");
                String service = row.getString("X_SWIFI_SVC_SE");
                String installYear = row.getString("X_SWIFI_CNSTC_YEAR");
                String organization = row.getString("X_SWIFI_INSTL_MBY");

                double lat = Double.parseDouble(row.getString("LAT"));
                double lnt = Double.parseDouble(row.getString("LNT"));
                double distance = getDistance(userLat, userLnt, lat, lnt);

                Wifidto wifi = new Wifidto();
                wifi.setMgrNo(mgrNo);
                wifi.setName(name);
                wifi.setAddress(address);
                wifi.setGu(gu);
                wifi.setDetailAddress(detailAddress);
                wifi.setFloor(floor);
                wifi.setType(type);
                wifi.setService(service);
                wifi.setInstallYear(installYear);
                wifi.setOrganization(organization);
                wifi.setLat(lat);
                wifi.setLnt(lnt);
                wifi.setDistance(distance);

                wifiList.add(wifi);
            }

            wifiList.sort(Comparator.comparingDouble(Wifidto::getDistance));

            for (int i = 0; i < Math.min(20, wifiList.size()); i++) {
                Wifidto wifi = wifiList.get(i);
                Wifidao dao = new Wifidao();
                dao.insertWifi(wifi);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return wifiList.subList(0, Math.min(20, wifiList.size()));
    }

	private int totalCount = 0;

	public int getTotalCount() {
	    return totalCount;
	}

	// 전체 와이파이 데이터를 API로부터 가져와 DB에 저장하는 메서드
	public void getAllWifiData() {
	    int start = 1;
	    int end = 1000;
	    this.totalCount = 0;

	    try {
	        String apiKey = "6e6577626639303336366672784b59";
	        Wifidao dao = new Wifidao();

	        while (true) {
	            String apiUrl = "http://openapi.seoul.go.kr:8088/" +
	                    URLEncoder.encode(apiKey, "UTF-8") + "/json/TbPublicWifiInfo/" +
	                    start + "/" + end + "/";

	            URL url = new URL(apiUrl);
	            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	            conn.setRequestMethod("GET");

	            int responseCode = conn.getResponseCode();
	            BufferedReader rd = (responseCode >= 200 && responseCode <= 300) ?
	                    new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8")) :
	                    new BufferedReader(new InputStreamReader(conn.getErrorStream()));

	            StringBuilder sb = new StringBuilder();
	            String line;
	            while ((line = rd.readLine()) != null) {
	                sb.append(line);
	            }
	            rd.close();
	            conn.disconnect();

	            JSONObject json = new JSONObject(sb.toString());
	            JSONObject data = json.getJSONObject("TbPublicWifiInfo");

	            if (this.totalCount == 0) {
	                this.totalCount = data.getInt("list_total_count");
	            }

	            JSONArray rows = data.getJSONArray("row");

	            List<Wifidto> batchList = new ArrayList<>();

	            for (int i = 0; i < rows.length(); i++) {
	                JSONObject row = rows.getJSONObject(i);

	                Wifidto wifi = new Wifidto();
	                wifi.setMgrNo(row.getString("X_SWIFI_MGR_NO"));
	                wifi.setName(row.getString("X_SWIFI_MAIN_NM"));
	                wifi.setAddress(row.getString("X_SWIFI_ADRES1"));
	                wifi.setGu(row.getString("X_SWIFI_WRDOFC"));
	                wifi.setDetailAddress(row.getString("X_SWIFI_ADRES2"));
	                wifi.setFloor(row.getString("X_SWIFI_INSTL_FLOOR"));
	                wifi.setType(row.getString("X_SWIFI_INSTL_TY"));
	                wifi.setService(row.getString("X_SWIFI_SVC_SE"));
	                wifi.setInstallYear(row.getString("X_SWIFI_CNSTC_YEAR"));
	                wifi.setOrganization(row.getString("X_SWIFI_INSTL_MBY"));
	                wifi.setLat(Double.parseDouble(row.getString("LAT")));
	                wifi.setLnt(Double.parseDouble(row.getString("LNT")));
	                wifi.setDistance(0.0);

	                batchList.add(wifi);
	            }

	            dao.insertWifiList(batchList); // batch insert
	            System.out.println("저장 완료: " + start + "~" + end);

	            start += 1000;
	            end += 1000;

	            if (start > this.totalCount) break;
	        }

	        System.out.println("전체 와이파이 정보 저장 완료 (" + this.totalCount + "개)");

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
}