package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
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
	ObservableList<Integer> Year = FXCollections.observableArrayList(current.getYear(), current.getYear() - 1);
	@FXML
	private ComboBox<Integer> CbYear;

	public void initialize(URL arg0, ResourceBundle arg1) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM, yyyy", new Locale("vi", "VN"));
		String formattedDate = current.format(formatter);
		currentDate.setText(formattedDate);
		CbYear.setItems(Year);
		CbYear.setValue(current.getYear());

		try {
			soNhanKhau.setText(NhanKhauService.countNhanKhau());
			soHoKhau.setText(HoKhauService.countHoKhau());
			soPhong.setText(PhongService.countSoPhong());
			soTamTru.setText(TamTruService.countTamTru());
			soTamVang.setText(TamVangService.countTamVang());
			iniGenderChart();
			iniAgeChart();
			iniPaymentChart(current.getYear());
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		CbYear.getSelectionModel().selectedItemProperty().addListener((observableee, oldValueee, newValueee) -> {
			try {
				Integer selectedYear = CbYear.getValue();

				if (selectedYear != null) {
					iniPaymentChart(selectedYear);
				}
			} catch (SQLException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		});

	}

	private void iniPaymentChart(Integer Year) throws SQLException, ClassNotFoundException {
		barTienthuduoc.getData().clear();
		XYChart.Series series = ThuPhiService.PaymentSatistic(Year);
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
