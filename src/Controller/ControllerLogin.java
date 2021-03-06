/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Database.DatabaseUser;
import Model.Person;
import View.Login;
import View.User_Register;
import com.sun.java.accessibility.util.AWTEventMonitor;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Date;
import java.util.Arrays;
import javax.swing.JOptionPane;

public class ControllerLogin {
    private Login vLogin;
    private User_Register vRegis;
    private Person mPerson;
    private final DatabaseUser akun= new DatabaseUser();
    public ControllerLogin(){
        this.vLogin=new View.Login();
        this.vLogin.addListenerMLogin(new LoginListener());
        this.vLogin.addEListenerMLogin(new LoginEListener());
        this.vLogin.setVisible(true);
    }

    class LoginEListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            Object x = e.getSource();
            if (x.equals(vLogin.getlabelDaftar())){
                vRegis =new View.User_Register();
                vRegis.addListener(new RegisListener());
                vRegis.setKodeAng("AN"+Integer.toString(akun.getSum()));
                vRegis.setVisible(true);
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e){
            Object x = e.getSource();
            if (x.equals(vLogin.getlabelDaftar())){
                vLogin.getlabelDaftar().setCursor(new Cursor(Cursor.HAND_CURSOR));//To change body of generated methods, choose Tools | Templates.
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }

    class RegisListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Object x = e.getSource();
            if(x.equals(vRegis.getBtnRegis())){
                String username = vRegis.getUser();
                String pass = vRegis.getPass();
                String id = vRegis.getId();
                String nama = vRegis.getNama();
                String alamat = vRegis.getAlamat();
                int idxjk = vRegis.getR_dbJk().getSelectedIndex();
                String lhr_tgl = vRegis.getKalender();
                String lhr_tmp = vRegis.getTmp();
                String status = "ang";
                String email = vRegis.getEmail();
                String kerja = vRegis.getProfesi();
                String pass1 = Arrays.toString(vRegis.getPass1().getPassword());
                String pass2 = Arrays.toString(vRegis.getPass2().getPassword());
                
                if(username.length()==0||pass.length()==0||
                        nama.length()==0||id.length()==0||alamat.length()==0||
                        lhr_tgl.length()==0||lhr_tmp.length()==0||
                        email.length()==0||kerja.length()==0||
                        idxjk==0){
                    JOptionPane.showMessageDialog(vRegis, "Data tidak boleh kosong","GAGAL",JOptionPane.ERROR_MESSAGE);
                }  else if (pass1.compareTo(pass2)!=0){
                    JOptionPane.showMessageDialog(vRegis, "Password tidak sama","GAGAL",JOptionPane.ERROR_MESSAGE);
                }else {
                    char jk;
                    if(idxjk==1){
                        jk='L';
                    }else{
                        jk='P';
                    }
                    int hasil =akun.regis(new Person(id,nama,kerja,lhr_tmp,Date.valueOf(lhr_tgl),email,status,alamat,jk,username,pass,null,null,null));
                    if(hasil==1){
                        JOptionPane.showMessageDialog(vRegis, "Akun baru telah terdaftar","BERHASIL",JOptionPane.INFORMATION_MESSAGE);
                        vRegis.dispose();
                    }else if(hasil==2){
                        JOptionPane.showMessageDialog(vRegis, "Username sudah ada","GAGAL",JOptionPane.ERROR_MESSAGE);
                    }
                    else {
                        JOptionPane.showMessageDialog(vRegis, "Data gagal diinput","GAGAL",JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    }
    
    class LoginListener implements ActionListener{
        
        @Override
        public void actionPerformed(ActionEvent e) {
            Object x = e.getSource();
            if (x.equals(vLogin.getbtnLogin())){
                int log = akun.login(vLogin.getUsername(),vLogin.getPassword());
                if(log==1){
                    vLogin.dispose();
                    new ControllerUserMenu(akun.getData(vLogin.getUsername()));
                }else if(log==2){
                    vLogin.dispose();
                    new ControllerMenuAdmin();
                }
                else{
                    JOptionPane.showMessageDialog(vLogin, "Username atau password salah","GAGAL",JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    
}
