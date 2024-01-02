package models;

public class ThuPhiBean {
    private ThuPhiModel thuPhiModel;
    private String tenKhoanThu;
    private String tenChuHo;

    // getter and setter
    public ThuPhiModel getThuPhiModel() {
        return thuPhiModel;
    }
    public void setThuPhiModel(ThuPhiModel thuPhiModel) {
        this.thuPhiModel = thuPhiModel;
    }
    public String getTenKhoanThu() {
        return tenKhoanThu;
    }
    public void setTenKhoanThu(String tenKhoanThu) {
        this.tenKhoanThu = tenKhoanThu;
    }
    public String getTenChuHo() {
        return tenChuHo;
    }
    public void setTenChuHo(String tenChuHo) {
        this.tenChuHo = tenChuHo;
    }
    public double getSoTienDong() {
        return thuPhiModel.getSoTien();
    }
    public double getSoTienPhaiDong() {
        return thuPhiModel.getSoTienPhaiDong();
    }
    public String getNgayDong() {
        return thuPhiModel.getNgayDong();
    }
}
