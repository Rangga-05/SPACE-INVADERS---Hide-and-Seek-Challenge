/**************************************************************
 * Filename    : Obstacle.java
 * Programmer  : Muhammad Rangga Nur Praditha
 * Date        : 2025-12-25
 * Email       : muhrangganp.05@upi.edu
 * Deskripsi   : Model Entity untuk rintangan (batu) yang menyimpan
 * koordinat posisi dan mengatur logika pantulan
 **************************************************************/

package model.entity;

/**
 * Kelas Obstacle murni mengelola data posisi asteroid.
 */
public class Obstacle {
    // Koordinat posisi asteroid
    private int x, y;
    // Dimensi asteroid
    private int width = 70, height = 70;
    // Kecepatan gerak asteroid
    private int dx = 1;
    private int dy = 1;

    // Konstruktor: Menentukan lokasi awal rintangan
    public Obstacle(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Fungsi untuk menggerakkan rintangan dan memantulkannya di batas layar
    public void move() {
        x += dx;
        y += dy;

        // Memantul jika mengenai batas layar
        if (x <= 0 || x >= 520) {
            dx = -dx;
        }
        if (y <= 0 || y >= 510) {
            dy = -dy;
        }
    }

    // Fungsi khusus untuk membalikkan arah saat bertabrakan dengan objek lain
    public void bounceBack() {
        dx = -dx;
        dy = -dy;
    }

    // Getter diperlukan agar View dapat mengetahui lokasi untuk menggambar asset
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
}
