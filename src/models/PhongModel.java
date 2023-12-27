package models;

public class PhongModel {
    private int SoPhong;
    private double DienTich;
    private String LoaiPhong;
    private String TrangThai = "Chưa được sử dụng";

    public PhongModel() {

    }

    public PhongModel(int soPhong, double dienTich, String loaiPhong) {
        SoPhong = soPhong;
        DienTich = dienTich;
        LoaiPhong = loaiPhong;
    }

    public PhongModel(int soPhong, double dienTich, String loaiPhong, String trangThai) {
        SoPhong = soPhong;
        DienTich = dienTich;
        LoaiPhong = loaiPhong;
        TrangThai = trangThai;
    }

    public static boolean comparePhongModel(PhongModel phong1, PhongModel phong2) {
        return phong1.getSoPhong() == phong2.getSoPhong()
                && phong1.getDienTich() == phong2.getDienTich()
                && phong1.getLoaiPhong() == phong2.getLoaiPhong();
    }

    public int getSoPhong() {
        return SoPhong;
    }

    public void setSoPhong(int soPhong) {
        SoPhong = soPhong;
    }

    public double getDienTich() {
        return DienTich;
    }

    public void setDienTich(double dienTich) {
        DienTich = dienTich;
    }

    public String getLoaiPhong() {
        return LoaiPhong;
    }

    public void setLoaiPhong(String loaiPhong) {
        LoaiPhong = loaiPhong;
    }

    public String getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(String trangThai) {
        TrangThai = trangThai;
    }
}
