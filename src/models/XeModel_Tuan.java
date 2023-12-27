package models;

public class XeModel_Tuan {
    private int IDHoKhau;
    private int OTo;
    private int XeDap;
    private int XeMay;

    private int TongSoXe;

    private String ChuHo;

    public XeModel_Tuan(int IDHoKhau, int OTo, int xeDap, int xeMay) {
        this.IDHoKhau = IDHoKhau;
        this.OTo = OTo;
        XeDap = xeDap;
        XeMay = xeMay;
    }

    public XeModel_Tuan(int IDHoKhau, int OTo, int xeDap, int xeMay, int tongSoXe, String chuHo) {
        this.IDHoKhau = IDHoKhau;
        this.OTo = OTo;
        XeDap = xeDap;
        XeMay = xeMay;
        TongSoXe = tongSoXe;
        ChuHo = chuHo;
    }

    public int getIDHoKhau() {
        return IDHoKhau;
    }

    public void setIDHoKhau(int IDHoKhau) {
        this.IDHoKhau = IDHoKhau;
    }

    public int getOTo() {
        return OTo;
    }

    public void setOTo(int OTo) {
        this.OTo = OTo;
    }

    public int getXeDap() {
        return XeDap;
    }

    public void setXeDap(int xeDap) {
        XeDap = xeDap;
    }

    public int getXeMay() {
        return XeMay;
    }

    public void setXeMay(int xeMay) {
        XeMay = xeMay;
    }

    public int getTongSoXe() {
        return TongSoXe;
    }

    public String getChuHo() {
        return ChuHo;
    }
}
