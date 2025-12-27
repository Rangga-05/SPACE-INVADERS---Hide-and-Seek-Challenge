/**************************************************************
 * Filename    : AlienBullet.java
 * Programmer  : Muhammad Rangga Nur Praditha
 * Date        : 2025-12-25
 * Email       : muhrangganp.05@upi.edu
 * Deskripsi   : Entitas peluru yang ditembakkan oleh Alien,
 * bergerak menuju posisi terakhir Player saat ditembakkan
 **************************************************************/

package model.entity;

/**
 * Kelas AlienBullet mengelola koordinat dan pergerakan peluru musuh.
 * Kecepatan peluru disetel lebih lambat dari peluru player untuk keseimbangan game.
 */
public class AlienBullet {
    // Koordinat posisi dan vektor arah pergerakan
    private double x, y;
    private double dx, dy;
    // Konfigurasi atribut peluru alien
    private int speed = 6;
    private int width = 5, height = 10;

    /**
     * Konstruktor AlienBullet
     * @param startX Posisi awal X (lokasi Alien)
     * @param startY Posisi awal Y (lokasi Alien)
     * @param targetX Koordinat X target (lokasi Player)
     * @param targetY Koordinat Y target (lokasi Player)
     */
    public AlienBullet(int startX, int startY, int targetX, int targetY) {
        this.x = startX;
        this.y = startY;

        // Menghitung selisih jarak untuk menentukan arah
        double diffX = targetX - startX;
        double diffY = targetY - startY;

        // Perhitungan jarak lurus
        double distance = Math.sqrt((diffX * diffX) + (diffY * diffY));

        // Normalisasi vektor arah agar peluru bergerak konsisten sesuai speed
        this.dx = (diffX / distance) * speed;
        this.dy = (diffY / distance) * speed;
    }

    /**
     * Memperbarui posisi peluru berdasarkan arah dan kecepatan
     */
    public void move() {
        x += dx;
        y += dy;
    }

    // Getter
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