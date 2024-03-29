package models;

public class NhanKhauModel {
	private int IDNhanKhau;
	private int IDHoKhau;
	private String QHvsChuHo = "(Chưa nhập)";
	private String HoTen = "(chưa nhập)";
	private String NgaySinh = "0001-01-01";
	private String CCCD = "(Chưa nhập)";
	private String NgheNghiep = "(Chưa nhập)";
	private String GioiTinh = "(Chưa nhập)";
	private String DanToc = "(Chưa nhập)";
	private String QueQuan = "(Chưa nhập)";
	private int SoPhong;
	
	public int getSoPhong() {
		return SoPhong;
	}

	public void setSoPhong(int soPhong) {
		SoPhong = soPhong;
	}

	public NhanKhauModel() {
	}

	public NhanKhauModel(int maChuHo, int maHo, String qh, String hoten, String cccdChuHo, String gioiTinh) {
		this.IDNhanKhau = maChuHo;
		this.IDHoKhau = maHo;
		this.QHvsChuHo = qh;
		HoTen = hoten;
		this.CCCD = cccdChuHo;
		GioiTinh = gioiTinh;
	}

	public NhanKhauModel(int IDNhanKhau, int IDHoKhau, String QHvsChuHo, String hoTen, String ngaySinh, String CCCD, String ngheNghiep, String gioiTinh, String dantoc, String queQuan) {
		this.IDNhanKhau = IDNhanKhau;
		this.IDHoKhau = IDHoKhau;
		this.QHvsChuHo = QHvsChuHo;
		HoTen = hoTen;
		NgaySinh = ngaySinh;
		this.CCCD = CCCD;
		NgheNghiep = ngheNghiep;
		GioiTinh = gioiTinh;
		DanToc = dantoc;
		QueQuan = queQuan;
	}

	public void setIDNhanKhau(int IDNhanKhau) {
		this.IDNhanKhau = IDNhanKhau;
	}

	public void setIDHoKhau(int IDHoKhau) {
		this.IDHoKhau = IDHoKhau;
	}

	public void setQHvsChuHo(String QHvsChuHo) {
		this.QHvsChuHo = QHvsChuHo;
	}

	public void setHoTen(String hoTen) {
		HoTen = hoTen;
	}

	public void setNgaySinh(String ngaySinh) {
		NgaySinh = ngaySinh;
	}

	public void setCCCD(String CCCD) {
		this.CCCD = CCCD;
	}

	public void setNgheNghiep(String ngheNghiep) {
		NgheNghiep = ngheNghiep;
	}

	public void setGioiTinh(String gioiTinh) {
		GioiTinh = gioiTinh;
	}

	public void setDanToc(String dantoc) {
		DanToc = dantoc;
	}

	public void setQueQuan(String queQuan) {
		QueQuan = queQuan;
	}

	public int getIDNhanKhau() {
		return IDNhanKhau;
	}

	public int getIDHoKhau() {
		return IDHoKhau;
	}

	public String getQHvsChuHo() {
		return QHvsChuHo;
	}

	public String getHoTen() {
		return HoTen;
	}

	public String getNgaySinh() {
		return NgaySinh;
	}

	public String getCCCD() {
		return CCCD;
	}

	public String getNgheNghiep() {
		return NgheNghiep;
	}

	public String getGioiTinh() {
		return GioiTinh;
	}

	public String getDanToc() {
		return DanToc;
	}

	public String getQueQuan() {
		return QueQuan;
	}

	public static boolean compareNhanKhauModels(NhanKhauModel nhanKhau1, NhanKhauModel nhanKhau2) {
		return nhanKhau1.getIDNhanKhau() == nhanKhau2.getIDNhanKhau() &&
				nhanKhau1.getIDHoKhau() == nhanKhau2.getIDHoKhau() &&
				nhanKhau1.getQHvsChuHo().equals(nhanKhau2.getQHvsChuHo()) &&
				nhanKhau1.getHoTen().equals(nhanKhau2.getHoTen()) &&
				nhanKhau1.getNgaySinh().equals(nhanKhau2.getNgaySinh()) &&
				nhanKhau1.getCCCD().equals(nhanKhau2.getCCCD()) &&
				nhanKhau1.getNgheNghiep().equals(nhanKhau2.getNgheNghiep()) &&
				nhanKhau1.getGioiTinh().equals(nhanKhau2.getGioiTinh()) &&
				nhanKhau1.getDanToc().equals(nhanKhau2.getDanToc()) &&
				nhanKhau1.getQueQuan().equals(nhanKhau2.getQueQuan());
	}
}
