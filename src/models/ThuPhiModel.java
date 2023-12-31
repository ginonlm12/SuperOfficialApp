package models;

public class ThuPhiModel {
    private int IDKhoanThu;
    private int IDHoKhau;
    private double SoTienPhaiDong;
    private double SoTien;
    private String NgayDong;

    public ThuPhiModel() {
    }

    public ThuPhiModel(int IDKhoanThu, int IDHoKhau, double SoTienPhaiDong, double soTien, String ngayDong) {
        this.IDKhoanThu = IDKhoanThu;
        this.IDHoKhau = IDHoKhau;
        this.SoTienPhaiDong = SoTienPhaiDong;
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
    public double getSoTienPhaiDong() {
        return SoTienPhaiDong;
    }
    public void setSoTienPhaiDong(double soTienPhaiDong) {
        SoTienPhaiDong = soTienPhaiDong;
    }
}
