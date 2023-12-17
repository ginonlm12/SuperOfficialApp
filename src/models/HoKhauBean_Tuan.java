package models;

import java.util.List;

public class HoKhauBean_Tuan {
    HoKhauModel_Tuan hoKhauModel_tuan;
    List<NhanKhauModel_Lam> listNhanKhau;

    int soTV;

    public HoKhauBean_Tuan(HoKhauModel_Tuan hoKhauModel_tuan, List<NhanKhauModel_Lam> listNhanKhau, int soTV) {
        this.hoKhauModel_tuan = hoKhauModel_tuan;
        this.listNhanKhau = listNhanKhau;
        this.soTV = soTV;
    }

    public HoKhauBean_Tuan(HoKhauModel_Tuan hoKhauModel_tuan, int soTV) {
        this.hoKhauModel_tuan = hoKhauModel_tuan;
        this.soTV = soTV;
    }

    public HoKhauBean_Tuan() {

    }

    public HoKhauModel_Tuan getHoKhauModel_tuan() {
        return hoKhauModel_tuan;
    }

    public void setHoKhauModel_tuan(HoKhauModel_Tuan hoKhauModel_tuan) {
        this.hoKhauModel_tuan = hoKhauModel_tuan;
    }

    public List<NhanKhauModel_Lam> getListNhanKhau() {
        return listNhanKhau;
    }

    public void setListNhanKhau(List<NhanKhauModel_Lam> listNhanKhau) {
        this.listNhanKhau = listNhanKhau;
    }

    public int getSoTV() {
        return soTV;
    }

    public void setSoTV(int soTV) {
        this.soTV = soTV;
    }
}
