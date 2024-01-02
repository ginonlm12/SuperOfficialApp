package models;

import java.util.List;

public class HoKhauBean {
    HoKhauModel hoKhauModel;
    List<NhanKhauModel> listNhanKhau;
    int soTV;

    public HoKhauBean(HoKhauModel hoKhauModel, List<NhanKhauModel> listNhanKhau, int soTV) {
        this.hoKhauModel = hoKhauModel;
        this.listNhanKhau = listNhanKhau;
        this.soTV = soTV;
    }

    public HoKhauBean(HoKhauModel hoKhauModel, int soTV) {
        this.hoKhauModel = hoKhauModel;
        this.soTV = soTV;
    }

    public HoKhauBean() {

    }

    public HoKhauModel getHoKhauModel_tuan() {
        return hoKhauModel;
    }

    public void setHoKhauModel_tuan(HoKhauModel hoKhauModel) {
        this.hoKhauModel = hoKhauModel;
    }

    public List<NhanKhauModel> getListNhanKhau() {
        return listNhanKhau;
    }

    public void setListNhanKhau(List<NhanKhauModel> listNhanKhau) {
        this.listNhanKhau = listNhanKhau;
    }

    public int getSoTV() {
        return soTV;
    }

    public void setSoTV(int soTV) {
        this.soTV = soTV;
    }
}
