/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import conn.Connexion;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tmehyg
 */
public class Patient {
    
    private int id;
    
    private String nom;
    
    private int Age;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int Age) {
        this.Age = Age;
    }

    public Patient() {
    }

    public Patient(int id, String nom, int Age) {
        this.id = id;
        this.nom = nom;
        this.Age = Age;
    }
    
    
    
       public List<Patient> getListPatient(Connection conn) throws  Exception{
        List<Patient> result = new ArrayList<>();
        String sql=" select * from patient";
        //sql=sql.format(sql); 
        if(conn == null){
            conn=Connexion.getConnection();
        }
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while(rs.next()){
            int id = rs.getInt("id");
            String nom = rs.getString("nom");
            int Age = rs.getInt("age");
            result.add(new Patient(id,nom,Age));
            conn.close();
        }
        
        return result;
    }
       
       
       public static void main(String[] args) throws Exception {
        
           Patient patient = new Patient();
           
           List<Patient> donnee = patient.getListPatient(null);
           
           for (Patient patient1 : donnee) {
               
               System.out.println("id :" +patient1.id + " nom :"+patient1.getNom() + " age :"+patient1.Age);
           }
    }
    
}
