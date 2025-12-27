/**************************************************************
 * Filename    : Player.java
 * Programmer  : Muhammad Rangga Nur Praditha
 * Date        : 2025-12-25
 * Email       : muhrangganp.05@upi.edu
 * Deskripsi   : Model Entity yang merepresentasikan data pemain,
 * posisi, dimensi, serta logika pergerakan dasar pesawat
 **************************************************************/

package model.entity;

/**
 * Kelas Player menyimpan state posisi, kecepatan, dimensi,
 * dan sudut rotasi pesawat pemain.
 */
public class Player {
    // Koordinat posisi pemain
    private int x, y;
    // Kecepatan gerak pesawat
    private int speed = 10;
    // Dimensi pesawat
    private int width = 90, height = 90;
    // Sudut rotasi pesawat
    private double angel = -90.0;

    /**
     * Konstruktor Player
     * @param startX Posisi awal X
     * @param startY Posisi awal Y
     */
    public Player(int startX, int startY) {
        this.x = startX;
        this.y = startY;
    }

    // Gerakan dasar
    public void moveUp() {
        y -= speed;
    }
    public void moveDown() {
        y += speed;
    }
    public void moveLeft() {
        x -= speed;
    }
    public void moveRight() {
        x += speed;
    }
    // Mengembalikan sudut hadap pesawat ke posisi default (atas)
    public void resetAngel() {
        angel = -90.0;
    }

    // Getter & Setter
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }

    public double getAngel() {
        return angel;
    }
    public void setAngel(double angel) {
        this.angel = angel;
    }

    // Fungsi untuk memaksa posisi pesawat (digunakan agar tidak tembus batas layar)
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
}