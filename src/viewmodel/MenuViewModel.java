/**************************************************************
 * Filename    : MenuViewModel.java
 * Programmer  : Muhammad Rangga Nur Praditha
 * Date        : 2025-12-26
 * Email       : muhrangganp.05@upi.edu
 * Deskripsi   : ViewModel untuk mengelola logika pada layar Menu,
 *  * termasuk validasi player dan data tabel Highscore
 **************************************************************/

package viewmodel;

import java.util.List;

import javax.swing.table.DefaultTableModel;

import model.entity.Benefit;
import model.repository.BenefitRepository;

/**
 * MenuViewModel menhubungkan data dari database (Repository)
 * untuk ditampilkan pada komponen GUI di MenuView.
 */
public class MenuViewModel {
    private BenefitRepository repository;

    /**
     * Konstruktor: Menyiapkan koneksi ke repository data benefit.
     */
    public MenuViewModel() {
        try {
            this.repository = new BenefitRepository();
        } catch (Exception e) {
            System.out.println("Gagal inisialisasi repository: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Mengambil data skor dari repository dan mengubahnya menjadi
     * model tabel yang bisa langsung digunakan oleh JTable.
     * @return DefaultTableModel model tabel berisi data Highscore
     */
    public DefaultTableModel getTableData() {
        // Mendefinisikan judul kolom tabel
        String[] columnNames = {"Username", "Skor", "Peluru Meleset", "Sisa Peluru"};
        // Inisialisasi model tabel kosong
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        // Ambil list data benefit (skor) dari database
        List<Benefit> data = repository.getAllBenefits();

        // Memasukkan setiap data benefit ke dalam baris tabel
        for (Benefit b : data) {
            Object[] row = {b.getUsername(), b.getSkor(), b.getPeluruMeleset(), b.getSisaPeluru()};
            model.addRow(row);
        }
        return model;
    }

    /**
     * Logika saat tombol Play ditekan: Validasi username dan pendaftaran user baru.
     * @param username Input nama dari pemain.
     */
    public void handlePlay(String username) {
        // Validasi agar input tidak kosong
        if (username.trim().isEmpty()) {
            return;
        }

        // Jika username belum ada di database, daftarkan sebagai user baru dengan skor 0
        if (!repository.isUsernameExists(username)) {
            Benefit newPlayer = new Benefit(username, 0, 0, 0);
            repository.addBenefit(newPlayer);
        }
        // Jika sudah ada, sistem akan langsung menggunakan data yang lama (Highscore)
    }
}