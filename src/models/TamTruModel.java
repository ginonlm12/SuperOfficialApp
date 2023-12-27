package models;

import java.time.LocalDate;

public class TamTruModel {
    private int IDTamTru;
    private int IDHoKhau;
    private String HoTen;
    private String ThuongTru;
    private LocalDate NgayBatDau;
    private LocalDate NgayKetThuc;
    private LocalDate NgaySinh;
    private String CCCD;
    private String GioiTinh;
    private String LyDo;

    public TamTruModel() {

    }

    public TamTruModel(int IDHoKhau, String hoTen, String thuongTru, LocalDate ngayBatDau, LocalDate ngayKetThuc, String lydo, LocalDate ngaySinh, String CCCD, String gioiTinh) {
        this.IDHoKhau = IDHoKhau;
        HoTen = hoTen;
        ThuongTru = thuongTru;
        NgayBatDau = ngayBatDau;
        NgayKetThuc = ngayKetThuc;
        NgaySinh = ngaySinh;
        this.CCCD = CCCD;
        GioiTinh = gioiTinh;
        LyDo = lydo;
    }


    public int getIDTamTru() {
        return IDTamTru;
    }

    public void setIDTamTru(int IDTamTru) {
        this.IDTamTru = IDTamTru;
    }

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String hoTen) {
        HoTen = hoTen;
    }

    public String getThuongTru() {
        return ThuongTru;
    }

    public void setThuongTru(String thuongTru) {
        ThuongTru = thuongTru;
    }

    public LocalDate getNgayBatDau() {
        return NgayBatDau;
    }

    public void setNgayBatDau(LocalDate ngayBatDau) {
        NgayBatDau = ngayBatDau;
    }

    public LocalDate getNgayKetThuc() {
        return NgayKetThuc;
    }

    public void setNgayKetThuc(LocalDate ngayKetThuc) {
        NgayKetThuc = ngayKetThuc;
    }

    public LocalDate getNgaySinh() {
        return NgaySinh;
    }

    public void setNgaySinh(LocalDate ngaySinh) {
        NgaySinh = ngaySinh;
    }

    public String getCCCD() {
        return CCCD;
    }

    public void setCCCD(String CCCD) {
        this.CCCD = CCCD;
    }

    public String getGioiTinh() {
        return GioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        GioiTinh = gioiTinh;
    }

    public String getLyDo() {
        return LyDo;
    }

    public void setLyDo(String lyDo) {
        LyDo = lyDo;
    }

    public int getIDHoKhau() {
        return IDHoKhau;
    }

    public void setIDHoKhau(int idHoKhau) {
        IDHoKhau = idHoKhau;
    }

}
