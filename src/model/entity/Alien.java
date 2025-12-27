/**************************************************************
 * Filename    : Alien.java
 * Programmer  : Muhammad Rangga Nur Praditha
 * Date        : 2025-12-25
 * Email       : muhrangganp.05@upi.edu
 * Deskripsi   : Model Entity untuk musuh (Alien) yang menyimpan
 * data posisi dan logika gerakan memantul
 **************************************************************/

package model.entity;

import java.util.Random;

/**
 * Kelas Alien murni mengelola data dan gerakan musuh
 */
public class Alien {
    // Posisi dan kecepatan gerak
    private int x, y;
    private int speedX;
    private int speedY;

    // Dimensi alien
    private int width = 60, height = 60;

    // Konstruktor: Mengatur posisi awal dan kecepatan acak
    public Alien(int startX, int startY) {
        this.x = startX;
        this.y = startY;

        Random rand = new Random();

        // Cari kecepatan X acak (agar tidak diam di tempat, speedX tidak boleh 0)
        speedX = 0;
        while (speedX == 0) {
            speedX = rand.nextInt(5) - 2;
        }

        // Kecepatan Y positif agar alien turun ke bawah perlahan
        speedY = rand.nextInt(2) + 1;
    }

    /**
     * Logika pergerakan Alien
     * alien akan memantul jika menabrak batas layar yang ditentukan
     */
    public void move() {
        x += speedX;
        y += speedY;

        // Logika pantulan pada batas horizontal dan vertikal yang sudah ditentukan
        if (x <= 0 || x >= 530) {
            speedX = -speedX;
        }
        if (y <= 0 || y >= 200) {
            speedY = -speedY;
        }

        // Penagaman posisi alien agar tidak keluar dari layar
        if (x < 0) {
            x = 0;
        }
        if (x > 540) {
            x = 540;
        }
        if (y < 0) {
            y = 0;
        }
        if (y > 200) {
            y = 200;
        }
    }

    // Getter agar View dapat mengambil koordinat untuk menggambar asset
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