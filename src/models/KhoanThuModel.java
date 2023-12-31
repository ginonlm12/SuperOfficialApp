package models;

public class KhoanThuModel {
	private int IDKhoanThu;
	private String TenKT;
	private String NgayBatDau;
	private String NgayKetThuc;
	private double TrongSoDienTich;
	private double TrongSoSTV;
	private double HangSo;
	private String LoaiKhoanThu;

	public KhoanThuModel() {
	}

	public KhoanThuModel(int IDKhoanThu, String TenKT, String NgayBatDau, String NgayKetThuc, double TrongSoDienTich,
			double TrongSoSTV, double HangSo, String LoaiKhoanThu) {
		this.IDKhoanThu = IDKhoanThu;
		this.TenKT = TenKT;
		this.NgayBatDau = NgayBatDau;
		this.NgayKetThuc = NgayKetThuc;
		this.TrongSoDienTich = TrongSoDienTich;
		this.TrongSoSTV = TrongSoSTV;
		this.HangSo = HangSo;
		this.LoaiKhoanThu = LoaiKhoanThu;
	}

	public int getIDKhoanThu() {
		return IDKhoanThu;
	}
	
	public String getTenKT() {
		return TenKT;
	}
	
	public String getNgayKetThuc() {
		return NgayKetThuc;
	}
	
	public double getTrongSoDienTich() {
		return TrongSoDienTich;
	}
	
	public double getTrongSoSTV() {
		return TrongSoSTV;
	}
	
	public double getHangSo() {
		return HangSo;
	}
	
	public String getNgayBatDau() {
		return NgayBatDau;
	}

	public void setTenKT(String TenKT) {
		this.TenKT = TenKT;
	}

	public void setIDKhoanThu(int IDKhoanThu) {
		this.IDKhoanThu = IDKhoanThu;
	}

	public void setNgayKetThuc(String NgayKetThuc) {
		this.NgayKetThuc = NgayKetThuc;
	}

	public void setTrongSoDienTich(double TrongSoDienTich) {
		this.TrongSoDienTich = TrongSoDienTich;
	}
	
	public void setTrongSoSTV(double TrongSoSTV) {
		this.TrongSoSTV = TrongSoSTV;
	}

	public void setHangSo(double HangSo) {
		this.HangSo = HangSo;
	}

	public void setNgayBatDau(String NgayBatDau) {
		this.NgayBatDau = NgayBatDau;
	}
	public String getLoaiKhoanThu() {
		return LoaiKhoanThu;
	}
	public void setLoaiKhoanThu(String LoaiKhoanThu) {
		this.LoaiKhoanThu = LoaiKhoanThu;
	}
}
