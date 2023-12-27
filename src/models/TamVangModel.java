package models;

import java.time.LocalDate;

public class TamVangModel {
    int IDTamVang;
    int IDNhanKhau;
    String HoTen;
    LocalDate NgayBatDau;
    LocalDate NgayKetThuc;
    String LyDo;


    public TamVangModel() {
    }

    public TamVangModel(int iDTamVang) {
        IDTamVang = iDTamVang;
    }

    public TamVangModel(int IDNhanKhau, LocalDate ngayBatDau, LocalDate ngayKetThuc, String lydo) {
        this.IDNhanKhau = IDNhanKhau;
        NgayBatDau = ngayBatDau;
        NgayKetThuc = ngayKetThuc;
        LyDo = lydo;
    }

    public int getIDTamVang() {
        return IDTamVang;
    }

    public void setIDTamVang(int IDTamVang) {
        this.IDTamVang = IDTamVang;
    }

    public int getIDNhanKhau() {
        return IDNhanKhau;
    }

    public void setIDNhanKhau(int IDNhanKhau) {
        this.IDNhanKhau = IDNhanKhau;
    }

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String hoTen) {
        HoTen = hoTen;
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

    public String getLyDo() {
        return LyDo;
    }

    public void setLyDo(String lyDo) {
        LyDo = lyDo;
    }

}
