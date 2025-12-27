/**************************************************************
 * Filename    : Bullet.java
 * Programmer  : Muhammad Rangga Nur Praditha
 * Date        : 2025-12-25
 * Email       : muhrangganp.05@upi.edu
 * Deskripsi   : Entitas peluru yang ditembakkan oleh Player,
 * menggunakan perhitungan vektor untuk bergerak menuju target
 **************************************************************/

package model.entity;

/**
 * Kelas Bullet mengelola posisi koordinat dan arah gerak peluru.
 * Menggunakan tipe data double untuk koordinat agar pergerakan lebih halus.
 */
public class Bullet {
    // Koordinat menggunakan double untuk presisi perhitungan vektor
    private double x, y;
    // Kecepatan perubahan koordinat
    private double dx, dy;
    // Konfigurasi atribut peluru
    private int speed= 10;
    private int width = 5, height = 10;

    /**
     * Konstruktor Bullet
     * @param startX Posisi awal X (biasanya pusat pesawat)
     * @param startY Posisi awal Y (biasanya pusat pesawat)
     * @param targetX Koordinat X tujuan tembakan
     * @param targetY Koordinat Y tujuan tembakan
     */
    public Bullet(int startX, int startY, int targetX, int targetY) {
        this.x = startX;
        this.y = startY;

        // Menghitung selisih jarak antara posisi asal dan target
        double diffX = targetX - startX;
        double diffY = targetY - startY;
        // Menghitung jarak lurus
        double distance = Math.sqrt((diffX * diffX) + (diffY * diffY));
        // Pengaman agar tidak terjadi pembagian dengan nol
        if (distance == 0) {
            distance = 1;
        }
        // Normalisasi vektor dan dikalikan dengan speed
        this.dx = (diffX / distance) * speed;
        this.dy = (diffY / distance) * speed;
    }

    /**
     * Memperbarui posisi peluru berdasarkan vektor arah (dx, dy)
     */
    public void move() {
        x += dx;
        y += dy;
    }

    // Getter untuk casting ke int diperlukan karena koordinat layar menggunakan pixel (integer)
    public int getX() {
        return (int) x;
    }
    public int getY() {
        return (int) y;
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
}
