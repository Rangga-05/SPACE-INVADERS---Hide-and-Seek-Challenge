/**************************************************************
 * Filename    : BenefitRepository.java
 * Programmer  : Muhammad Rangga Nur Praditha
 * Date        : 2025-12-26
 * Email       : muhrangganp.05@upi.edu
 * Deskripsi   : Jembatan data (Repository) untuk mengelola tabel
 * tbenefit dengan mewarisi fungsionalitas dari kelas DB.
 **************************************************************/

package model.repository;

import java.sql.ResultSet; // Import kelas DB yang baru
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.DB;
import model.entity.Benefit;

/**
 * Kelas BenefitRepository menangani semua operasi CRUD khusus untuk data skor.
 * Menggunakan pewarisan (Inheritance) dari kelas DB.
 */
public class BenefitRepository extends DB {

    public BenefitRepository() throws Exception {
        // Memanggil konstruktor DB
        super();
    }

    /**
     * Mengambil semua daftar skor untuk ditampilkan di Highscore.
     */
    public List<Benefit> getAllBenefits() {
        List<Benefit> list = new ArrayList<>();
        String sql = "SELECT * FROM tbenefit ORDER BY skor DESC";

        try {
            createQuery(sql);
            ResultSet rs = getResult();

            while (rs != null && rs.next()) {
                Benefit b = new Benefit();
                b.setUsername(rs.getString("username"));
                b.setSkor(rs.getInt("skor"));
                b.setPeluruMeleset(rs.getInt("peluru_meleset"));
                b.setSisaPeluru(rs.getInt("sisa_peluru"));
                list.add(b);
            }
            closeResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Mengecek ketersediaan username di database.
     */
    public boolean isUsernameExists(String username) {
        String sql = "SELECT username FROM tbenefit WHERE username = '" + username + "'";
        try {
            createQuery(sql);
            ResultSet rs = getResult();
            boolean exists = (rs != null && rs.next());
            closeResult();
            return exists;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Menambahkan record baru saat pemain mendaftar pertama kali.
     */
    public void addBenefit(Benefit benefit) {
        String sql = "INSERT INTO tbenefit (username, skor, peluru_meleset, sisa_peluru) VALUES (" +
                "'" + benefit.getUsername() + "', " +
                benefit.getSkor() + ", " +
                benefit.getPeluruMeleset() + ", " +
                benefit.getSisaPeluru() + ")";

        try {
            createUpdate(sql); // Pakai createUpdate
        } catch (SQLException es) {
            es.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mengambil statistik akumulasi data pemain tertentu.
     */
    public int[] getPlayerData(String username) {
        int[] data = {0, 0, 0}; // skor, meleset, sisa
        String sql = "SELECT skor, peluru_meleset, sisa_peluru FROM tbenefit WHERE username = '" + username + "'";

        try {
            createQuery(sql);
            ResultSet rs = getResult();

            if (rs != null && rs.next()) {
                data[0] = rs.getInt("skor");
                data[1] = rs.getInt("peluru_meleset");
                data[2] = rs.getInt("sisa_peluru");
            }
            closeResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * Menyimpan data akhir permainan (Update akumulasi skor).
     */
    public void saveGameData(String username, int skorBaru, int melesetBaru, int sisaPeluru) {
        // Query update
        String sql = "UPDATE tbenefit SET skor = skor + " + skorBaru +
                ", peluru_meleset = peluru_meleset + " + melesetBaru +
                ", sisa_peluru = " + sisaPeluru +
                " WHERE username = '" + username + "'";

        try {
            createUpdate(sql);
            System.out.println("Data Game Berhasil Disimpan: " + username);
        } catch (SQLException es) {
            es.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}