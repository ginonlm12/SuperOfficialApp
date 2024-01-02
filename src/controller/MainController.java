package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import services.*;

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
	private BarChart<String, Integer> barTienthuduoc;
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
	@FXML
	private Label soTamTru;
	@FXML
	private Label soTamVang;
	private final LocalDate current = LocalDate.now();

	public void initialize(URL arg0, ResourceBundle arg1) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM, yyyy", new Locale("vi", "VN"));
		String formattedDate = current.format(formatter);
		currentDate.setText(formattedDate);

		try {
			soNhanKhau.setText(NhanKhauService.countNhanKhau());
			soHoKhau.setText(HoKhauService.countHoKhau());
			soPhong.setText(PhongService.countSoPhong());
			soTamTru.setText(TamTruService.countTamTru());
			soTamVang.setText(TamVangService.countTamVang());
			iniGenderChart();
			iniAgeChart();
			iniPaymentChart();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	private void iniPaymentChart() throws SQLException, ClassNotFoundException {
		XYChart.Series series = ThuPhiService.PaymentSatistic();
		barTienthuduoc.getData().add(series);
		barTienthuduoc.lookup(".chart-plot-background").setStyle("-fx-background-color: transparent");
	}

	private void iniGenderChart() throws SQLException, ClassNotFoundException {
		ObservableList<PieChart.Data> GenderData = FXCollections.observableArrayList(NhanKhauService.GenderClassify());
		pieGioiTinh.setData(GenderData);
	}

	private void iniAgeChart() throws SQLException, ClassNotFoundException {
		ObservableList<PieChart.Data> AgeData = FXCollections.observableArrayList(NhanKhauService.AgeClassify());
		pieDoTuoi.setData(AgeData);
	}
}
