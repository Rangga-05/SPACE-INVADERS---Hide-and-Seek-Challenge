/**************************************************************
 * Filename    : DB.java
 * Programmer  : Muhammad Rangga Nur Praditha
 * Date        : 2025-12-26
 * Email       : muhrangganp.05@upi.edu
 * Deskripsi   : Kelas utilitas untuk mengelola koneksi, eksekusi
 * query, dan manipulasi data pada MySQL Database
 **************************************************************/

package model; //package model/kelas yang mengakses basis data

//import konektor
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Kelas DB menangani semua interaksi tingkat rendah dengan Database.
 * Digunakan sebagai dasar oleh kelas Repository untuk mengolah data game.
 */
public class DB {
    // Pengaturan alamat database
    private String ConAddress = "jdbc:mysql://localhost:3306/db_game?user=root&password=";
    private Statement stmt = null;  // Objek untuk menjalankan query
    private ResultSet rs = null;    // Objek untuk menampung hasil query SELECT
    private Connection conn = null; // Objek koneksi aktif

    /**
     * Konstruktor: Melakukan inisialisasi driver dan membuka koneksi ke MySQL.
     */
    public DB() throws Exception, SQLException {
        /**
         * Method DB
         * Konstruktor : melakukan koneksi ke MySQL dan basis data
         * Menerima masukan berupa string alamat koneksi ke MySQL dan basis data
         */
        try {
            // membuat driver MySQL
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            // membuat koneksi MySQL dan basis data
            conn = DriverManager.getConnection(ConAddress);
            conn.setTransactionIsolation(conn.TRANSACTION_READ_UNCOMMITTED);
        }
        catch(SQLException es) {
            // mengeluarkan pesan error jika koneksi gagal
            throw es;
        }
    }

    public void createQuery(String Query) throws Exception, SQLException {
        /**
         * Method createQuery
         * Mengeksekusi query tanpa mengubah isi data
         * Menerima masukan berupa string query
         */
        try {
            stmt = conn.createStatement();
            // eksekusi query
            rs = stmt.executeQuery(Query);
            if (stmt.execute(Query)) {
                // ambil hasil query
                rs = stmt.getResultSet();
            }
        }
        catch(SQLException es) {
            // eksepsi jika query gagal dieksekusi
            throw es;
        }
    }

    public void createUpdate(String Query) throws Exception, SQLException {
        /**
         * Method createUpdate
         * Mengeksekusi query yang mengubah isi data (update, insert, delete)
         * Menerima masukan berupa string query
         */
        try {
            stmt = conn.createStatement();
            // eksekusi query
            int hasil = stmt.executeUpdate(Query);
        }
        catch(SQLException es) {
            // eksepsi jika query gagal dieksekusi
            throw es;
        }
    }

    public ResultSet getResult() throws Exception {
        /**
         * Method getResult
         * Memberikan hasil query
         */
        ResultSet Temp = null;
        try{
            return rs;
        }
        catch (Exception ex) {
            // eksepsi jika hasil tidak dapat dikembalikan
            return Temp;
        }
    }

    public void closeResult() throws SQLException, Exception {
        /**
         * Method closeResult
         * Menutup hubungan dari eksekusi query
         */
        if (rs != null) {
            try {
                rs.close();
            }
            catch (SQLException sqlEx) {
                rs = null;
                throw sqlEx;
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            }
            catch (SQLException sqlEx) {
                stmt = null;
                throw sqlEx;
            }
        }
    }

    public void closeConnection() throws SQLException, Exception {
        /**
         * Method closeConnection
         * Menutup hubungan dengan MySQL dan basis data
         */
        if (conn != null) {
            try {
                conn.close();
            }
            catch(SQLException sqlEx) {
                conn = null;
            }
        }
    }
}