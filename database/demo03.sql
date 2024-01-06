-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th1 06, 2024 lúc 08:38 AM
-- Phiên bản máy phục vụ: 10.4.28-MariaDB
-- Phiên bản PHP: 8.0.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `demo03`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `hokhau`
--

CREATE TABLE `hokhau` (
  `IDHoKhau` int(11) NOT NULL,
  `SoPhong` int(11) NOT NULL DEFAULT 0,
  `NgayDen` date NOT NULL DEFAULT '0001-01-01',
  `NgayDi` date NOT NULL DEFAULT '0001-01-01',
  `SDT` varchar(15) NOT NULL DEFAULT '(Chưa nhập)'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `hokhau`
--

INSERT INTO `hokhau` (`IDHoKhau`, `SoPhong`, `NgayDen`, `NgayDi`, `SDT`) VALUES
(1, 6, '2012-06-09', '0001-01-01', '03767546477'),
(2, 1, '2014-06-08', '0001-01-01', '048346473'),
(3, 2, '2020-11-02', '0001-01-01', '0932483294'),
(4, 4, '2017-02-11', '0001-01-01', '032848234'),
(5, 6, '2023-12-15', '2023-12-27', '0376535353'),
(6, 5, '2023-12-16', '2023-12-18', '038383833'),
(7, 7, '2023-12-16', '2023-12-26', '1234'),
(8, 7, '2023-12-08', '2023-12-26', '123'),
(9, 7, '2023-12-21', '2023-12-27', '0123456789'),
(10, 5, '2024-01-01', '0001-01-01', '0916836438'),
(11, 7, '2024-01-03', '2024-01-03', '3456'),
(12, 7, '2024-01-03', '2024-01-03', '0916835437'),
(13, 7, '2024-01-03', '2024-01-06', '0394828743');

--
-- Bẫy `hokhau`
--
DELIMITER $$
CREATE TRIGGER `after_delete_ho_khau` AFTER DELETE ON `hokhau` FOR EACH ROW BEGIN
    DELETE FROM xe WHERE IDHoKhau = OLD.IDHoKhau;
END
$$
DELIMITER ;
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
-- Cấu trúc bảng cho bảng `khoanthu`
--

CREATE TABLE `khoanthu` (
  `IDKhoanThu` int(11) NOT NULL,
  `TenKT` varchar(40) NOT NULL DEFAULT '(Chưa điền)',
  `NgayBatDau` date NOT NULL DEFAULT '0001-01-01',
  `NgayKetThuc` date NOT NULL DEFAULT '0001-01-01',
  `TrongSoDienTich` float NOT NULL DEFAULT 0,
  `TrongSoSTV` float NOT NULL DEFAULT 0,
  `HangSo` float NOT NULL DEFAULT 0,
  `LoaiKhoanThu` varchar(30) NOT NULL DEFAULT 'Không'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `khoanthu`
--

INSERT INTO `khoanthu` (`IDKhoanThu`, `TenKT`, `NgayBatDau`, `NgayKetThuc`, `TrongSoDienTich`, `TrongSoSTV`, `HangSo`, `LoaiKhoanThu`) VALUES
(5, 'Gửi xe tháng 11/2023', '2023-11-01', '2023-11-30', 500000, 200000, 100000, 'Tiền giữ xe'),
(6, 'Gửi xe tháng 12/2023', '2023-12-01', '2023-12-31', 500000, 200000, 100000, 'Tiền giữ xe'),
(8, 'Dịch vụ tháng 10/2023', '2023-10-01', '2023-10-21', 5000, 10000, 0, 'Tiền quản lý'),
(9, 'Dịch vụ tháng 11/2023', '2023-11-01', '2023-11-30', 5000, 10000, 0, 'Tiền quản lý'),
(10, 'Dịch vụ tháng 12/2023', '2023-12-01', '2023-12-31', 5000, 10000, 0, 'Tiền quản lý'),
(11, 'Nước tháng 10/2023', '2023-10-01', '2023-10-31', 20000, 0, 0, 'Tiền nước'),
(12, 'Nước tháng 11/2023', '2023-11-01', '2023-11-30', 20000, 0, 0, 'Tiền nước'),
(13, 'Nước tháng 12/2023', '2023-12-01', '2023-12-31', 20000, 0, 0, 'Tiền nước'),
(14, 'Tết vùng cao 2024', '2024-01-01', '2024-01-15', 0, 0, 0, 'Tiền khác'),
(15, 'Liên hoan khu chung cư 2024', '2024-01-15', '2024-01-31', 200000, 0, 0, 'Tiền khác'),
(16, 'Gửi xe tháng 9/2023', '2023-09-01', '2023-09-30', 500000, 200000, 100000, 'Tiền giữ xe'),
(17, 'Dịch vụ tháng 8/2023', '2023-08-01', '2023-08-31', 20000, 10000, 0, 'Tiền quản lý'),
(18, 'Gửi xe tháng 7/2023', '2023-07-01', '2023-07-31', 400000, 150000, 50000, 'Tiền giữ xe'),
(19, 'Nước tháng 12/2023', '2023-12-01', '2023-12-31', 20000, 0, 0, 'Tiền nước');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `nhankhau`
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
-- Đang đổ dữ liệu cho bảng `nhankhau`
--

INSERT INTO `nhankhau` (`IDNhanKhau`, `IDHoKhau`, `QHvsChuHo`, `HoTen`, `NgaySinh`, `CCCD`, `NgheNghiep`, `GioiTinh`, `DanToc`, `QueQuan`) VALUES
(1, 1, 'Cháu chắt', 'Nguyễn Hoàng Lâm', '2003-02-11', '(Chưa nhập)', 'Sinh viên', 'Nam', 'Kinh', 'Đồng Hợp, Quỳ Hợp, Nghệ An, Việt Nam'),
(2, 1, 'Chủ hộ', 'Nguyễn Trọng Tuấn', '2003-02-04', '(Chưa nhập)', 'Sinh viên', 'Nam', 'Kinh', '(Chưa nhập)'),
(3, 1, 'Vợ/Chồng', 'Nguyễn Minh Quang', '2003-04-14', '(Chưa nhập)', '(Chưa nhập)', '(Chưa nhập)', '(Chưa nhập)', '(Chưa nhập)'),
(4, 2, 'Chủ hộ', 'Đào Quốc Tuấn', '2003-01-01', '(Chưa nhập)', '(Chưa nhập)', '(Chưa nhập)', '(Chưa nhập)', '(Chưa nhập)'),
(5, 3, 'Bố mẹ', 'Vương Đình Minh', '2003-07-19', '(Chưa nhập)', '(Chưa nhập)', 'Nữ', 'Kinh', '(Chưa nhập)'),
(6, 4, 'Chủ hộ', 'Nguyễn Sỹ Trọng', '1967-07-19', '(Chưa nhập)', '(Chưa nhập)', 'Nữ', '(Chưa nhập)', '(Chưa nhập)'),
(7, 3, 'Bố mẹ', 'Nguyễn Mạnh Tuấn', '1990-12-23', '202320222021', 'Minh tinh', 'Nam', 'Kinh', '(Chưa nhập)'),
(8, 1, 'Bố mẹ', 'Trần Quang Trường', '1995-01-03', '1234827382', 'Bán phở', 'Nữ', 'Kinh', '(Chưa nhập)'),
(9, 1, 'Con cái', 'Nguyễn Minh A', '2023-12-14', '(Chưa nhập)', '(Chưa nhập)', 'Nam', 'Kinh', 'Thanh Nhàn, Hai Bà Trưng, Hà Nội, Việt Nam'),
(10, 5, 'Chủ hộ', 'Trịnh Văn Chiến', '0001-01-01', '1234567894554', '(Chưa điền)', 'Nam', 'Kinh', 'Việt Nam'),
(11, 6, 'Chủ hộ', 'Huỳnh Thị Thanh Bình', '0001-01-01', '23456789', '(Chưa điền)', 'Nữ', 'Kinh', 'Việt Nam'),
(12, 7, 'Chủ hộ', 'Nguyễn Thị Mỹ Bình', '0001-01-01', '12345678', 'Chủ hộ', 'Nam', 'Kinh', 'Việt Nam'),
(13, 8, 'Chủ hộ', 'A B C', '0001-01-01', '123', 'Chủ hộ', 'Nữ', 'Kinh', 'Việt Nam'),
(14, 3, 'Chủ hộ', 'Vương Đình Lâm', '2023-12-17', '(Chưa nhập)', 'Công nhân', 'Nam', 'Kinh', 'Tân Kỳ, Tân Kỳ, Nghệ An, Việt Nam'),
(15, 3, 'Vợ/Chồng', 'Đỗ Đức Mạnh', '2003-01-01', '111111111111', '(Chưa điền)', 'Nam', 'Kinh', 'Nghĩa Thuận, Thái Hoà, Nghệ An, Việt Nam'),
(16, 4, 'Con cái', 'Nguyễn Tiến Thành', '1978-10-28', '1237562345', 'Giảng viên', 'Nam', 'Kinh', 'Nậm Chua, Nậm Pồ, Điện Biên, Việt Nam'),
(17, 9, 'Chủ hộ', 'Phùng Cảnh Châu', '0001-01-01', '12345123', '(Chưa nhập)', 'Nam', '(Chưa nhập)', '(Chưa nhập)'),
(18, 1, 'Khác', 'Nguyễn Đức Hiếu', '2003-10-03', '17248183838', 'Rapper', 'Nam', 'Mường', 'Avenue Victor Hugo, Paris, France'),
(19, 4, 'Con cái', 'Trương Ngọc Ánh', '2011-09-05', '029129', 'Học sinh', 'Nữ', 'Kinh', 'Mương Chanh, Mai Sơn, Sơn La, Việt Nam'),
(20, 3, 'Con cái', 'Cấn Nhật Linh', '2007-09-18', '(Chưa nhập)', 'Học sinh', 'Nam', 'Kinh', 'Vạn Phúc, Hà Đông, Hà Nội, Việt Nam'),
(21, 2, 'Ông bà', 'Nguyễn Viết Hà', '2004-10-21', '0020123', 'Giảng Viên', 'Nam', 'Kinh', 'Minh Dân, Hàm Yên, Tuyên Quang, Việt Nam'),
(22, 2, 'Con cái', 'Trần Thị Quỳnh Trang', '2014-03-28', '0020123', 'Học sinh', 'Nữ', 'Kinh', 'Trí Quả, Thuận Thành, Bắc Ninh, Việt Nam'),
(23, 10, 'Chủ hộ', 'Nguyễn Thiên Phúc', '1999-01-01', '00200913', '(Chưa nhập)', 'Nam', '(Chưa nhập)', '(Chưa nhập)'),
(24, 10, 'Bố mẹ', 'Lê Văn Quang Trung', '1948-02-01', '00300393', 'Nghỉ hưu', 'Nam', 'Kinh', 'Vàng Ma Chải, Phong Thổ, Lai Châu, Việt Nam'),
(25, 11, 'Chủ hộ', 'dfghj', '2001-01-01', '34567', '(Chưa nhập)', 'Nam', '(Chưa nhập)', '(Chưa nhập)'),
(26, 12, 'Chủ hộ', 'Vương Hoàng Minh', '2003-01-26', '817288374', '(Chưa nhập)', 'Nam', 'Kinh', 'Mường Lựm, Yên Châu, Sơn La, Việt Nam'),
(27, 12, 'Bố mẹ', 'Vương Hoàng Nhật', '1990-12-01', '019839284', 'Nghỉ hưu', 'Nữ', 'Kinh', 'Phình Hồ, Trạm Tấu, Yên Bái, Việt Nam'),
(28, 4, 'Bố mẹ', 'Nguyễn Phú Sỹ', '2023-03-29', '(Chưa nhập)', 'Chưa cung cấp', 'Nữ', 'Kinh', '(Chưa nhập)'),
(29, 13, 'Chủ hộ', 'Dương Phương Thảo', '2003-11-11', '1800', 'Sinh Viên', 'Nữ', 'Kinh', 'Xuân Tân, Xuân Trường, Nam Định, Việt Nam'),
(30, 13, 'Con cái', 'Dương Phương Lâm', '2010-12-20', '(Chưa nhập)', 'Học sinh', 'Nữ', 'Kinh', 'Xuân Thủy, Xuân Trường, Nam Định, Việt Nam');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `phong`
--

CREATE TABLE `phong` (
  `SoPhong` int(11) NOT NULL,
  `DienTich` int(11) NOT NULL DEFAULT 0,
  `LoaiPhong` varchar(20) NOT NULL DEFAULT '(Chưa điền)'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `phong`
--

INSERT INTO `phong` (`SoPhong`, `DienTich`, `LoaiPhong`) VALUES
(1, 100, 'Cao cấp'),
(2, 20, 'Thường'),
(3, 20, 'Cao cấp'),
(4, 50, 'Thường'),
(5, 25, 'Cao cấp'),
(6, 20, 'Cao cấp'),
(7, 40, 'Cao cấp'),
(9, 40, 'Thường');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tamtru`
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
-- Đang đổ dữ liệu cho bảng `tamtru`
--

INSERT INTO `tamtru` (`IDTamTru`, `IDHoKhau`, `HoTen`, `ThuongTru`, `NgayBatDau`, `NgayKetThuc`, `LyDo`, `NgaySinh`, `CCCD`, `GioiTinh`) VALUES
(1, 5, 'Hoàng Tiến Nguyên', '(Chưa nhập)', '2023-01-01', '2023-12-31', '(Chưa nhập)', '2004-01-01', '01324832', 'Nam'),
(3, 5, 'Trương Xuân Thông', 'Chiềng Hặc, Yên Châu, Sơn La, Việt Nam', '2023-12-26', '2024-04-18', 'Vui chơi giải trí là chính', '2004-05-16', '1028529583', 'Nam'),
(5, 13, 'Dương Phương Quang', 'Xuân Tân, Xuân Trường, Nam Định, Việt Nam', '2024-01-03', '2024-02-03', 'Lên chơi', '1979-07-12', '234563', 'Nữ');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tamvang`
--

CREATE TABLE `tamvang` (
  `IDTamvang` int(11) NOT NULL,
  `IDNhanKhau` int(11) NOT NULL,
  `NgayBatDau` date NOT NULL DEFAULT '0001-01-01',
  `NgayKetThuc` date NOT NULL DEFAULT '0001-01-01',
  `LyDo` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `tamvang`
--

INSERT INTO `tamvang` (`IDTamvang`, `IDNhanKhau`, `NgayBatDau`, `NgayKetThuc`, `LyDo`) VALUES
(1, 15, '2023-12-23', '2024-02-11', 'Đi du học'),
(2, 17, '2023-12-25', '2023-12-25', 'Đi thăm bạn bè ở Cà Mau'),
(3, 16, '2023-12-20', '2024-12-20', 'Đi xuất khẩu lao động'),
(9, 12, '2023-12-26', '2023-12-26', 'Đi giảng dạy cho sinh viên đại học Công Nghiệp'),
(11, 26, '2023-01-03', '2024-01-01', 'Đi du học'),
(12, 30, '2024-01-03', '2024-01-03', 'Đi du lịch với lớp');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `thuphi`
--

CREATE TABLE `thuphi` (
  `IDKhoanThu` int(11) NOT NULL,
  `IDHoKhau` int(11) NOT NULL,
  `SoTienPhaiDong` float NOT NULL,
  `TienDaDong` float NOT NULL,
  `NgayDong` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `thuphi`
--

INSERT INTO `thuphi` (`IDKhoanThu`, `IDHoKhau`, `SoTienPhaiDong`, `TienDaDong`, `NgayDong`) VALUES
(5, 1, 600000, 600000, '2023-10-10'),
(5, 2, 2100000, 2100000, '2023-11-14'),
(5, 3, 200000, 200000, '2023-12-12'),
(6, 2, 2100000, 2100000, '2023-11-14'),
(6, 3, 200000, 200000, '2023-12-12'),
(6, 4, 1600000, 1600000, '2023-10-27'),
(8, 1, 100000, 100000, '2023-10-10'),
(8, 2, 500000, 500000, '2023-11-14'),
(8, 3, 200000, 200000, '2023-12-12'),
(9, 1, 100000, 100000, '2023-10-10'),
(9, 2, 500000, 500000, '2023-11-14'),
(9, 3, 200000, 200000, '2023-12-12'),
(10, 1, 100000, 100000, '2023-10-10'),
(10, 2, 500000, 500000, '2023-11-14'),
(10, 3, 200000, 200000, '2023-12-12'),
(16, 1, 600000, 600000, '2023-05-16'),
(16, 2, 2100000, 2100000, '2023-09-19'),
(16, 3, 200000, 200000, '2023-07-19'),
(16, 4, 1600000, 1600000, '2023-07-20'),
(17, 1, 400000, 400000, '2023-08-24'),
(17, 2, 2000000, 2000000, '2023-06-20'),
(17, 3, 200000, 200000, '2023-08-16'),
(17, 4, 500000, 500000, '2023-08-31'),
(18, 1, 450000, 450000, '2023-07-07'),
(18, 2, 1650000, 1650000, '2023-07-12'),
(18, 3, 100000, 100000, '2023-07-26'),
(18, 4, 1250000, 1250000, '2023-07-18'),
(19, 10, 40000, 400000, '2023-12-22');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `users`
--

CREATE TABLE `users` (
  `username` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL DEFAULT '0',
  `SDT` varchar(10) NOT NULL DEFAULT '0',
  `Email` varchar(30) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `users`
--

INSERT INTO `users` (`username`, `password`, `SDT`, `Email`) VALUES
('1', '12', '0', 'Nguyễn Hoàng Lâm');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `xe`
--

CREATE TABLE `xe` (
  `IDHoKhau` int(11) NOT NULL,
  `XeDap` int(11) NOT NULL DEFAULT 0,
  `XeMay` int(11) NOT NULL DEFAULT 0,
  `Oto` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `xe`
--

INSERT INTO `xe` (`IDHoKhau`, `XeDap`, `XeMay`, `Oto`) VALUES
(1, 1, 0, 1),
(2, 0, 3, 3),
(3, 2, 0, 0),
(4, 0, 3, 2),
(5, 0, 0, 0),
(6, 0, 0, 0),
(7, 0, 0, 0),
(8, 0, 0, 0),
(9, 0, 0, 0),
(10, 0, 0, 0),
(11, 0, 0, 0),
(12, 0, 0, 0),
(13, 0, 0, 0);

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `hokhau`
--
ALTER TABLE `hokhau`
  ADD PRIMARY KEY (`IDHoKhau`),
  ADD KEY `fk_sophong` (`SoPhong`);

--
-- Chỉ mục cho bảng `khoanthu`
--
ALTER TABLE `khoanthu`
  ADD PRIMARY KEY (`IDKhoanThu`);

--
-- Chỉ mục cho bảng `nhankhau`
--
ALTER TABLE `nhankhau`
  ADD PRIMARY KEY (`IDNhanKhau`),
  ADD KEY `fk_idhk` (`IDHoKhau`);

--
-- Chỉ mục cho bảng `phong`
--
ALTER TABLE `phong`
  ADD PRIMARY KEY (`SoPhong`);

--
-- Chỉ mục cho bảng `tamtru`
--
ALTER TABLE `tamtru`
  ADD PRIMARY KEY (`IDTamTru`);

--
-- Chỉ mục cho bảng `tamvang`
--
ALTER TABLE `tamvang`
  ADD PRIMARY KEY (`IDTamvang`),
  ADD KEY `fk_idnktv` (`IDNhanKhau`);

--
-- Chỉ mục cho bảng `thuphi`
--
ALTER TABLE `thuphi`
  ADD PRIMARY KEY (`IDKhoanThu`,`IDHoKhau`),
  ADD KEY `fk_idnk` (`IDHoKhau`);

--
-- Chỉ mục cho bảng `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`username`);

--
-- Chỉ mục cho bảng `xe`
--
ALTER TABLE `xe`
  ADD KEY `fk_idnkxe` (`IDHoKhau`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `hokhau`
--
ALTER TABLE `hokhau`
  MODIFY `IDHoKhau` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT cho bảng `khoanthu`
--
ALTER TABLE `khoanthu`
  MODIFY `IDKhoanThu` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT cho bảng `nhankhau`
--
ALTER TABLE `nhankhau`
  MODIFY `IDNhanKhau` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- AUTO_INCREMENT cho bảng `tamtru`
--
ALTER TABLE `tamtru`
  MODIFY `IDTamTru` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT cho bảng `tamvang`
--
ALTER TABLE `tamvang`
  MODIFY `IDTamvang` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `hokhau`
--
ALTER TABLE `hokhau`
  ADD CONSTRAINT `fk_sophong` FOREIGN KEY (`SoPhong`) REFERENCES `phong` (`SoPhong`) ON DELETE NO ACTION ON UPDATE CASCADE;

--
-- Các ràng buộc cho bảng `nhankhau`
--
ALTER TABLE `nhankhau`
  ADD CONSTRAINT `fk_idhk` FOREIGN KEY (`IDHoKhau`) REFERENCES `hokhau` (`IDHoKhau`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Các ràng buộc cho bảng `tamvang`
--
ALTER TABLE `tamvang`
  ADD CONSTRAINT `fk_idnktv` FOREIGN KEY (`IDNhanKhau`) REFERENCES `nhankhau` (`IDNhanKhau`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Các ràng buộc cho bảng `thuphi`
--
ALTER TABLE `thuphi`
  ADD CONSTRAINT `fk_idkt` FOREIGN KEY (`IDKhoanThu`) REFERENCES `khoanthu` (`IDKhoanThu`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_idnk` FOREIGN KEY (`IDHoKhau`) REFERENCES `hokhau` (`IDHoKhau`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
