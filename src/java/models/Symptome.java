/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import conn.Connexion;
import static conn.Connexion.getConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tmehyg
 */
public class Symptome {
    
    private int idpatient;
    
    private String idsymptome; 
    
    private int etat;

    public String getIdsymptome() {
        return idsymptome;
    }

    public void setIdsymptome(String idsymptome) {
        this.idsymptome = idsymptome;
    }
    

    public int getIdpatient() {
        return idpatient;
    }

    public void setIdpatient(int idpatient) {
        this.idpatient = idpatient;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public Symptome() {
    }

    public Symptome(int idpatient, String idsymptome, int etat) {
        this.idpatient = idpatient;
        this.idsymptome = idsymptome;
        this.etat = etat;
    }

    @Override
    public String toString() {
        return "Symptome{" + "idpatient=" + idpatient + ", idsymptome=" + idsymptome + ", etat=" + etat + '}';
    }
    
    
    
     public Symptome[] getSymptome(Connection connection , int idPat) {
             List<Symptome> donneeSymptome = new ArrayList<>();
            PreparedStatement preparedStatement = null;
            ResultSet rs = null;

            try {
                    connection = getConnection();
                    preparedStatement = connection.prepareStatement("select * from symptome_patient where idpatient = ?");
                    preparedStatement.setInt(1, idPat);
                    rs = preparedStatement.executeQuery();

                    while (rs.next()) {
                        int idpatient = rs.getInt("idpatient");
                        String idsymptome = rs.getString("idsymptome");
                        int etat = rs.getInt("etat");

                        donneeSymptome.add(new Symptome(idpatient, idsymptome, etat));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
            } finally {
                   try {
                       if (rs != null) {
                           rs.close();
                       }
                       if (preparedStatement != null) {
                           preparedStatement.close();
                       }
                       if (connection != null && !connection.isClosed()) {
                           connection.close();
                       }
                   } catch (SQLException e) {
                       e.printStackTrace();
                   }
               }

               return donneeSymptome.toArray(new Symptome[0]);
           }
     
     
         public void insert(int idP,String idS,int etat, Connection conn) throws Exception{
        String sql="INSERT into symptome_patient(idpatient,idsymptome,etat) values (%s,'%s',%s)";
        sql=sql.format(sql,idP,idS,etat);
        if(conn == null){
            conn=Connexion.getConnection();
        }
        conn.createStatement().executeUpdate(sql);
    }
     
     
     public static void main(String[] args) throws Exception {
        
        int id = 1;
        //String ids = "S001";
        //int etat = 4;
         Symptome symptome = new Symptome();
         
         Symptome[] donne = symptome.getSymptome(null, id);
         
         for (Symptome symptome1 : donne) {
             System.out.println(symptome1.toString());
         }
         //symptome.insert(id, ids, etat, null);
    }
}
