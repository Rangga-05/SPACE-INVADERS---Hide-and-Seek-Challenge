/**************************************************************
 * Filename    : Benefit.java
 * Programmer  : Muhammad Rangga Nur Praditha
 * Date        : 2025-12-25
 * Email       : muhrangganp.05@upi.edu
 * Deskripsi   : Representasi entitas skor pemain (Benefit) yang
 * menghubungkan data game dengan tabel di basis data
 **************************************************************/

package model.entity;

/**
 * Kelas Benefit merupakan kelas untuk menyimpan
 * informasi skor dan statistik akhir permainan setiap user.
 */
public class Benefit {
    // Atribut yang memetakan kolom di tabel database
    private String username;
    private int skor;
    private int peluruMeleset;
    private int sisaPeluru;

    /**
     * Konstruktor Default (Kosong)
     * Diperlukan oleh beberapa library atau instansiasi manual tanpa data awal.
     */
    public Benefit() {

    }

    /**
     * Konstruktor Parameter
     * Digunakan saat membuat objek hasil dari input game atau fetch dari database.
     */
    public Benefit(String username, int skor, int peluruMeleset, int sisaPeluru) {
        this.username = username;
        this.skor = skor;
        this.peluruMeleset = peluruMeleset;
        this.sisaPeluru = sisaPeluru;
    }

    // Getter Setter
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public int getSkor() {
        return skor;
    }
    public void setSkor(int skor) {
        this.skor = skor;
    }

    public int getPeluruMeleset() {
        return peluruMeleset;
    }
    public void setPeluruMeleset(int peluruMeleset) {
        this.peluruMeleset = peluruMeleset;
    }

    public int getSisaPeluru() {
        return sisaPeluru;
    }
    public void setSisaPeluru(int sisaPeluru) {
        this.sisaPeluru = sisaPeluru;
    }
}