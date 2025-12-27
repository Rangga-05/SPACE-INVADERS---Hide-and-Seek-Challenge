/**************************************************************
 * Filename    : MenuView.java
 * Programmer  : Muhammad Rangga Nur Praditha
 * Date        : 2025-12-26
 * Email       : muhrangganp.05@upi.edu
 * Deskripsi   : Layar utama (Menu) yang berfungsi untuk input
 * username pemain dan menampilkan daftar Highscore
 **************************************************************/

package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import viewmodel.MenuViewModel;

/**
 * Kelas MenuView mengatur tampilan GUI utama menggunakan JFrame.
 * Berinteraksi dengan MenuViewModel untuk mendapatkan data skor.
 */
public class MenuView extends JFrame {
    private MenuViewModel viewModel;
    private JTextField usernameField;
    private JTable table;

    public MenuView() {
        // Inisialisasi ViewModel pendukung
        this.viewModel = new MenuViewModel();
        initUI();
    }

    /**
     * Menyiapkan seluruh komponen antarmuka pengguna (GUI).
     */
    private void initUI() {
        // Pengaturan Dasar Frame
        setTitle("SPACE INVADERS");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Membuat jendela muncul di tengah layar
        setLayout(new BorderLayout(10, 10));

        // Judul game
        JLabel titleLabel = new JLabel("SPACE INVADERS", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(titleLabel, BorderLayout.NORTH);

        // Panel tengah (Input username & tabel highscore)
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

        // Input username
        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(new JLabel("Username: "));
        usernameField = new JTextField(15);
        inputPanel.add(usernameField);
        centerPanel.add(inputPanel);

        // Tabel highscore
        table = new JTable();
        refreshTable(); // Mengambil data dari ViewModel
        JScrollPane scrollPane = new JScrollPane(table);
        centerPanel.add(scrollPane);

        add(centerPanel, BorderLayout.CENTER);

        // Panel tombol
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton btnPlay = new JButton("Play");
        JButton btnQuit = new JButton("Quit");

        // Event Handler: Tombol Play
        btnPlay.addActionListener(e -> {
            String nama = usernameField.getText().trim();
            if (!nama.isEmpty()) {
                // Proses pendaftaran / cek user di ViewModel
                viewModel.handlePlay(nama);
                // Tutup menu dan buka jendela game
                this.dispose();
                SwingUtilities.invokeLater(() -> {
                    GameWindow game = new GameWindow(nama);
                    game.setVisible(true);
                });
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(this, "Isi Username Terlebih Dahulu");
            }
        });

        // Event Handler: Tombol Quit
        btnQuit.addActionListener(e -> System.exit(0));

        buttonPanel.add(btnPlay);
        buttonPanel.add(btnQuit);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Memperbarui isi tabel highscore dengan data terbaru dari database.
     */
    private void refreshTable() {
        table.setModel(viewModel.getTableData());
    }
}