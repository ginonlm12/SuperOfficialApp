package models;

public class ThuPhiModel {
    private int IDKhoanThu;
    private int IDHoKhau;
    private double SoTien;
    private String NgayDong;

    public ThuPhiModel(int IDKhoanThu, int IDHoKhau, double soTien, String ngayDong) {
        this.IDKhoanThu = IDKhoanThu;
        this.IDHoKhau = IDHoKhau;
        SoTien = soTien;
        NgayDong = ngayDong;
    }
    //get and set method
    public int getIDKhoanThu() {
        return IDKhoanThu;
    }
    public int getIDHoKhau() {
        return IDHoKhau;
    }
    public double getSoTien() {
        return SoTien;
    }
    public String getNgayDong() {
        return NgayDong;
    }
    public void setIDKhoanThu(int IDKhoanThu) {
        this.IDKhoanThu = IDKhoanThu;
    }
    public void setIDHoKhau(int IDHoKhau) {
        this.IDHoKhau = IDHoKhau;
    }
    public void setSoTien(double soTien) {
        SoTien = soTien;
    }
    public void setNgayDong(String ngayDong) {
        NgayDong = ngayDong;
    }
}
