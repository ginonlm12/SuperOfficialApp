-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th12 26, 2023 lúc 05:29 PM
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
-- Cơ sở dữ liệu: `quanlychungcu`
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
(1, 3, '2012-06-09', '0001-01-01', '03767546477'),
(2, 1, '2014-06-08', '0001-01-01', '048346473'),
(3, 2, '2020-11-02', '0001-01-01', '0932483294'),
(4, 4, '2017-02-11', '0001-01-01', '032848234'),
(5, 6, '2023-12-15', '0001-01-01', '0376535353'),
(6, 5, '2023-12-16', '2023-12-18', '038383833'),
(7, 7, '2023-12-16', '2023-12-26', '1234'),
(8, 7, '2023-12-08', '2023-12-26', '123'),
(9, 7, '2023-12-21', '0001-01-01', '0123456789');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `khoanthu`
--

CREATE TABLE `khoanthu` (
  `IDKhoanThu` int(11) NOT NULL,
  `TenKT` varchar(40) NOT NULL DEFAULT '(Chưa điền)',
  `NgayBatDau` date NOT NULL DEFAULT '0001-01-01',
  `NgayKetThuc` date NOT NULL DEFAULT '0001-01-01',
  `TrongSoDienTich` int(11) NOT NULL DEFAULT 0,
  `TrongSoSTV` int(11) NOT NULL DEFAULT 0,
  `HangSo` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

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
(4, 50, '(Chưa điền)'),
(5, 25, '(Chưa điền)'),
(6, 20, '(Chưa điền)'),
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
(3, 5, 'Trương Xuân Thông', 'Chiềng Hặc, Yên Châu, Sơn La, Việt Nam', '2023-12-26', '2024-04-12', 'Vui chơi giải trí là chính', '2004-05-16', '1028529583', 'Nam');

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
(9, 12, '2023-12-26', '2023-12-26', 'Đi giảng dạy cho sinh viên đại học Công Nghiệp');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `thuphi`
--

CREATE TABLE `thuphi` (
  `IDKhoanThu` int(11) NOT NULL,
  `IDHoKhau` int(11) NOT NULL,
  `TienDaDong` int(11) NOT NULL,
  `NgayDong` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

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
('1', '1', '0', '0');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `xe`
--

CREATE TABLE `xe` (
  `IDXe` int(11) NOT NULL,
  `TenXe` varchar(30) NOT NULL DEFAULT '(Chưa điền)',
  `LoaiXe` varchar(30) NOT NULL DEFAULT 'Ô tô',
  `IDNhanKhau` int(11) NOT NULL,
  `BienSo` varchar(15) NOT NULL DEFAULT '(Chưa điền)'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

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
  ADD PRIMARY KEY (`IDXe`),
  ADD KEY `fk_idnkxe` (`IDNhanKhau`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `hokhau`
--
ALTER TABLE `hokhau`
  MODIFY `IDHoKhau` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT cho bảng `nhankhau`
--
ALTER TABLE `nhankhau`
  MODIFY `IDNhanKhau` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT cho bảng `tamtru`
--
ALTER TABLE `tamtru`
  MODIFY `IDTamTru` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT cho bảng `tamvang`
--
ALTER TABLE `tamvang`
  MODIFY `IDTamvang` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

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
  ADD CONSTRAINT `fk_idkt` FOREIGN KEY (`IDHoKhau`) REFERENCES `khoanthu` (`IDKhoanThu`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_idnk` FOREIGN KEY (`IDHoKhau`) REFERENCES `hokhau` (`IDHoKhau`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Các ràng buộc cho bảng `xe`
--
ALTER TABLE `xe`
  ADD CONSTRAINT `fk_idnkxe` FOREIGN KEY (`IDNhanKhau`) REFERENCES `nhankhau` (`IDNhanKhau`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
