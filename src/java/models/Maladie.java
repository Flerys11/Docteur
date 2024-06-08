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
public class Maladie {
    
    private int idmaladie;
    
    private String idsymptome;
    
    private int entre;
    
    private int et;
    
    private String nom;

    public int getIdmaladie() {
        return idmaladie;
    }

    public void setIdmaladie(int idmaladie) {
        this.idmaladie = idmaladie;
    }

    public String getIdsymptome() {
        return idsymptome;
    }

    public void setIdsymptome(String idsymptome) {
        this.idsymptome = idsymptome;
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

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Maladie() {
    }

    public Maladie(int idmaladie, String idsymptome, int entre, int et, String nom) {
        this.idmaladie = idmaladie;
        this.idsymptome = idsymptome;
        this.entre = entre;
        this.et = et;
        this.nom = nom;
    }
    
    
    public Maladie Mal(String nom) {
        this.nom = nom;
        return null;
    }
    

    @Override
    public String toString() {
        return "Maladie{" + "idmaladie=" + idmaladie + ", idsymptome=" + idsymptome + ", entre=" + entre + ", et=" + et + ", nom=" + nom + '}';
    }
    
    
    
    
    
    public Maladie[] getMaladie(Connection connection ) {
             List<Maladie> donneeMaladie = new ArrayList<>();
           
            PreparedStatement preparedStatement = null;
            ResultSet rs = null;

            try {
                    connection = getConnection();
                    preparedStatement = connection.prepareStatement("SELECT * FROM soinMedical as sm join maladie as m on sm.idMaladie = m.id");
                    rs = preparedStatement.executeQuery();

                    while (rs.next()) {
                        int idmaladie = rs.getInt("idmaladie");
                        String idsymptome = rs.getString("idsymptome");
                        int entre = rs.getInt("entre");
                        int et = rs.getInt("et");
                        String nom = rs.getString("nom");

                        donneeMaladie.add(new Maladie(idmaladie, idsymptome, entre, et,nom));
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

               return donneeMaladie.toArray(new Maladie[0]);
           }
    
    
        private static boolean isPercentageMatch(String fullString, String subString, int percentage) {
            if (fullString.length() == 0 || subString.length() == 0) {
                return false;
            }

            int subStringLength = subString.length();
            int fullStringLength = fullString.length();
            int matchThreshold = (percentage * fullStringLength) / 100;
            
            int matchCount = 0;
            for (int i = 0; i <= fullStringLength - subStringLength; i++) {
                if (fullString.regionMatches(i, subString, 0, subStringLength)) {
                    matchCount++;
                }
            }

            return matchCount >= matchThreshold;
        }
    
    
        List<Maladie> getListMaladie(int idpatient, Connection conn) {
            List<Maladie> result = new ArrayList<>();
            Maladie maladie = new Maladie();
            String symp = "";
            
            
            Maladie[] donneeM = maladie.getMaladie(conn);
            Symptome symptome = new Symptome();
            Symptome[] donneeS = symptome.getSymptome(conn, idpatient);

            for (Symptome symptome1 : donneeS) {
                symp = symptome1.getIdsymptome();
                for (Maladie maladie1 : donneeM) {
                    if (isPercentageMatch(maladie1.getIdsymptome(), symp, 100)) {
                        result.add(Mal(maladie1.getNom()));
                        //System.out.println("maladie "+maladie1.getNom());
                    } else {
                        
                        String malad = "Aucune Maladie indentifiée";
                        
                        result.add(Mal(malad));
                    }
                }
            }
            
          //System.out.println("symp " + symp);

            return result;
        }

    
    
    public static void main(String[] args) throws SQLException {
        
        Maladie maladie = new Maladie();
        int id = 1;
        Connection conn = getConnection(); 
        Maladie[] donne = maladie.getMaladie(null);
        
        for (Maladie maladie1 : donne) {
            System.out.println(maladie1.toString());
        }
        
       /* List<Maladie> donneMa = maladie.getListMaladie(id, conn);
        
        for (Maladie mala : donneMa) {
            System.out.println("maladie "+mala.getNom());
        }*/
    }



}
