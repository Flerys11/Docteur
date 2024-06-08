/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

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
public class SymptomePatient {
    
    private int id;
    
    private int idpatient;
    
    private String idsymptome;
    
    private int idmedicament;
    
    private int entre;
    
    private int et;
    
    private double prix;
    
    private String nom_medicament;
    
    private int type;
    
    private String nom_symptome;
    
    private String nom_patient;
    
    private int etat;
    
    private int fois;
    
    private double prix_total;
    
    private double efficacite;

    public double getEfficacite() {
        return efficacite;
    }

    public void setEfficacite(double efficacite) {
        this.efficacite = efficacite;
    }
    
    

    public int getFois() {
        return fois;
    }

    public void setFois(int fois) {
        this.fois = fois;
    }

    public double getPrix_total() {
        return prix_total;
    }

    public void setPrix_total(double prix_total) {
        this.prix_total = prix_total;
    }
    
    

    public int getIdpatient() {
        return idpatient;
    }

    public void setIdpatient(int idpatient) {
        this.idpatient = idpatient;
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdsymptome() {
        return idsymptome;
    }

    public void setIdsymptome(String idsymptome) {
        this.idsymptome = idsymptome;
    }



    public int getIdmedicament() {
        return idmedicament;
    }

    public void setIdmedicament(int idmedicament) {
        this.idmedicament = idmedicament;
    }

    public int getEntre() {
        return entre;
    }

    public void setEntre(int entre) {
        this.entre = entre;
    }

    public int getEt() {
        return et;
    }

    public void setEt(int et) {
        this.et = et;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public String getNom_medicament() {
        return nom_medicament;
    }

    public void setNom_medicament(String nom_medicament) {
        this.nom_medicament = nom_medicament;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getNom_symptome() {
        return nom_symptome;
    }

    public void setNom_symptome(String nom_symptome) {
        this.nom_symptome = nom_symptome;
    }

    public String getNom_patient() {
        return nom_patient;
    }

    public void setNom_patient(String nom_patient) {
        this.nom_patient = nom_patient;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public SymptomePatient() {
    }

    public SymptomePatient(int id, int idpatient, String idsymptome, int idmedicament, int entre, int et, double prix, String nom_medicament, int type, String nom_symptome, String nom_patient, int etat, int fois, double prix_total, double efficacite) {
        this.id = id;
        this.idpatient = idpatient;
        this.idsymptome = idsymptome;
        this.idmedicament = idmedicament;
        this.entre = entre;
        this.et = et;
        this.prix = prix;
        this.nom_medicament = nom_medicament;
        this.type = type;
        this.nom_symptome = nom_symptome;
        this.nom_patient = nom_patient;
        this.etat = etat;
        this.fois = fois;
        this.prix_total = prix_total;
        this.efficacite = efficacite;
    }

    @Override
    public String toString() {
        return "SymptomePatient{" + "id=" + id + ", idpatient=" + idpatient + ", idsymptome=" + idsymptome + ", idmedicament=" + idmedicament + ", entre=" + entre + ", et=" + et + ", prix=" + prix + ", nom_medicament=" + nom_medicament + ", type=" + type + ", nom_symptome=" + nom_symptome + ", nom_patient=" + nom_patient + ", etat=" + etat + ", fois=" + fois + ", prix_total=" + prix_total + ", efficacite=" + efficacite + '}';
    }

   


    
    
    
        public SymptomePatient[] getSymPat(int idPat, Connection connection ) {
             List<SymptomePatient> donneePatient = new ArrayList<>();
           
            PreparedStatement preparedStatement = null;
            ResultSet rs = null;

            try {
                    connection = getConnection();
                    preparedStatement = connection.prepareStatement("SELECT * FROM v_getPrix WHERE idpatient = ?");
                    preparedStatement.setInt(1, idPat);

                    rs = preparedStatement.executeQuery();

                    while (rs.next()) {
                        int id = rs.getInt("id");
                        int idpatient = rs.getInt("idpatient");
                        String idsymptome = rs.getString("idsymptome");
                        int idmedicament = rs.getInt("idmedicament");
                        int entre = rs.getInt("entre");
                        int et = rs.getInt("et");
                        double prix = rs.getDouble("prix");
                        String nom_medicament = rs.getString("nom_medicament");
                        int type = rs.getInt("type");
                        String nom_symptome = rs.getString("nom_symptome");
                        String nom_patient = rs.getString("nom_patient");
                        int etat = rs.getInt("etat");
                        int fois = rs.getInt("fois");
                        double prix_total = rs.getDouble("prix_total");
                        donneePatient.add(new SymptomePatient(id, idpatient, idsymptome, idmedicament, entre, et, prix, nom_medicament, type, nom_symptome, nom_patient, etat,fois,prix_total,efficacite));
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

               return donneePatient.toArray(new SymptomePatient[0]);
           }
        
        
        public SymptomePatient[] getListSymPat(int idPat, Connection connection ) {
             List<SymptomePatient> donneePatient = new ArrayList<>();
           
            PreparedStatement preparedStatement = null;
            ResultSet rs = null;

            try {
                    connection = getConnection();
                    preparedStatement = connection.prepareStatement("SELECT * FROM  v_getSymPati WHERE idpatient = ?");
                    preparedStatement.setInt(1, idPat);

                    rs = preparedStatement.executeQuery();

                    while (rs.next()) {
                        int id = rs.getInt("id");
                        int idpatient = rs.getInt("idpatient");
                        String idsymptome = rs.getString("idsymptome");
                        int idmedicament = rs.getInt("idmedicament");
                        int entre = rs.getInt("entre");
                        int et = rs.getInt("et");
                        double prix = rs.getDouble("prix");
                        String nom_medicament = rs.getString("nom_medicament");
                        int type = rs.getInt("type");
                        String nom_symptome = rs.getString("nom_symptome");
                        String nom_patient = rs.getString("nom_patient");
                        double efficacite = rs.getDouble("efficacite");
                        int etat = rs.getInt("etat");
                        donneePatient.add(new SymptomePatient(id, idpatient, idsymptome, idmedicament, entre, et, prix, nom_medicament, type, nom_symptome, nom_patient, etat,fois,prix_total,efficacite));
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

               return donneePatient.toArray(new SymptomePatient[0]);
           }
        

        
        
        public static void main(String[] args) {
        
            SymptomePatient patient = new SymptomePatient();
            
            int idpatient = 1;
            SymptomePatient[] donnee = patient.getListSymPat(idpatient,null);
            
            for (SymptomePatient symptomePatient : donnee) {
                
                System.out.println("result :" +symptomePatient.toString());
            }
            
    }
    
}
