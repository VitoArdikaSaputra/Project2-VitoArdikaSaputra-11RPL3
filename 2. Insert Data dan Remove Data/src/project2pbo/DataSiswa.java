/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project2pbo;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Admin
 */
public class DataSiswa extends javax.swing.JFrame {

    /**
     * Creates new form DataSiswa
     */
    Connection koneksi;
    
    public DataSiswa() {
        initComponents();
        koneksi = DatabaseConnection.getKoneksi("localhost", "3306", "root", "", "db_sekola");
        showData();
    }
    
        DefaultTableModel dtm;
    public void showData(){
        
        String[] kolom = {"NO","NIS","Nama","Kelas","Jurusan"};
        
        dtm = new DefaultTableModel(null, kolom);
        try {
            Statement stat = koneksi.createStatement();
            String query = "SELECT * FROM t_siswa";
            ResultSet rs = stat.executeQuery(query);
            int no = 1;
            while (rs.next()){
                String nis = rs.getString("nis");
                String nama = rs.getString("nama");
                String kelas = rs.getString("kelas");
                String jurusan = rs.getString("jurusan");                

                dtm.addRow(new String[]{no+"",nis,nama,kelas,jurusan});
                no++;}
            }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        t_siswa.setModel(dtm);
    }
    private void cmdTambahActionPerformed(java.awt.event.ActionEvent evt){
        ManageData tambahData = new Manage(this, true, "Tambah", "");
        tambahData.setVisible(true);
    }
    private void cmdTambahActionPerformed(java.awt.event.ActionEvent evt){
        ManageData tambahData = new ManageData(this, true, "Tambah", "");
        tambahData.setVisible(true);
    }
    private void cmdEditActionPerformed(java.awt.event.ActionEvent evt){
        String nis = tbl_siswa.getValue(baris, 1).toString();
        ManageData tambahData = new ManageData(this, true, "Edit", nis);
        tambahData.setVisible(true);
    }
}
