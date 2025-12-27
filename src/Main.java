/*Saya Muhammad Rangga Nur Praditha dengan NIM 2400297 mengerjakan evaluasi Tugas Masa Depan
dalam mata kuliah Desain dan Pemrograman Berorientasi Objek untuk keberkahanNya maka saya tidak
melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin.*/

/**************************************************************
 * Filename    : Main.java
 * Programmer  : Muhammad Rangga Nur Praditha
 * Date        : 2025-12-26
 * Email       : muhrangganp.05@upi.edu
 * Deskripsi   : Titik awal (Entry Point) utama untuk menjalankan
 * seluruh aplikasi Space Invaders
 **************************************************************/
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import view.MenuView;

/**
 * Kelas Main berfungsi untuk memulai aplikasi
 */
public class Main {
    /**
     * Metode utama yang dieksekusi pertama kali saat aplikasi dijalankan
     */
    public static void main(String[] args) {
        // Menjalankan GUI di Event Dispatch Thread (EDT) agar stabil
        SwingUtilities.invokeLater(() -> {
            try {
                // Instansiasi dan menampilkan layar Menu Utama
                MenuView menu = new MenuView();
                menu.setVisible(true);
            } catch (Exception e) {
                // Menampilkan pesan error jika aplikasi gagal terbuka
                JOptionPane.showMessageDialog(null, "Gagal menjalankan game: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }
}