/**************************************************************
 * Filename    : Sound.java
 * Programmer  : Muhammad Rangga Nur Praditha
 * Date        : 2025-12-26
 * Email       : muhrangganp.05@upi.edu
 * Deskripsi   : Kelas utilitas untuk mengelola pemutaran efek
 * suara (SFX) dan musik latar (BGM) secara statis
 **************************************************************/

package view;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * Kelas Sound menyediakan fungsionalitas untuk memutar audio.
 * Mendukung pemutaran sekali (play) dan berulang (loop).
 */
public class Sound {

    // Menyimpan referensi klip musik latar agar bisa dikontrol (stop/start)
    private static Clip backgroundClip;

    /**
     * Memutar file suara satu kali. Cocok untuk efek tembakan atau ledakan.
     * @param fileName Nama file suara (misal: "explosion.wav")
     */
    public static void play(String fileName) {
        try {
            // Mencari file di dalam folder resources asset/sound/
            URL url = Sound.class.getResource("/asset/sound/" + fileName);
            if (url != null) {
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
                Clip clip = AudioSystem.getClip();
                clip.open(audioIn);
                clip.start(); // Jalankan suara
            } else {
                System.out.println("File suara tidak ditemukan: " + fileName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Memutar file suara secara terus-menerus (looping).
     * Digunakan untuk musik latar (Backsound).
     * @param fileName Nama file musik (misal: "bgm.wav")
     */
    public static void playLoop(String fileName) {
        try {
            // Menghentikan musik sebelumnya agar tidak terjadi penumpukan suara
            stopBackground();

            URL url = Sound.class.getResource("/asset/sound/" + fileName);
            if (url != null) {
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);

                // Inisialisasi dan jalankan klip dalam mode loop
                backgroundClip = AudioSystem.getClip();
                backgroundClip.open(audioIn);
                backgroundClip.loop(Clip.LOOP_CONTINUOUSLY);
                backgroundClip.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Menghentikan musik latar yang sedang berjalan dan membersihkan memori.
     */
    public static void stopBackground() {
        if (backgroundClip != null && backgroundClip.isRunning()) {
            backgroundClip.stop();  // Hentikan suara
            backgroundClip.close(); // Lepaskan resource memori
        }
    }
}