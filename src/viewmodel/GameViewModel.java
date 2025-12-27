/**************************************************************
 * Filename    : GameViewModel.java
 * Programmer  : Muhammad Rangga Nur Praditha
 * Date        : 2025-12-26
 * Email       : muhrangganp.05@upi.edu
 * Deskripsi   : Otak permainan yang mengelola logika,
 * pergerakan objek, sistem tabrakan, dan sinkronisasi database
 **************************************************************/

package viewmodel;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import model.entity.Alien;
import model.entity.AlienBullet;
import model.entity.Bullet;
import model.entity.Obstacle;
import model.entity.Player;
import model.repository.BenefitRepository;

/**
 * GameViewModel mengimplementasikan Runnable untuk menjalankan Game Loop.
 * Kelas ini memisahkan logika permainan dari tampilan visual (View).
 */
public class GameViewModel implements Runnable {
    // Entitas permainan
    private Player player;
    private List<Alien> aliens;
    private List<Bullet> bullets;
    private List<AlienBullet> alienBullets;
    private List<Obstacle> obstacles;

    // Status game loop
    private boolean isRunning = true;
    private Thread gameThread;
    private Runnable onGameFinishedAction;

    // Callbacks untuk komunikasi dengan View
    private Runnable onRepaintNeeded;

    // Data & database
    private String username;
    private BenefitRepository repo;

    // Statistik permainan
    public int score = 0;
    public int ammo = 0;
    public int missedBullets = 0;

    // Tingkat kesulitan
    private int aliensToSpawn = 2;
    private int aggressionLevel = 0;

    // Flags untuk input keyboard
    public boolean isLeftPressed = false;
    public boolean isRightPressed = false;
    public boolean isUpPressed = false;
    public boolean isDownPressed = false;
    public boolean isAutoAiming = false;

    /**
     * Konstruktor: Menyiapkan data pemain dari DB dan inisialisasi objek awal
     */
    public GameViewModel(String username) {
        this.username = username;

        try {
            this.repo = new BenefitRepository();
            // // Load data amunisi terakhir milik user dari tabel database
            int[] data = repo.getPlayerData(username);
            this.ammo = data[2];
        } catch (Exception e) {
            System.out.println("Gagal koneksi ke database: " + e.getMessage());
            this.repo = null;
            this.ammo = 0;
        }

        // Menyiapkan objek dasar permainan
        player = new Player(300, 460);
        aliens = new ArrayList<>();
        bullets = new ArrayList<>();
        alienBullets = new ArrayList<>();
        obstacles = new ArrayList<>();

        // Munculkan alien dan asteroid pertama
        spawnAlien();
        spawnObstacle();
    }

    /**
     * Menjalankan Thread utama permainan (Game Loop)
     */
    public void startGame(Runnable onRepaintNeeded) {
        this.onRepaintNeeded = onRepaintNeeded;
        gameThread = new Thread(this);
        gameThread.start();
    }

