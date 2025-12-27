/**************************************************************
 * Filename    : GamePlayView.java
 * Programmer  : Muhammad Rangga Nur Praditha
 * Date        : 2025-12-26
 * Email       : muhrangganp.05@upi.edu
 * Deskripsi   : Kelas View utama yang mengelola rendering visual,
 * aset gambar, dan input keyboard.
 **************************************************************/

package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import model.entity.Alien;
import model.entity.AlienBullet;
import model.entity.Bullet;
import model.entity.Obstacle;
import model.entity.Player;
import viewmodel.GameViewModel;

public class GamePlayView extends JPanel {
    private GameViewModel viewModel;

    // Aset gambar
    private BufferedImage backGroundImage;
    private BufferedImage playerImage;
    private BufferedImage alienImage;
    private BufferedImage obstacleImage;

    /**
     * Konstruktor: Menginisialisasi View permainan, meload aset, 
     * dan mengatur kontrol keyboard.
     * @param username Nama pemain yang dikirim dari MenuView.
     * @param parentWindow Referensi jendela utama untuk navigasi menu.
     */
    public GamePlayView(String username, JFrame parentWindow) {
        this.viewModel = new GameViewModel(username);

        // Memutar musik latar
        view.Sound.playLoop("backsound.wav");

        // Load semua aset gambar di awal (hanya sekali) agar performa optimal
        try {
            backGroundImage = ImageIO.read(getClass().getResource("/asset/image/background.png"));
            playerImage     = ImageIO.read(getClass().getResource("/asset/image/player.png"));
            alienImage      = ImageIO.read(getClass().getResource("/asset/image/alien.png"));
            obstacleImage   = ImageIO.read(getClass().getResource("/asset/image/obstacle.png"));
        } catch (Exception e) {
            System.out.println("Gagal load aset gambar: " + e.getMessage());
        }

        // Aksi ketika game selesai
        viewModel.setOnGameFinishedAction(() -> {
            SwingUtilities.invokeLater(() -> {
                view.Sound.stopBackground();
                JOptionPane.showMessageDialog(this, "Game Over!\nSkor Akhir: " + viewModel.score);

                parentWindow.dispose();
                new MenuView().setVisible(true);
            });
        });

        setFocusable(true);
        setBackground(Color.BLACK);

        // Kontrol Keyboard
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_LEFT)  viewModel.isLeftPressed = true;
                if (key == KeyEvent.VK_RIGHT) viewModel.isRightPressed = true;
                if (key == KeyEvent.VK_UP)    viewModel.isUpPressed = true;
                if (key == KeyEvent.VK_DOWN)  viewModel.isDownPressed = true;
                if (key == KeyEvent.VK_X)     viewModel.isAutoAiming = true;

                if (key == KeyEvent.VK_F) {
                    viewModel.shoot();
                    Sound.play("shoot.wav");
                }

                if (key == KeyEvent.VK_SPACE) {
                    viewModel.quitGame();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_LEFT)  viewModel.isLeftPressed = false;
                if (key == KeyEvent.VK_RIGHT) viewModel.isRightPressed = false;
                if (key == KeyEvent.VK_UP)    viewModel.isUpPressed = false;
                if (key == KeyEvent.VK_DOWN)  viewModel.isDownPressed = false;
                if (key == KeyEvent.VK_X)     viewModel.isAutoAiming = false;
            }
        });

        // Jalankan game loop
        viewModel.startGame(() -> repaint());
    }

    /**
     * Method paintComponent
     * Melakukan rendering seluruh komponen visual game ke layar.
     * @param g Objek Graphics untuk menggambar.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Gambar background
        if (backGroundImage != null) {
            g2d.drawImage(backGroundImage, 0, 0, getWidth(), getHeight(), null);
        }

        // Gambar obstacle
        for (Obstacle obs : viewModel.getObstacles()) {
            if (obstacleImage != null) {
                g2d.drawImage(obstacleImage, obs.getX(), obs.getY(), obs.getWidth(), obs.getHeight(), null);
            }
        }

        // Gambar alien
        for (Alien alien : viewModel.getAliens()) {
            if (alienImage != null) {
                g2d.drawImage(alienImage, alien.getX(), alien.getY(), alien.getWidth(), alien.getHeight(), null);
            }
        }

        // Gambar player dengan logika rotasi
        Player p = viewModel.getPlayer();
        if (playerImage != null) {
            java.awt.geom.AffineTransform old = g2d.getTransform();
            g2d.rotate(Math.toRadians(p.getAngel() + 90), p.getX() + p.getWidth() / 2, p.getY() + p.getHeight() / 2);
            g2d.drawImage(playerImage, p.getX(), p.getY(), p.getWidth(), p.getHeight(), null);
            g2d.setTransform(old);
        }

        // Gambar peluru
        g2d.setColor(Color.RED);
        for (Bullet b : viewModel.getBullets()) {
            g2d.fillRect(b.getX(), b.getY(), b.getWidth(), b.getHeight());
        }

        // Gambar peluru alien
        g2d.setColor(Color.GREEN);
        for (AlienBullet ab : viewModel.getAlienBullets()) {
            g2d.fillRect(ab.getX(), ab.getY(), ab.getWidth(), ab.getHeight());
        }

        // Render UI
        renderUI(g2d, p);
    }

    /**
     * Method renderUI
     * Menggambar elemen antarmuka (HUD) seperti skor, peluru, dan garis bidik.
     * @param g2d Objek Graphics2D untuk rendering yang lebih halus.
     * @param p Objek Player untuk referensi posisi bidikan.
     */
    private void renderUI(Graphics2D g2d, Player p) {
        // Garis bidikan
        int centerX = p.getX() + (p.getWidth() / 2);
        int centerY = p.getY() + (p.getHeight() / 2);
        double rad = Math.toRadians(p.getAngel());
        int endX = centerX + (int) (50 * Math.cos(rad));
        int endY = centerY + (int) (50 * Math.sin(rad));

        g2d.setColor(Color.RED);
        g2d.drawLine(centerX, centerY, endX, endY);

        // Statistik teks
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        g2d.drawString("Score: " + viewModel.score, 10, 20);
        g2d.drawString("Peluru: " + viewModel.ammo, 10, 40);
        g2d.drawString("Meleset: " + viewModel.missedBullets, 10, 60);
    }
}