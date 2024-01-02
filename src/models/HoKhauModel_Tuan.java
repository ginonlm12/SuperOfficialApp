
package models;

public class HoKhauModel_Tuan {
    int IDHoKhau;
    int SoPhong;
    String NgayDen;
    String NgayDi;
    String SDT;

    public HoKhauModel_Tuan(int IDHoKhau, int soPhong, String ngayDen, String ngayDi, String SDT) {
        this.IDHoKhau = IDHoKhau;
        SoPhong = soPhong;
        NgayDen = ngayDen;
        NgayDi = ngayDi;
        this.SDT = SDT;
    }

    public HoKhauModel_Tuan() {

    }

    public int getIDHoKhau() {
        return IDHoKhau;
    }

    public void setIDHoKhau(int IDHoKhau) {
        this.IDHoKhau = IDHoKhau;
    }

    public int getSoPhong() {
        return SoPhong;
    }

    public void setSoPhong(int soPhong) {
        SoPhong = soPhong;
    }

    public String getNgayDen() {
        return NgayDen;
    }

    public void setNgayDen(String ngayDen) {
        NgayDen = ngayDen;
    }

    public String getNgayDi() {
        return NgayDi;
    }

    public void setNgayDi(String ngayDi) {
        NgayDi = ngayDi;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }
}
