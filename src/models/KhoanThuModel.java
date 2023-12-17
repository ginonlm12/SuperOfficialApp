package models;

public class KhoanThuModel {
	private int IDKhoanThu;
	private String tenKT;
	private String ngayBatDau;
	private String ngayKetThuc;
	private double trongSoDienTich;
	private double trongSoSTV;
	private double HangSo;

	public KhoanThuModel() {
	}

	public KhoanThuModel(int IDKhoanThu, String tenKT, String ngayBatDau, String ngayKetThuc, double trongSoDienTich,
			double trongSoSTV, double HangSo) {
		this.IDKhoanThu = IDKhoanThu;
		this.tenKT = tenKT;
		this.ngayBatDau = ngayBatDau;
		this.ngayKetThuc = ngayKetThuc;
		this.trongSoDienTich = trongSoDienTich;
		this.trongSoSTV = trongSoSTV;
		this.HangSo = HangSo;
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

	public String getngayKetThuc() {
		return ngayKetThuc;
	}

	public double gettrongSoDienTich() {
		return trongSoDienTich;
	}

	public double gettrongSoSTV() {
		return trongSoSTV;
	}

	public double getHangSo() {
		return HangSo;
	}

	public String getngayBatDau() {
		return ngayBatDau;
	}

	public void setngayKetThuc(String ngayKetThuc) {
		this.ngayKetThuc = ngayKetThuc;
	}

	public void settrongSoDienTich(double trongSoDienTich) {
		this.trongSoDienTich = trongSoDienTich;
	}

	public void settrongSoSTV(double trongSoSTV) {
		this.trongSoSTV = trongSoSTV;
	}

	public void setHangSo(double HangSo) {
		this.HangSo = HangSo;
	}

	public void setngayBatDau(String ngayBatDau) {
		this.ngayBatDau = ngayBatDau;
	}

	public void setngayBatDu(String ngayBatDau) {
		this.ngayBatDau = ngayBatDau;
	}
}
