-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 27 Des 2025 pada 04.35
-- Versi server: 10.4.32-MariaDB
-- Versi PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_game`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `tbenefit`
--

CREATE TABLE `tbenefit` (
  `username` varchar(50) NOT NULL,
  `skor` int(11) DEFAULT 0,
  `peluru_meleset` int(11) DEFAULT 0,
  `sisa_peluru` int(11) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `tbenefit`
--

INSERT INTO `tbenefit` (`username`, `skor`, `peluru_meleset`, `sisa_peluru`) VALUES
('Aku', 11580, 2664, 1687),
('Gue', 6200, 184, 22),
('Kamu', 700, 41, 25),
('Kau', 500, 14, 3),
('Rangga', 130, 50, 26),
('Saya', 780, 365, 131),
('Siapa', 3900, 97, 1);

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `tbenefit`
--
ALTER TABLE `tbenefit`
  ADD PRIMARY KEY (`username`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
