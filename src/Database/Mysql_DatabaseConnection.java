package Database;


import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

public class Mysql_DatabaseConnection {
    protected static Connection con = null;
    protected static Statement stmt = null;
    private String url;
    private String user;
    private String pass;

    public Mysql_DatabaseConnection() {
        this.url = "jdbc:mysql://localhost/koseru";
        this.user = "root";
        this.pass = "";
    }
    
    public void connect(){
        try {
            con = DriverManager.getConnection(url, user, pass);
            stmt = con.createStatement();
            System.out.println("Database terhubung");
        } catch (SQLException ex){
            System.out.println(ex.getLocalizedMessage());
            if(ex.getMessage().equals("Communications link failure")){
                System.out.println("Database gagal");
            }
        }
    }
    
    public void disconnect(){
        try {
            con.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(Mysql_DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean manipulate(String query){
        boolean cek = false;
        try {
            int rows = stmt.executeUpdate(query);
            if (rows > 0) cek = true;
        } catch (SQLException ex) {
            Logger.getLogger(Mysql_DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cek;
    }
}
