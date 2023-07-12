package com.carpooling.frequentroute.gripmap;

import com.carpooling.frequentroute.entity.FrequentPoint;
import com.carpooling.frequentroute.entity.GridPoint;
import com.carpooling.frequentroute.entity.Route;
import com.carpooling.frequentroute.entity.Waypoint;
import com.carpooling.frequentroute.model.LatLng;
import com.carpooling.frequentroute.model.LatLngGrid;
import com.carpooling.frequentroute.model.Offer;
import com.github.chen0040.fpm.AssocRuleMiner;
import com.github.chen0040.fpm.data.ItemSet;
import com.github.chen0040.fpm.data.ItemSets;
import com.github.chen0040.fpm.data.MetaData;
import com.github.chen0040.fpm.fpg.FPGrowth;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class MapUtility {

    public static final double COST_OF_KM = 7000;

    public static double START_LATITUDE = 20.99;
    public static double START_LONGITUDE = 105.77;
    public static double END_LATITUDE = 21.05;
    public static double END_LONGITUDE = 105.85;
    public static double LENGTH_REGION = 0.0025;

    public static double MINUTES = 0.25; // Trong frequent thì chỉ có time đến dự kiến => Cộng thêm 15' để tạo ra time muộn nhất

    /**
     * Method to decode polyline points Courtesy :
     * jeffreysambells.com/2010/05/27
     * /decoding-polylines-from-google-maps-direction-api-with-java
     */
    public static List<LatLng> decodePoly(String encoded) {

        List<LatLng> poly = new ArrayList<>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }

    public static String tranformToGrid(List<LatLng> listPoint) {
        StringBuilder builder = new StringBuilder();
        int preIndexLat = -1;
        int preIndexLong = -1;
        for (LatLng latLng : listPoint) {
            if (latLng.latitude >= START_LATITUDE && latLng.latitude <= END_LATITUDE
                    && latLng.longitude >= START_LONGITUDE && latLng.longitude <= END_LONGITUDE) {

                double latTemp = latLng.latitude - START_LATITUDE;
                double longTemp = latLng.longitude - START_LONGITUDE;
                int indexLat = (int) (latTemp / LENGTH_REGION) + 1;
                int indexLong = (int) (longTemp / LENGTH_REGION) + 1;
                if (indexLat != preIndexLat || indexLong != preIndexLong) {
                    if (builder.length() != 0) builder.append(", ");
                    builder.append("(").append(indexLat).append(":").append(indexLong).append(")");
                    preIndexLat = indexLat;
                    preIndexLong = indexLong;
                }
            }
        }
        return builder.toString();
    }

    // Dán nhán dữ liệu
    public static List<GridPoint> tranformTriptoGrid(List<Waypoint> waypoints, int grid_trip_id) {
        ArrayList arrayGridPoint = new ArrayList();
        int preIndexLat = -1;
        int preIndexLong = -1;
        int preTime = -1;
        for (Waypoint waypoint : waypoints) {
            double lat = waypoint.getLatitude();
            double lng = waypoint.getLongitude();
            Date time = waypoint.getTime();
            int hh = time.getHours();
            int mm = time.getMinutes();

            if (lat >= START_LATITUDE && lat <= END_LATITUDE
                    && lng >= START_LONGITUDE && lng <= END_LONGITUDE) {

                double latTemp = lat - START_LATITUDE;
                double longTemp = lng - START_LONGITUDE;
                int indexLat = (int) (latTemp / LENGTH_REGION) + 1;
                int indexLong = (int) (longTemp / LENGTH_REGION) + 1;
                int timeInt = convertTimetoInt(hh, mm);

                if (indexLat != preIndexLat || indexLong != preIndexLong || timeInt != preTime) {

                    GridPoint gridPoint = new GridPoint();
                    gridPoint.setGrid_trip_id(grid_trip_id);
                    gridPoint.setLat(indexLat);
                    gridPoint.setLng(indexLong);
                    gridPoint.setTime(timeInt);
                    arrayGridPoint.add(gridPoint);

                    preIndexLat = indexLat;
                    preIndexLong = indexLong;
                    preTime = timeInt;
                }
            }
        }
        return arrayGridPoint;
    }

    public static int convertTimetoInt(int hh, int mm) {
        int sumTime = 0;
        sumTime += hh * 12;
        sumTime += mm/5;
        return sumTime;
    }

    public static List<ItemSet> findFrequentRoute(List<List<GridPoint>> gridmap, int min_support, int min_length) {
        List<ItemSet> itemSets;
        List<List<String>> dataset = new ArrayList<>();
        for (List<GridPoint> gridPointList : gridmap) {
            List<String> strings = new ArrayList<>();
            for (GridPoint gridPoint : gridPointList) {
                strings.add(gridPoint.toString());
            }
            dataset.add(strings);
        }

        AssocRuleMiner method = new FPGrowth();
        method.setMinSupportLevel(min_support);
        MetaData metaData = new MetaData(dataset);
        ItemSets frequent_item_sets = method.findMaxPatterns(dataset, metaData.getUniqueItems());
        itemSets = frequent_item_sets.getSets();
        for (int i = 0; i < itemSets.size(); i++) {
            ItemSet itemSet = itemSets.get(i);
            if(itemSet.getItems().size() < min_length) itemSet.getItems().remove(i);
        }
        return itemSets;
    }

    public static FrequentPoint createFrequentPoint (String stringFP, int frequent_route_id){

        //Lấy dữ liệu từng điểm tọa độ
        FrequentPoint frequentPoint = new FrequentPoint();
        frequentPoint.setFrequent_route_id(frequent_route_id);

        // Tính toán tọa tương với ô trong bản đồ lưới
        if(!stringFP.isEmpty()) {
            stringFP = stringFP.substring(1,stringFP.length()-1);
            String[] arrString = stringFP.split(":");          // Tách từng phần tử trong (x:y:t)
            frequentPoint.setLat(Integer.parseInt(arrString[0]));   // Quy đổi x -> X
            frequentPoint.setLng(Integer.parseInt(arrString[1]));   // Quy đổi y -> Y
            frequentPoint.setTime(Integer.parseInt(arrString[2]));  // Quy đổi t -> T
        }

        return frequentPoint;           // trả về nhãn dạng (X:Y:T)
    }

    public static void covertFrequentPointToString(List<FrequentPoint> listPoint, List<String>listString) {
        for (FrequentPoint point : listPoint) {
            listString.add(point.toString());
        }
    }

    /* offers là danh sách chuyến đi hiện có. Method sẽ thực hiện tìm kiếm chuyến đi có account_id, origin, destination có trong danh sách đó
    hay không và nếu có thì capacity còn đủ chỗ không
     */
    public static Offer checkAvailableSeatOffer(List<Offer> offers, String account_id, String origin, String destination) {
        for (Offer offer : offers) {
            if(offer.getUser_id().equals(account_id)
            && offer.getCurrentLocation().equals(origin)
            && offer.getDestination().equals(destination)) {
                if(offer.getSeatsAvailable() - 1 > 0) {
                    return offer;
                }
            }
        }
        return null;
    }






    public static LatLngGrid convertToGrid(LatLng latLng) {
        if (latLng.latitude >= START_LATITUDE && latLng.latitude <= END_LATITUDE
                && latLng.longitude >= START_LONGITUDE && latLng.longitude <= END_LONGITUDE) {

            double latTemp = latLng.latitude - START_LATITUDE;
            double longTemp = latLng.longitude - START_LONGITUDE;
            int indexLat = (int) (latTemp / LENGTH_REGION) + 1;
            int indexLong = (int) (longTemp / LENGTH_REGION) + 1;
            return new LatLngGrid(indexLat, indexLong);
        }
        return null;
    }

    public static String convertTimeFromDouble(double time) {
        int hour = (int) time;
        int minute = (int) ((time - hour) * 60);
        String result = "" + hour + ":" + minute;
        return result;
    }

    public static double timeLate(double expectedTime) {
        return expectedTime + MapUtility.MINUTES;
    }
}
