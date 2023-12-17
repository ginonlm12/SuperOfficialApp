package models;

public class KhoanThuModel {
	private int IDKhoanThu;
	private String tenKT;
	private String ngayBatDau;
	private String ngayKetThuc;
	private double trongSoDienTich;
	private double trongSoSTV;
	private double HangSo;
	
	public KhoanThuModel(int IDKhoanThu, String tenKT, String ngayBatDau, String ngayKetThuc, double trongSoDienTich, double trongSoSTV, double HangSo) {
		this.IDKhoanThu = IDKhoanThu;
		this.tenKT=tenKT;
		this.ngayBatDau=ngayBatDau;
		this.ngayKetThuc=ngayKetThuc;
		this.trongSoDienTich=trongSoDienTich;
		this.trongSoSTV=trongSoSTV;
		this.HangSo=HangSo;
	}

	public int getIDKhoanThu() {
		return IDKhoanThu;
	}

	public void setIDKhoanThu(int IDKhoanThu) {
		this.IDKhoanThu = IDKhoanThu;
	}

	public String gettenKT() {
		return tenKT;
	}

	public void settenKT(String tenKT) {
		this.tenKT = tenKT;
	}
}