    /**
     * Loop utama: Update logika -> Gambar ulang -> Istirahat (20ms)
     */
    @Override
    public void run() {
        while (isRunning) {
            try {
                updateGameLogic(); // Hitung semua pergerakan dan tabrakan

                if (onRepaintNeeded != null) {
                    onRepaintNeeded.run(); // View merender
                }

                Thread.sleep(20); // Mengatur kecepatan game
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Fungsi menembak: Menentukan target (Auto-aim atau lurus) dan mengurangi amunisi
     */
    public void shoot() {
        if (ammo > 0) {
            int startX = player.getX() + (player.getWidth() / 2);
            int startY = player.getY() + (player.getHeight() / 2);

            int targetX, targetY;

            if (isAutoAiming) {
                // Jika Auto-Aim aktif, cari alien terdekat sebagai target
                Alien nearest = getNearestAlien();

                if (nearest != null) {
                    targetX = nearest.getX() + (nearest.getWidth() / 2);
                    targetY = nearest.getY() + (nearest.getHeight() / 2);

                    updatePlayerAngle(startX, startY, targetX, targetY);
                } else {
                    // Tembakan lurus ke atas jika Auto-Aim mati
                    targetX = startX;
                    targetY = -100;
                    player.resetAngel();
                }
            } else {
                targetX = startX;
                targetY = -100;
                player.resetAngel();
            }

            bullets.add(new Bullet(startX, startY, targetX, targetY));
            ammo--;
        } else {
            System.out.println("Klik! Peluru Habis! Hindari Tembakan!");
        }
    }

    /**
     * Algoritma mencari alien terdekat
     */
    private Alien getNearestAlien() {
        Alien nearest = null;
        double minDistance = Double.MAX_VALUE;

        for (Alien alien : aliens) {
            double diffX = alien.getX() - player.getX();
            double diffY = alien.getY() - player.getY();
            double dist = Math.sqrt(diffX * diffX + diffY * diffY);

            if (dist < minDistance) {
                minDistance = dist;
                nearest = alien;
            }
        }
        return nearest;
    }

    /**
     * Menghitung sudut rotasi gambar pesawat agar menghadap ke target
     */
    private void updatePlayerAngle(int px, int py, int tx, int ty) {
        double angelRed = Math.atan2(ty - py, tx - px);
        player.setAngel(Math.toDegrees(angelRed));
    }

    /**
     * Mematikan game loop dan menyimpan hasil akhir ke database
     */
    public void quitGame() {
        if (isRunning) {
            isRunning = false;
            if (repo != null) {
                repo.saveGameData(username, score, missedBullets, ammo);
            }

            if (onGameFinishedAction != null) {
                onGameFinishedAction.run();
            }
        }
    }

    /**
     * Inti Logika Game: Mengatur pergerakan semua objek per frame.
     */
    private void updateGameLogic() {
        // Kontrol pergerakan Player
        if (isLeftPressed && player.getX() > 0) {
            player.moveLeft();
        }
        if (isRightPressed && player.getX() < 480) {
            player.moveRight();
        }
        if (isUpPressed && player.getY() > 0) {
            player.moveUp();
        }
        if (isDownPressed && player.getY() < 470) {
            player.moveDown();
        }

        // Kontrol rotasi pesawat saat Auto-Aim aktif
        if (isAutoAiming) {
            Alien nearest = getNearestAlien();
            if (nearest != null) {
                int startX = player.getX() + (player.getWidth() / 2);
                int startY = player.getY() + (player.getHeight() / 2);
                int targetX = nearest.getX() + (nearest.getWidth() / 2);
                int targetY = nearest.getY() + (nearest.getHeight() / 2);

                updatePlayerAngle(startX, startY, targetX, targetY);
            }
        } else {
            player.resetAngel();
        }

        // Pergerakan dan logika menembak alien random
        Random rand = new Random();
        for (Alien alien : aliens) {
            alien.move();

            if (rand.nextInt(100) < (2 + aggressionLevel)) {
                int targetX = player.getX() + (player.getWidth() / 2);
                int targetY = player.getY() + (player.getHeight() / 2);

                alienBullets.add(new AlienBullet(alien.getX(), alien.getY(), targetX, targetY));
            }
        }

        // Pergerakan obstacle (asteroid)
        for (Obstacle obs : obstacles) {
            obs.move();
        }

        // Pembersihan peluru player yang keluar layar
        Iterator<Bullet> bulletIter = bullets.iterator();
        while (bulletIter.hasNext()) {
            Bullet b = bulletIter.next();
            b.move();
            if (b.getY() < 0) {
                bulletIter.remove();
            }
        }

        // Pembersihan peluru alien dan update statistik amunisi
        Iterator<AlienBullet> abIter = alienBullets.iterator();
        while (abIter.hasNext()) {
            AlienBullet ab = abIter.next();
            ab.move();

            if (ab.getY() > 650 || ab.getY() < -50 || ab.getX() > 650) {
                abIter.remove();
                missedBullets++;    // Statistik untuk DB
                ammo++;             // Peluru meleset alien jadi tambahan amunisi player
            }
        }

        // Cek semua potensi tabrakan objek
        checkCollisions();

        // Sistem Round dengan spawn alien baru jika semua sudah hancur
        if (aliens.isEmpty()) {
            aliensToSpawn++;

            if (aliensToSpawn > 6) {
                aliensToSpawn = 2;
                aggressionLevel++; // Musuh makin cepat menembak
                System.out.println("Round Selanjutnya! Musuh Semakin Agresif");
            }
            spawnAlien();
            spawnObstacle();
        }
    }

    /**
     * Mengatur sistem deteksi benturan antar objek (Hitbox).
     */
    private void checkCollisions() {
        int paddingX = 12;
        int paddingY = 5;

        // Area hit-box player
        Rectangle playerRect = new Rectangle(
                player.getX() + paddingX,
                player.getY() + paddingY,
                player.getWidth() - (paddingX * 2),
                player.getHeight() - (paddingY * 2)
        );

        // Cek peluru player menabrak asteroid atau alien
        Iterator<Bullet> bit = bullets.iterator();
        while (bit.hasNext()) {
            Bullet b = bit.next();
            Rectangle bRect = new Rectangle(b.getX(), b.getY(), b.getWidth(), b.getHeight());
            boolean bulletActive = true;

            // Kena batu (peluru Hilang, batu tetap)
            Iterator<Obstacle> obsIter = obstacles.iterator();
            while (obsIter.hasNext() && bulletActive) {
                Obstacle o = obsIter.next();
                Rectangle obsRect = new Rectangle(o.getX() + 10, o.getY() + 10, o.getWidth() - 20, o.getHeight() - 20);

                if (bRect.intersects(obsRect)) {
                    bit.remove();
                    bulletActive = false;
                }
            }

            // Kena alien (alien mati, peluru hilang)
            if (bulletActive) {
                Iterator<Alien> ait = aliens.iterator();
                while (ait.hasNext() && bulletActive) {
                    Alien a = ait.next();
                    Rectangle alienRect = new Rectangle(a.getX() + 5, a.getY() + 5, a.getWidth() - 10, a.getHeight() - 10);
                    if (bRect.intersects(alienRect)) {
                        bit.remove();
                        ait.remove();
                        score += 10;
                        bulletActive = false;
                        view.Sound.play("explosion.wav");
                    }
                }
            }
        }

        // Peluru musuh terkena batu atau player
        Iterator<AlienBullet> abit = alienBullets.iterator();
        while (abit.hasNext()) {
            AlienBullet ab = abit.next();
            Rectangle abRect = new Rectangle(ab.getX(), ab.getY(), ab.getWidth(), ab.getHeight());
            boolean bulletActive = true;

            // Kena batu (peluru musuh milang, player selamat)
            Iterator<Obstacle> obsIter = obstacles.iterator();
            while (obsIter.hasNext() && bulletActive) {
                Obstacle o = obsIter.next();
                Rectangle obsRect = new Rectangle(o.getX() + 10, o.getY() + 10, o.getWidth() - 20, o.getHeight() - 20);

                if (abRect.intersects(obsRect)) {
                    abit.remove();
                    bulletActive = false;
                }
            }

            // Kena player (game over)
            if (bulletActive) {
                if (abRect.intersects(playerRect)) {
                    handleGameOver();
                }
            }
        }

        // Cek Player menabrak Alien langsung
        for(Alien a : aliens) {
            Rectangle alienRect = new Rectangle(a.getX() + 5, a.getY() + 5, a.getWidth() - 10, a.getHeight() - 10);
            if (playerRect.intersects(alienRect)) {
                handleGameOver();
            }
        }

        // Logika asteorid memantul saat mengenai player atau alien
        for (Obstacle obs : obstacles) {
            Rectangle obsRect = new Rectangle(obs.getX() + 5, obs.getY() + 5, obs.getWidth() - 10, obs.getHeight() - 10);

            // Cek batu nabrak player
            if (obsRect.intersects(playerRect)) {
                obs.bounceBack();
                System.out.println("Batu Nabrak Player!");
            }

            // Cek batu nabrak alien
            for (Alien alien : aliens) {
                Rectangle alienRect = new Rectangle(alien.getX() + 5, alien.getY() + 5, alien.getWidth() - 10, alien.getHeight() - 10);

                if (obsRect.intersects(alienRect)) {
                    obs.bounceBack();
                }
            }
        }
    }

    /**
     * Memproses kondisi permainan berakhir dan update database.
     */
    private void handleGameOver() {
        if (isRunning) {
            isRunning = false;
            if (repo != null) {
                repo.saveGameData(username, score, missedBullets, ammo);
            }

            if (onGameFinishedAction != null) {
                onGameFinishedAction.run();
            }
        }
    }

    /**
     * Instansiasi Alien di posisi acak.
     */
    public void spawnAlien() {
        Random rand = new Random();
        // Munculkan alien
        for(int i = 0; i < aliensToSpawn; i++) {
            aliens.add(new Alien(rand.nextInt(500), rand.nextInt(150)));
        }
    }

    /**
     * Instansiasi Rintangan (asteroid) berdasarkan jumlah wave.
     */
    public void spawnObstacle() {
        obstacles.clear();
        int numberOfStones = 1;
        if (aliensToSpawn >= 3 && aliensToSpawn <= 4) {
            numberOfStones = 2;
        } else if (aliensToSpawn >= 5) {
            numberOfStones = 3;
        }
        System.out.println("Spawan Batu: " + numberOfStones);

        // Muncul batu
        Random rand = new Random();
        for(int i = 0; i < numberOfStones; i++) {
            obstacles.add(new Obstacle(rand.nextInt(500), rand.nextInt(300) + 100));
        }
    }

    // Getter & Setter untuk komunikasi ke View
    public Player getPlayer() {
        return player;
    }
    public List<Alien> getAliens() {
        return aliens;
    }
    public List<Bullet> getBullets() {
        return bullets;
    }
    public List<AlienBullet> getAlienBullets() {
        return alienBullets;
    }
    public List<Obstacle> getObstacles() {
        return obstacles;
    }
    public boolean isRunning() { return this.isRunning; }
    public void setOnGameFinishedAction(Runnable action) {
        this.onGameFinishedAction = action;
    }
}