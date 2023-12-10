-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th12 10, 2023 lúc 03:42 PM
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
-- Cơ sở dữ liệu: `quan_ly_khoan_thu`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `chu_ho`
--

CREATE TABLE `chu_ho` (
  `MaHo` int(11) NOT NULL,
  `IDChuHo` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

--
-- Đang đổ dữ liệu cho bảng `chu_ho`
--

INSERT INTO `chu_ho` (`MaHo`, `IDChuHo`) VALUES
(2, 2),
(3, 3);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `ho_khau`
--

CREATE TABLE `ho_khau` (
  `IDHoKhau` int(11) NOT NULL,
  `IDChuHo` int(11) NOT NULL,
  `SoPhong` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

--
-- Đang đổ dữ liệu cho bảng `ho_khau`
--

INSERT INTO `ho_khau` (`IDHoKhau`, `IDChuHo`, `SoPhong`) VALUES
(2, 3, 'Phúc thành'),
(3, 1, 'Kim');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `khoan_thu`
--

CREATE TABLE `khoan_thu` (
  `MaKhoanThu` int(11) NOT NULL,
  `TenKhoanThu` varchar(100) NOT NULL,
  `SoTien` double NOT NULL,
  `LoaiKhoanThu` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

--
-- Đang đổ dữ liệu cho bảng `khoan_thu`
--

INSERT INTO `khoan_thu` (`MaKhoanThu`, `TenKhoanThu`, `SoTien`, `LoaiKhoanThu`) VALUES
(1, '12', 10000, 0),
(2, 'nước', 1000.12, 1),
(3, 'Tiền Điện', 200000, 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `nhan_khau`
--

CREATE TABLE `nhan_khau` (
  `ID` int(11) NOT NULL,
  `QHvsChuHo` varchar(20) DEFAULT 'Khác',
  `IDChuHo` int(11) NOT NULL DEFAULT 0,
  `SoPhong` int(11) NOT NULL DEFAULT 0,
  `CMND` varchar(20) DEFAULT NULL,
  `Ten` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `Tuoi` int(11) NOT NULL,
  `SDT` varchar(15) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

--
-- Đang đổ dữ liệu cho bảng `nhan_khau`
--

INSERT INTO `nhan_khau` (`ID`, `QHvsChuHo`, `IDChuHo`, `SoPhong`, `CMND`, `Ten`, `Tuoi`, `SDT`) VALUES
(2, 'Chủ hộ', 2, 1, '122330541', 'Nguyễn Minh Quang', 20, '0377016054'),
(3, 'Chủ hộ', 3, 2, '122440651', 'Lâm', 20, '0945123567'),
(4, 'Khác', 2, 1, '20210517', 'Nguyễn Trọng Tuấn', 20, '0916836437');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `nop_tien`
--

CREATE TABLE `nop_tien` (
  `IDNopTien` int(11) NOT NULL,
  `MaKhoanThu` int(11) NOT NULL,
  `NgayThu` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

--
-- Đang đổ dữ liệu cho bảng `nop_tien`
--

INSERT INTO `nop_tien` (`IDNopTien`, `MaKhoanThu`, `NgayThu`) VALUES
(2, 2, '2020-12-16'),
(3, 1, '2020-12-16'),
(3, 2, '2020-12-16');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `quan_he`
--

CREATE TABLE `quan_he` (
  `MaHo` int(11) NOT NULL,
  `IDThanhVien` int(11) NOT NULL,
  `QuanHe` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

--
-- Đang đổ dữ liệu cho bảng `quan_he`
--

INSERT INTO `quan_he` (`MaHo`, `IDThanhVien`, `QuanHe`) VALUES
(2, 2, ''),
(2, 4, 'Bà con'),
(3, 3, 'Là chủ hộ');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `users`
--

CREATE TABLE `users` (
  `ID` int(11) NOT NULL,
  `username` varchar(30) NOT NULL,
  `passwd` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

--
-- Đang đổ dữ liệu cho bảng `users`
--

INSERT INTO `users` (`ID`, `username`, `passwd`) VALUES
(1, 'admin', '1');

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `chu_ho`
--
ALTER TABLE `chu_ho`
  ADD PRIMARY KEY (`MaHo`,`IDChuHo`),
  ADD KEY `chu_ho_2` (`IDChuHo`);

--
-- Chỉ mục cho bảng `ho_khau`
--
ALTER TABLE `ho_khau`
  ADD PRIMARY KEY (`IDHoKhau`);

--
-- Chỉ mục cho bảng `khoan_thu`
--
ALTER TABLE `khoan_thu`
  ADD PRIMARY KEY (`MaKhoanThu`);

--
-- Chỉ mục cho bảng `nhan_khau`
--
ALTER TABLE `nhan_khau`
  ADD PRIMARY KEY (`ID`);

--
-- Chỉ mục cho bảng `nop_tien`
--
ALTER TABLE `nop_tien`
  ADD PRIMARY KEY (`IDNopTien`,`MaKhoanThu`),
  ADD KEY `nop_tien_2` (`MaKhoanThu`);

--
-- Chỉ mục cho bảng `quan_he`
--
ALTER TABLE `quan_he`
  ADD PRIMARY KEY (`MaHo`,`IDThanhVien`),
  ADD KEY `quan_he_2` (`IDThanhVien`);

--
-- Chỉ mục cho bảng `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`ID`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `ho_khau`
--
ALTER TABLE `ho_khau`
  MODIFY `IDHoKhau` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT cho bảng `khoan_thu`
--
ALTER TABLE `khoan_thu`
  MODIFY `MaKhoanThu` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT cho bảng `nhan_khau`
--
ALTER TABLE `nhan_khau`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT cho bảng `users`
--
ALTER TABLE `users`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `chu_ho`
--
ALTER TABLE `chu_ho`
  ADD CONSTRAINT `chu_ho_1` FOREIGN KEY (`MaHo`) REFERENCES `ho_khau` (`IDHoKhau`),
  ADD CONSTRAINT `chu_ho_2` FOREIGN KEY (`IDChuHo`) REFERENCES `nhan_khau` (`ID`);

--
-- Các ràng buộc cho bảng `nop_tien`
--
ALTER TABLE `nop_tien`
  ADD CONSTRAINT `nop_tien_1` FOREIGN KEY (`IDNopTien`) REFERENCES `nhan_khau` (`ID`),
  ADD CONSTRAINT `nop_tien_2` FOREIGN KEY (`MaKhoanThu`) REFERENCES `khoan_thu` (`MaKhoanThu`);

--
-- Các ràng buộc cho bảng `quan_he`
--
ALTER TABLE `quan_he`
  ADD CONSTRAINT `quan_he_1` FOREIGN KEY (`MaHo`) REFERENCES `ho_khau` (`IDHoKhau`),
  ADD CONSTRAINT `quan_he_2` FOREIGN KEY (`IDThanhVien`) REFERENCES `nhan_khau` (`ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
