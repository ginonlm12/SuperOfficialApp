package models;

public class ThongKeModel {
    int IDKhoanThu;
    String TenKhoanThu;
    String Loai;
    String TinhTrang;
    int SoHoDaThu;
    Double TongSoTien;

    public ThongKeModel() {
    }

    public ThongKeModel(int IDKhoanThu, String tenKhoanThu, String loai, String tinhTrang, int soHoDaThu, Double tongSoTien) {
        this.IDKhoanThu = IDKhoanThu;
        TenKhoanThu = tenKhoanThu;
        Loai = loai;
        TinhTrang = tinhTrang;
        SoHoDaThu = soHoDaThu;
        TongSoTien = tongSoTien;
    }
    // get and set methods for all attributes
    public int getIDKhoanThu() {
        return IDKhoanThu;
    }
    public void setIDKhoanThu(int IDKhoanThu) {
        this.IDKhoanThu = IDKhoanThu;
    }
    public String getTenKhoanThu() {
        return TenKhoanThu;
    }
    public void setTenKhoanThu(String tenKhoanThu) {
        TenKhoanThu = tenKhoanThu;
    }
    public String getLoai() {
        return Loai;
    }
    public void setLoai(String loai) {
        Loai = loai;
    }
    public String getTinhTrang() {
        return TinhTrang;
    }
    public void setTinhTrang(String tinhTrang) {
        TinhTrang = tinhTrang;
    }
    public int getSoHoDaThu() {
        return SoHoDaThu;
    }
    public void setSoHoDaThu(int soHoDaThu) {
        SoHoDaThu = soHoDaThu;
    }
    public Double getTongSoTien() {
        return TongSoTien;
    }
    public void setTongSoTien(Double tongSoTien) {
        TongSoTien = tongSoTien;
    }
}
