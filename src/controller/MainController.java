package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import services.HoKhauService_Tuan;
import services.NhanKhauService_Lam;
import services.PhongService;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainController implements Initializable {
	@FXML
	private Label currentDate;
	@FXML
	private PieChart pieDoTuoi;
	@FXML
	private PieChart pieGioiTinh;
	@FXML
	private Label soHoKhau;
	@FXML
	private Label soNhanKhau;
	@FXML
	private Label soPhong;

	public void initialize(URL arg0, ResourceBundle arg1) {
		LocalDate current = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM, yyyy", new Locale("vi", "VN"));
		String formattedDate = current.format(formatter);
		System.out.println(formattedDate);
		currentDate.setText(formattedDate);

		try {
			soNhanKhau.setText(NhanKhauService_Lam.countNhanKhau());
			soHoKhau.setText(HoKhauService_Tuan.countHoKhau());
			soPhong.setText(PhongService.countSoPhong());
			iniGenderChart();
			iniAgeChart();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	private void iniGenderChart() throws SQLException, ClassNotFoundException {
		ObservableList<PieChart.Data> GenderData = FXCollections.observableArrayList(NhanKhauService_Lam.GenderClassify());
		pieGioiTinh.setData(GenderData);
	}

	private void iniAgeChart() throws SQLException, ClassNotFoundException {
		ObservableList<PieChart.Data> AgeData = FXCollections.observableArrayList(NhanKhauService_Lam.AgeClassify());
		pieDoTuoi.setData(AgeData);
	}
}
