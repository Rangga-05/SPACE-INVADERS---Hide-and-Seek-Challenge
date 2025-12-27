/**************************************************************
 * Filename    : GameWindow.java
 * Programmer  : Muhammad Rangga Nur Praditha
 * Date        : 2025-12-25
 * Email       : muhrangganp.05@upi.edu
 * Deskripsi   : Window utama (JFrame) yang berfungsi sebagai
 * wadah untuk menampilkan arena permainan (GamePlayView)
 **************************************************************/

package view;

import javax.swing.JFrame;

/**
 * Kelas GameWindow mengatur bingkai jendela aplikasi saat game berlangsung.
 * Di dalamnya akan dimuat komponen GamePlayView yang berisi logika visual.
 */
public class GameWindow extends JFrame {

    /**
     * Konstruktor: Mengatur konfigurasi jendela dan memuat area permainan.
     * @param username Nama pemain untuk ditampilkan pada judul jendela.
     */
    public GameWindow(String username) {
        // Mengatur judul jendela dengan nama pemain
        setTitle("SPACE INVADERS - Player: (" + username + ")");
        // Ukuran jendela disesuaikan dengan area permainan (600x600)
        setSize(600, 600);
        // Memastikan aplikasi benar-benar tertutup saat jendela di-close
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Membuat jendela muncul tepat di tengah layar pengguna
        setLocationRelativeTo(null);

        // Menambahkan komponen GamePlayView ke dalam frame ini
        add(new GamePlayView(username, this));

    }
}