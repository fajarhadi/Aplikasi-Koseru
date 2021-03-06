/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Database.DatabasePenarikan;
import Database.DatabasePinjaman;
import Database.DatabaseSimpanan;
import Model.Penarikan;
import Model.Person;
import Model.Pinjaman;
import Model.Simpanan;
import View.User_MenuUser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ControllerUserMenu implements ActionListener{
    private User_MenuUser vUser;
    private Person newP;
    private int totSaldo=0;
    private boolean ablePinjam;
    private DatabasePinjaman dbPinjaman;
    private DatabaseSimpanan dbSimpanan;
    private DatabasePenarikan dbPenarikan;
    public ControllerUserMenu(Person p) {
        this.newP=p;
        this.vUser=new User_MenuUser();
        this.ablePinjam = true;
        dbPenarikan = new DatabasePenarikan();
        dbSimpanan = new DatabaseSimpanan();
        
        this.vUser.addListenerMUser(this);
        this.vUser.getU_txEmail().setText(p.getEmail());
        this.vUser.getU_txKd_Anggota().setText(p.getKode_angg());
        this.vUser.getU_txNama().setText(p.getNama());
        this.vUser.getU_txAlamat().setText(p.getAlamat());
        this.vUser.getU_txProfesi().setText(p.getProfesi());
        if(p.getJk()=='L'){
            this.vUser.getU_txJk().setText("Laki-laki");
        }else{
            this.vUser.getU_txJk().setText("Perempuan");
        }
        dbPinjaman = new DatabasePinjaman();
        dbPinjaman.getAllPinjaman(p.getKode_angg());
        ArrayList<Pinjaman> pinjaman = dbPinjaman.getPinjaman();
        for (Pinjaman pj : pinjaman) {
            if(pj.getStatus_acc().equals("Menunggu")||pj.getKet_lunas().equals("belum lunas")){
                this.ablePinjam = false;
            }
        }
        this.vUser.getU_txTTL().setText(p.getTmplahir()+", "+p.getTgllahir());
        this.vUser.setVisible(true);
    }



        @Override
        public void actionPerformed(ActionEvent e) {
            Object x=e.getSource();
            if(x.equals(vUser.getU_btnReqPinjam())){
                if(ablePinjam==true){
                   new ControllerReqPinjaman(newP,this,ablePinjam);
                }
                else{
                    JOptionPane.showMessageDialog(vUser,"Tidak bisa melakukan request","GAGAL",JOptionPane.ERROR_MESSAGE);
                }
            }
            else if(x.equals(vUser.getU_btnCekSaldo())){
                dbPenarikan.getPenarikanUser(newP.getKode_angg());
                dbSimpanan.getAllSimpanan(newP.getKode_angg());
                ArrayList<Penarikan> penarikan = dbPenarikan.getPenarikan();
                for (Penarikan pj : penarikan) {
                    totSaldo-=pj.getJum_penarikan();
                }
                ArrayList<Simpanan> simpanan = dbSimpanan.getSimpanan();
                for (Simpanan s : simpanan) {
                    totSaldo+=s.getJum_simpanan();
                }
                JOptionPane.showMessageDialog(vUser,"Jumlah saldo saat ini : Rp"+totSaldo,"SALDO",JOptionPane.INFORMATION_MESSAGE);
            }
            else if(x.equals(vUser.getU_btnEdit())){
                new ControllerEditProfile(newP,vUser);
            }
            else if(x.equals(vUser.getU_btnLogout())){
                vUser.dispose();
                new ControllerLogin();
            }
            else if(x.equals(vUser.getBtnTerimaPenarikan())){
                
                new ControllerUserLihatPenarikan(newP.getKode_angg());
            }
            else if(x.equals(vUser.getBtnTerimaPinjaman())){
                
                new ControllerUserLihatPinjaman(newP.getKode_angg(),this);
            }
            else if(x.equals(vUser.getBtnTerimaSimpanan())){
                
                new ControllerUserLihatSimpanan(newP.getKode_angg());
            }
        }


    public void setAblePinjam(boolean ablePinjam) {
        this.ablePinjam = ablePinjam;
    }
    
}
