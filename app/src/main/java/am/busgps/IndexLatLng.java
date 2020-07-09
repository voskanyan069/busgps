package am.busgps;

public class IndexLatLng {
    private int index;
    private double lat;
    private double lng;

    public IndexLatLng(int index2, double lat2, double lng2) {
        this.index = index2;
        this.lat = lat2;
        this.lng = lng2;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index2) {
        index = index2;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat2) {
        lat = lat2;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng2) {
        lng = lng2;
    }
}