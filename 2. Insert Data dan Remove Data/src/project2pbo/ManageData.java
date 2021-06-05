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
/**
 *
 * @author Admin
 */
public class ManageData extends javax.swing.JDialog {

    /**
     * Creates new form ManageData
     */
    Connection koneksi;
    String action;
    public ManageData(java.awt.Frame parent, boolean modal, String act, String nis) {
        super(parent, modal);
        initComponents();
        koneksi = DatabaseConnection.getKoneksi("localhost", "3306", "root", "", "db_sekola");
        
        action = act;
        if (action.equals("Edit")) {
            txtNIS.setEnabled(false);
            showData(nis);
        }
    }
    public void SimpanData(){
        String nis      = txtNIS.getText();
        String nama     = txtNama.getText();
        String kelas    = cmbKelas.getSelectedItem().toString();
        String jurusan  = cmbJurusan.getSelectedItem().toString();

        try{
            Statement stmt = koneksi.createStatement();
            String query = "INSERT INTO t_siswa(nis, nama, kelas, jurusan)" + "VALUES('"+nis+"','"+nama+"','"+kelas+"', '"+jurusan+"')";
            System.out.println(query);
            int berhasil = stmt.executeUpdate(query);
            if(berhasil == 1){
                JOptionPane.showMessageDialog(null,"Data Berhasil Dimasukkan");
            }else {
                JOptionPane.showMessageDialog(null, "Data Gagal Dimasukkan");
            }
        }catch(SQLException ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Terjadi Kesalahan pada Database");
        }
    }
    public void EditData(){
        String nis      = txtNIS.getText();
        String nama     = txtNama.getText();
        String kelas    = cmbKelas.getSelectedItem().toString();
        String jurusan  = cmbJurusan.getSelectedItem().toString();

        try{
            Statement stmt = koneksi.createStatement();
            String query = "UPDATE t_siswa SET nama = '"+nama+"'" 
                    + "kelas='"+kelas"',"
                    + "jurusan = '"+jurusan+"' WHERE nis= '"+nis+"'";
            System.out.println(query);
            int berhasil = stmt.executeUpdate(query);
            if(berhasil == 1){
                JOptionPane.showMessageDialog(null,"Data Berhasil Diubah");
            }else {
                JOptionPane.showMessageDialog(null, "Data Gagal Diubah");
            }
        }catch(SQLException ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Terjadi Kesalahan pada Database");
        }
    }
    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {
        simpanData();
    }
    private void cmdTambahActionPerform(java.awt.event.ActionEvent evt){
        ManageData tambahData = new ManageData(this, true);
        tambahData.setVisible(true);
    }
    int baris;
    private void tbl_siswaMouseClicked(java.awt.event.ActionEvent evt){
        baris = tbl_siswa.getSelectedRow();
    }
    private void cmdHapusActionPerformd(java.awt.event.ActionEvent evt){
        String idWhoWantToBeDelete = tbl_siswa.getValueAt(baris, 0).toString();
        try {
            Statement stmt = koneksi.createStatement();
            String query = "DELETE FROM t_siswa WHERE nis = '"+idWhoWantToBeDelete+";";
            int berhasil = stmt.executeUpdate(query);
            if(berhasil == 1){
                JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus");
                dtm.getDataVector().removeAllElements();
                showData();
            }else{
                JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus");
            }
        } catch (SQLExpeption ex) {
            ex.printStackTrace();
        }
    }
    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {
        if(action .equals("Edit")) EditData();
        else SimpanData();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ManageData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ManageData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ManageData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ManageData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
       
    }
        void showData(String nis){
        try{
            Statement stmt = koneksi.createStatement();
            String query = "SELECT * FROM t_siswa WHERE nis = '"+nis+"'";
            ResultSet rs = stmt.executeQuery(query);
            rs.first();
            txtNIS.setText(rs.getString("nis"));
            txtNama.setText(rs.getString("nama"));
            cmbKelas.setSelectedItem(rs.getString("kelas"));
            cmbJurusan.setSelectedItem(rs.getString("jurusan"));
        }catch(SQLException ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Terjadi Kesalahan di Query");
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}