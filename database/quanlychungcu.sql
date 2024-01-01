-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 31, 2023 at 12:21 PM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `demo01`
--

-- --------------------------------------------------------

--
-- Table structure for table `hokhau`
--

CREATE TABLE `hokhau` (
  `IDHoKhau` int(11) NOT NULL,
  `SoPhong` int(11) NOT NULL DEFAULT 0,
  `NgayDen` date NOT NULL DEFAULT '0001-01-01',
  `NgayDi` date NOT NULL DEFAULT '0001-01-01',
  `SDT` varchar(15) NOT NULL DEFAULT '(Chưa nhập)'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `hokhau`
--

INSERT INTO `hokhau` (`IDHoKhau`, `SoPhong`, `NgayDen`, `NgayDi`, `SDT`) VALUES
(1, 3, '2012-06-09', '0001-01-01', '03767546477'),
(2, 1, '2014-06-08', '0001-01-01', '048346473'),
(3, 2, '2020-11-02', '0001-01-01', '0932483294'),
(4, 4, '2017-02-11', '0001-01-01', '032848234'),
(5, 6, '2023-12-15', '0001-01-01', '0376535353'),
(6, 5, '2023-12-16', '2023-12-18', '038383833'),
(7, 7, '2023-12-16', '2023-12-26', '1234'),
(8, 7, '2023-12-08', '2023-12-26', '123'),
(9, 7, '2023-12-21', '2023-12-27', '0123456789');

--
-- Triggers `hokhau`
--
DELIMITER $$
CREATE TRIGGER `tuan` AFTER INSERT ON `hokhau` FOR EACH ROW BEGIN
    DECLARE idhokhau INT;
    SELECT NEW.IDHoKhau INTO idhokhau;
    INSERT INTO xe (IDHoKhau) VALUES (idhokhau);
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `khoanthu`
--

CREATE TABLE `khoanthu` (
  `IDKhoanThu` int(11) NOT NULL,
  `TenKT` varchar(40) NOT NULL DEFAULT '(Chưa điền)',
  `NgayBatDau` date NOT NULL DEFAULT '0001-01-01',
  `NgayKetThuc` date NOT NULL DEFAULT '0001-01-01',
  `TrongSoDienTich` float NOT NULL DEFAULT 0,
  `TrongSoSTV` float NOT NULL DEFAULT 0,
  `HangSo` float NOT NULL DEFAULT 0,
  `LoaiKhoanThu` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `khoanthu`
--

INSERT INTO `khoanthu` (`IDKhoanThu`, `TenKT`, `NgayBatDau`, `NgayKetThuc`, `TrongSoDienTich`, `TrongSoSTV`, `HangSo`, `LoaiKhoanThu`) VALUES
(1, 'gui xe thang 10', '2024-01-01', '2024-01-01', 1000, 500, 200, 'Tiền quản lý'),
(100, 'Quan ly thang 1', '2024-01-01', '2024-01-01', 1000, 2000, 3000, 'Tiền quản lý');

-- --------------------------------------------------------

--
-- Table structure for table `nhankhau`
--

CREATE TABLE `nhankhau` (
  `IDNhanKhau` int(11) NOT NULL,
  `IDHoKhau` int(11) NOT NULL,
  `QHvsChuHo` varchar(15) NOT NULL DEFAULT '(Chưa nhập)',
  `HoTen` varchar(20) NOT NULL DEFAULT '(chưa nhập)',
  `NgaySinh` date NOT NULL DEFAULT '0001-01-01',
  `CCCD` varchar(15) NOT NULL DEFAULT '(Chưa nhập)',
  `NgheNghiep` varchar(15) NOT NULL DEFAULT '(Chưa nhập)',
  `GioiTinh` varchar(15) NOT NULL DEFAULT '(Chưa nhập)',
  `DanToc` varchar(15) NOT NULL DEFAULT '(Chưa nhập)',
  `QueQuan` varchar(100) NOT NULL DEFAULT '(Chưa nhập)'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `nhankhau`
--

INSERT INTO `nhankhau` (`IDNhanKhau`, `IDHoKhau`, `QHvsChuHo`, `HoTen`, `NgaySinh`, `CCCD`, `NgheNghiep`, `GioiTinh`, `DanToc`, `QueQuan`) VALUES
(1, 1, 'Cháu chắt', 'Nguyễn Hoàng Lâm', '2003-02-11', '(Chưa nhập)', 'Sinh viên', 'Nam', 'Kinh', 'Đồng Hợp, Quỳ Hợp, Nghệ An, Việt Nam'),
(2, 1, 'Chủ hộ', 'Nguyễn Trọng Tuấn', '2003-02-04', '(Chưa nhập)', 'Quái vật', 'Nam', 'Kinh', '(Chưa nhập)'),
(3, 1, 'Vợ/Chồng', 'Nguyễn Minh Quang', '2003-04-14', '(Chưa nhập)', '(Chưa nhập)', '(Chưa nhập)', '(Chưa nhập)', '(Chưa nhập)'),
(4, 2, 'Chủ hộ', 'Đào Quốc Tuấn', '2003-01-01', '(Chưa nhập)', '(Chưa nhập)', '(Chưa nhập)', '(Chưa nhập)', '(Chưa nhập)'),
(5, 3, 'Bố mẹ', 'Vương Đình Minh', '2003-07-19', '(Chưa nhập)', '(Chưa nhập)', '(Chưa nhập)', '(Chưa nhập)', '(Chưa nhập)'),
(6, 4, 'Chủ hộ', 'Nguyễn Sỹ Trọng', '0001-01-01', '(Chưa nhập)', '(Chưa nhập)', '(Chưa nhập)', '(Chưa nhập)', '(Chưa nhập)'),
(7, 3, 'Cháu chắt', 'Nguyễn Mạnh Tuấn', '1977-12-23', '202320222021', 'Giảng viên', 'Nam', 'Kinh', '(Chưa nhập)'),
(8, 1, 'Bố mẹ', 'Greatest of all time', '1995-01-03', '1234', 'Ca sĩ', 'Nữ', 'Kinh', '(Chưa nhập)'),
(9, 1, 'Con cái', 'Nguyễn Minh A', '2023-12-14', '', 'Chưa cung cấp', 'Nam', 'Kinh', 'Thanh Nhàn, Hai Bà Trưng, Hà Nội, Việt Nam'),
(10, 5, 'Chủ hộ', 'Trịnh Văn Chiến', '0001-01-01', '1234567894554', '(Chưa điền)', 'Nam', 'Kinh', 'Việt Nam'),
(11, 6, 'Chủ hộ', 'Huỳnh Thị Thanh Bình', '0001-01-01', '23456789', '(Chưa điền)', 'Nữ', 'Kinh', 'Việt Nam'),
(12, 7, 'Chủ hộ', 'Nguyễn Thị Mỹ Bình', '0001-01-01', '12345678', 'Chủ hộ', 'Nam', 'Kinh', 'Việt Nam'),
(13, 8, 'Chủ hộ', 'A B C', '0001-01-01', '123', 'Chủ hộ', 'Nữ', 'Kinh', 'Việt Nam'),
(14, 3, 'Chủ hộ', 'Vương Đình Lâm', '2023-12-17', '', 'Chưa cung cấp', 'Nam', 'Kinh', 'Tân Kỳ, Tân Kỳ, Nghệ An, Việt Nam'),
(15, 3, 'Vợ/Chồng', 'Đỗ Đức Mạnh', '2003-01-01', '111111111111', 'Chưa cung cấp', 'Nam', 'Kinh', 'Nghĩa Thuận, Thái Hoà, Nghệ An, Việt Nam'),
(16, 4, 'Con cái', 'Nguyễn Tiến Thành', '1978-10-28', '1237562345', 'Giảng viên', 'Nam', 'Kinh', 'Nậm Chua, Nậm Pồ, Điện Biên, Việt Nam'),
(17, 9, 'Chủ hộ', 'Phùng Cảnh Châu', '0001-01-01', '12345123', '(Chưa nhập)', 'Nam', '(Chưa nhập)', '(Chưa nhập)'),
(18, 1, 'Khác', 'Nguyễn Đức Hiếu', '2003-10-03', '17248183838', 'Rapper', 'Nam', 'Mường', 'Avenue Victor Hugo, Paris, France');

-- --------------------------------------------------------

--
-- Table structure for table `phong`
--

CREATE TABLE `phong` (
  `SoPhong` int(11) NOT NULL,
  `DienTich` int(11) NOT NULL DEFAULT 0,
  `LoaiPhong` varchar(20) NOT NULL DEFAULT '(Chưa điền)'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `phong`
--

INSERT INTO `phong` (`SoPhong`, `DienTich`, `LoaiPhong`) VALUES
(1, 100, 'Cao cấp'),
(2, 20, 'Thường'),
(3, 20, 'Cao cấp'),
(4, 50, '(Chưa điền)'),
(5, 25, '(Chưa điền)'),
(6, 20, '(Chưa điền)'),
(7, 40, 'Cao cấp'),
(9, 40, 'Thường');

-- --------------------------------------------------------

--
-- Table structure for table `tamtru`
--

CREATE TABLE `tamtru` (
  `IDTamTru` int(11) NOT NULL,
  `IDHoKhau` int(11) NOT NULL,
  `HoTen` varchar(30) NOT NULL DEFAULT '(Chưa nhập)',
  `ThuongTru` varchar(100) NOT NULL DEFAULT '(Chưa nhập)',
  `NgayBatDau` date NOT NULL DEFAULT '0001-01-01',
  `NgayKetThuc` date NOT NULL DEFAULT '0001-01-01',
  `LyDo` varchar(100) NOT NULL DEFAULT '(Chưa nhập)',
  `NgaySinh` date NOT NULL DEFAULT '0001-01-01',
  `CCCD` varchar(15) NOT NULL DEFAULT '(Chưa nhập)',
  `GioiTinh` varchar(15) NOT NULL DEFAULT '(Chưa nhập)'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `tamtru`
--

INSERT INTO `tamtru` (`IDTamTru`, `IDHoKhau`, `HoTen`, `ThuongTru`, `NgayBatDau`, `NgayKetThuc`, `LyDo`, `NgaySinh`, `CCCD`, `GioiTinh`) VALUES
(1, 5, 'Hoàng Tiến Nguyên', '(Chưa nhập)', '2023-01-01', '2023-12-31', '(Chưa nhập)', '2004-01-01', '01324832', 'Nam'),
(3, 5, 'Trương Xuân Thông', 'Chiềng Hặc, Yên Châu, Sơn La, Việt Nam', '2023-12-26', '2024-04-18', 'Vui chơi giải trí là chính', '2004-05-16', '1028529583', 'Nam');

-- --------------------------------------------------------

--
-- Table structure for table `tamvang`
--

CREATE TABLE `tamvang` (
  `IDTamvang` int(11) NOT NULL,
  `IDNhanKhau` int(11) NOT NULL,
  `NgayBatDau` date NOT NULL DEFAULT '0001-01-01',
  `NgayKetThuc` date NOT NULL DEFAULT '0001-01-01',
  `LyDo` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `tamvang`
--

INSERT INTO `tamvang` (`IDTamvang`, `IDNhanKhau`, `NgayBatDau`, `NgayKetThuc`, `LyDo`) VALUES
(1, 15, '2023-12-23', '2024-02-11', 'Đi du học'),
(2, 17, '2023-12-25', '2023-12-25', 'Đi thăm bạn bè ở Cà Mau'),
(3, 16, '2023-12-20', '2024-12-20', 'Đi xuất khẩu lao động'),
(9, 12, '2023-12-26', '2023-12-26', 'Đi giảng dạy cho sinh viên đại học Công Nghiệp');

-- --------------------------------------------------------

--
-- Table structure for table `thuphi`
--

CREATE TABLE `thuphi` (
  `IDKhoanThu` int(11) NOT NULL,
  `IDHoKhau` int(11) NOT NULL,
  `SoTienPhaiDong` float NOT NULL,
  `TienDaDong` float NOT NULL,
  `NgayDong` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `username` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL DEFAULT '0',
  `SDT` varchar(10) NOT NULL DEFAULT '0',
  `Email` varchar(30) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`username`, `password`, `SDT`, `Email`) VALUES
('1', '1', '0', '0');

-- --------------------------------------------------------

--
-- Table structure for table `xe`
--

CREATE TABLE `xe` (
  `IDHoKhau` int(11) NOT NULL,
  `XeDap` int(11) NOT NULL DEFAULT 0,
  `XeMay` int(11) NOT NULL DEFAULT 0,
  `Oto` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `xe`
--

INSERT INTO `xe` (`IDHoKhau`, `XeDap`, `XeMay`, `Oto`) VALUES
(1, 0, 0, 0),
(2, 1, 5, 2),
(3, 0, 0, 0),
(4, 0, 0, 0),
(5, 0, 0, 0),
(6, 0, 0, 0),
(7, 0, 0, 0),
(8, 0, 0, 0),
(9, 0, 0, 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `hokhau`
--
ALTER TABLE `hokhau`
  ADD PRIMARY KEY (`IDHoKhau`),
  ADD KEY `fk_sophong` (`SoPhong`);

--
-- Indexes for table `khoanthu`
--
ALTER TABLE `khoanthu`
  ADD PRIMARY KEY (`IDKhoanThu`);

--
-- Indexes for table `nhankhau`
--
ALTER TABLE `nhankhau`
  ADD PRIMARY KEY (`IDNhanKhau`),
  ADD KEY `fk_idhk` (`IDHoKhau`);

--
-- Indexes for table `phong`
--
ALTER TABLE `phong`
  ADD PRIMARY KEY (`SoPhong`);

--
-- Indexes for table `tamtru`
--
ALTER TABLE `tamtru`
  ADD PRIMARY KEY (`IDTamTru`);

--
-- Indexes for table `tamvang`
--
ALTER TABLE `tamvang`
  ADD PRIMARY KEY (`IDTamvang`),
  ADD KEY `fk_idnktv` (`IDNhanKhau`);

--
-- Indexes for table `thuphi`
--
ALTER TABLE `thuphi`
  ADD PRIMARY KEY (`IDKhoanThu`,`IDHoKhau`),
  ADD KEY `fk_idnk` (`IDHoKhau`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`username`);

--
-- Indexes for table `xe`
--
ALTER TABLE `xe`
  ADD KEY `fk_idnkxe` (`IDHoKhau`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `hokhau`
--
ALTER TABLE `hokhau`
  MODIFY `IDHoKhau` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `nhankhau`
--
ALTER TABLE `nhankhau`
  MODIFY `IDNhanKhau` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT for table `tamtru`
--
ALTER TABLE `tamtru`
  MODIFY `IDTamTru` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `tamvang`
--
ALTER TABLE `tamvang`
  MODIFY `IDTamvang` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `hokhau`
--
ALTER TABLE `hokhau`
  ADD CONSTRAINT `fk_sophong` FOREIGN KEY (`SoPhong`) REFERENCES `phong` (`SoPhong`) ON DELETE NO ACTION ON UPDATE CASCADE;

--
-- Constraints for table `nhankhau`
--
ALTER TABLE `nhankhau`
  ADD CONSTRAINT `fk_idhk` FOREIGN KEY (`IDHoKhau`) REFERENCES `hokhau` (`IDHoKhau`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `tamvang`
--
ALTER TABLE `tamvang`
  ADD CONSTRAINT `fk_idnktv` FOREIGN KEY (`IDNhanKhau`) REFERENCES `nhankhau` (`IDNhanKhau`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `thuphi`
--
ALTER TABLE `thuphi`
  ADD CONSTRAINT `fk_idkt` FOREIGN KEY (`IDKhoanThu`) REFERENCES `khoanthu` (`IDKhoanThu`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_idnk` FOREIGN KEY (`IDHoKhau`) REFERENCES `hokhau` (`IDHoKhau`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
