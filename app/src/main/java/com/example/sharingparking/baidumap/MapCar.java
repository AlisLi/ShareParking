package com.example.sharingparking.baidumap;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.sharingparking.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MapCar implements Parcelable {
    private double latitude;   //纬度
    private double longitude;  //经度
    private int imgId;
    private String userName;   //车位主
    private String parkingPrice;  //车位价格
    private String parkingAddress; //车位地址


    static List<MapCar> infos = new ArrayList<MapCar>();

    static {
        infos.add(new MapCar(28.6876884562, 115.8645218847, R.mipmap.placeholder));
        infos.add(new MapCar(28.6568828299, 115.8350762063, R.mipmap.placeholder));
        infos.add(new MapCar(28.6667491455, 115.8110128188, R.mipmap.placeholder));
        infos.add(new MapCar(28.6833061930, 115.9299075035, R.mipmap.placeholder));
        infos.add(new MapCar(28.7426815315, 115.8448061446, R.mipmap.placeholder));
        infos.add(new MapCar(28.6596980000, 115.8502240000, R.mipmap.placeholder));

        infos.add(new MapCar(28.6286374925,115.7991690925, R.mipmap.placeholder));
        infos.add(new MapCar(28.6683537235,115.9254639589, R.mipmap.placeholder));
        infos.add(new MapCar(28.6437382553,115.8427890829, R.mipmap.placeholder));
        infos.add(new MapCar(28.6885984562,115.8688118847, R.mipmap.placeholder));
        infos.add(new MapCar(28.6928417488,115.8634726735, R.mipmap.placeholder));

        infos.add(new MapCar(28.6669866515,115.9062423304, R.mipmap.placeholder));
        infos.add(new MapCar(28.6814488002,115.8949715201, R.mipmap.placeholder));
        infos.add(new MapCar(28.7321940651,115.8493055960, R.mipmap.placeholder));
        infos.add(new MapCar(28.6391289059,115.8648194633, R.mipmap.placeholder));
        infos.add(new MapCar(28.5853719258,115.7931528465, R.mipmap.placeholder));

        infos.add(new MapCar(28.5810410000,115.8845160000, R.mipmap.placeholder));
        infos.add(new MapCar(28.5811000000,115.8851590000, R.mipmap.placeholder));
        infos.add(new MapCar(28.5807200000,115.8848890000, R.mipmap.placeholder));
        infos.add(new MapCar(28.5804360000,115.8845120000, R.mipmap.placeholder));
        infos.add(new MapCar(28.5803910000,115.8841260000, R.mipmap.placeholder));

        infos.add(new MapCar(28.5803650000,115.8820280000, R.mipmap.placeholder));
        infos.add(new MapCar(28.5796480000,115.8830430000, R.mipmap.placeholder));
        infos.add(new MapCar(28.5817590000,115.8852890000, R.mipmap.placeholder));
        infos.add(new MapCar(28.5832080000,115.8902880000, R.mipmap.placeholder));
        infos.add(new MapCar(28.5839090000,115.8899110000, R.mipmap.placeholder));

    }

    public MapCar(double latitude, double longitude, int imgId) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.imgId = imgId;
    }

    public MapCar() {

    }

    public MapCar(String userName, String parkingPrice, String parkingAddress) {
        this.userName = userName;
        this.parkingPrice = parkingPrice;
        this.parkingAddress = parkingAddress;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getParkingPrice() {
        return parkingPrice;
    }

    public void setParkingPrice(String parkingPrice) {
        this.parkingPrice = parkingPrice;
    }

    public String getParkingAddress() {
        return parkingAddress;
    }

    public void setParkingAddress(String parkingAddress) {
        this.parkingAddress = parkingAddress;
    }

    /**
     * 内容接口描述，默认返回0即可
     * @return
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * 将对象序列化成一个Parcel对象，也就是将对象存入Parcel中
     * @param dest
     * @param flags
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.latitude);
        dest.writeDouble(this.longitude);
        dest.writeString(this.userName);
        dest.writeString(this.parkingPrice);
        dest.writeString(this.parkingAddress);
    }

    /**
     * 这里的读的顺序必须与writeToParcel(Parcel dest, int flags)方法中
     * 写的顺序一致，否则数据会有差错
     * @param in
     */
    protected MapCar(Parcel in) {
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
        this.userName = in.readString();
        this.parkingPrice = in.readString();
        this.parkingAddress = in.readString();
    }

    public static final Creator<MapCar> CREATOR = new Creator<MapCar>() {

        /**
         * 从Parcel中读取数据
         */
        @Override
        public MapCar createFromParcel(Parcel source) {
            return new MapCar(source);
        }

        /**
         * 供外部类反序列化本类数组使用
         */
        @Override
        public MapCar[] newArray(int size) {
            return new MapCar[size];
        }
    };

    @Override
    public String toString() {
        return "MapCar{" +
                "latitude = " + latitude +
                "longitude = " + longitude +
                "userName = " + userName +
                ", parkingPrice = " + parkingPrice +
                ", parkingAddress" + parkingAddress +
                '\'' +
                '}';
    }
}
