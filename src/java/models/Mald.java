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
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Tmehyg
 */
public class Mald {
    
    private String Nom_Maladie;

    public String getNom_Maladie() {
        return Nom_Maladie;
    }

    public void setNom_Maladie(String Nom_Maladie) {
        this.Nom_Maladie = Nom_Maladie;
    }

    public Mald() {
    }

    public Mald(String Nom_Maladie) {
        this.Nom_Maladie = Nom_Maladie;
    }

    @Override
    public String toString() {
        return "Mald{" + "Nom_Maladie=" + Nom_Maladie + '}';
    }
    
    
    
    
            boolean containsSymptome(int idmaladie, String idsymptome, Connection conn) {
            try (PreparedStatement statement = conn.prepareStatement("SELECT COUNT(*) FROM soinMedical WHERE idmaladie = ? AND idsymptome = ?")) {
                statement.setInt(1, idmaladie);
                statement.setString(2, idsymptome);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        //System.out.println("eto");
                        return resultSet.getInt(1) > 0;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        }
            
            
            /*public List<Mald> getLisMaladie(int idpatient, Connection conn) throws SQLException {
                List<Mald> result = new ArrayList<>();
                Symptome symptome = new Symptome();
                Maladie maladie = new Maladie();

                Symptome[] donneeS = symptome.getSymptome(conn, idpatient);
                Maladie[] donneeM = maladie.getMaladie(conn);

                for (Symptome sympt : donneeS) {
                    Set<String> maladiesCorrespondantes = new HashSet<>();
                    for (Maladie mald : donneeM) {
                        if (mald.getIdsymptome().equalsIgnoreCase(sympt.getIdsymptome())
                                && sympt.getEtat() >= mald.getEntre() && sympt.getEtat() <= mald.getEt()) {
                            maladiesCorrespondantes.add(mald.getNom());
                        }
                    }

                    if (maladiesCorrespondantes.isEmpty()) {
                        System.out.println("Aucune maladie identifiée pour le symptôme: " + sympt.getIdsymptome());
                    } else {
                        // Ajouter les maladies correspondantes à la liste result
                        for (String maladieNom : maladiesCorrespondantes) {
                            result.add(new Mald(maladieNom));
                        }
                    }
                }

                return result;
            }*/
           
            
        
           /*public List<Mald> getLisMaladie(int idpatient, Connection conn) throws SQLException {
                   conn = getConnection();
                   List<Mald> result = new ArrayList<>();
                   Maladie maladie = new Maladie();
                   Symptome symptome = new Symptome();

                   // Ajout : Obtenir les informations spécifiques au patient
                   Patient patient = getPatientInfo(idpatient, conn);

                   // Vérifier si le patient a été trouvé
                   if (patient != null) {
                       Symptome[] donneeS = symptome.getSymptome(conn, idpatient);
                       Maladie[] donneeM = maladie.getMaladie(conn);

                       for (Maladie maladie1 : donneeM) {
                           boolean maladieIdentifiee = true;
                           for (Symptome symptome1 : donneeS) {
                               if (!containsSymptome(maladie1.getIdmaladie(), symptome1.getIdsymptome(), conn)) {
                                   maladieIdentifiee = false;
                                   break;
                               }
                           }
                           if (maladieIdentifiee) {
                               Mald nouvelleMaladie = new Mald(maladie1.getNom());
                               System.out.println("eee " + nouvelleMaladie);
                               if (!result.stream().anyMatch(m -> m.getNom_Maladie().equalsIgnoreCase(nouvelleMaladie.getNom_Maladie()))) {

                                   System.out.println("valiny " + nouvelleMaladie);
                                   result.add(nouvelleMaladie);
                               }
                           }
                       }

                       if (result.isEmpty()) {
                           String malad = "Aucune Maladie identifiée";
                           result.add(new Mald(malad));
                       }
                   } else {
                       // Patient non trouvé, ajoutez une logique en conséquence
                       System.out.println("Patient non trouvé");
                   }

                   return result;
               }*/


    
  
        public List<Mald> getLisMaladie(int idpatient, Connection conn) {
            List<Mald> result = new ArrayList<>();
            Maladie maladie = new Maladie();
            Symptome symptome = new Symptome();

            Symptome[] donneeS = symptome.getSymptome(conn, idpatient);
            Maladie[] donneeM = maladie.getMaladie(conn);

            Set<String> symptomesPatient = new HashSet<>();
            for (Symptome symptome1 : donneeS) {
                symptomesPatient.add(symptome1.getIdsymptome());
            }

            Map<String, Set<String>> symptomesMaladies = new HashMap<>();

            for (Maladie maladie1 : donneeM) {
                Set<String> symptomesMaladie = symptomesMaladies.getOrDefault(maladie1.getNom(), new HashSet<>());
                symptomesMaladie.addAll(Arrays.asList(maladie1.getIdsymptome().split(",")));
                symptomesMaladies.put(maladie1.getNom(), symptomesMaladie);
            }

            for (Map.Entry<String, Set<String>> entry : symptomesMaladies.entrySet()) {
                String nomMaladie = entry.getKey();
                Set<String> symptomesMaladie = entry.getValue();
                //System.out.println("Symptomes pour la maladie " + nomMaladie + " : " + symptomesMaladie);

                if (symptomesPatient.containsAll(symptomesMaladie)) {
                    Mald nouvelleMaladie = new Mald(nomMaladie);
                    result.add(nouvelleMaladie);
                }
            }

            if (result.isEmpty()) {
                String malad = "Aucune Maladie identifiée";
                result.add(new Mald(malad));
            }

            return result;
        }

    
          public static boolean isPercentageMatch(String fullString, String subString, int percentage) {
            if (fullString.length() == 0 || subString.length() == 0) {
                return false;
            }

            int subStringLength = subString.length();
            int fullStringLength = fullString.length();
            int matchThreshold = (percentage * fullStringLength) / 100;

            int matchCount = 0;
            for (int i = 0; i <= fullStringLength - subStringLength; i++) {
                if (fullString.regionMatches(true, i, subString, 0, subStringLength)) {
                    matchCount++;
                }
            }

            return matchCount >= matchThreshold;
        }





       

    public static void main(String[] args) throws SQLException {
        Mald maladInstance = new Mald();
        int id =1;

        Connection conn = getConnection(); 
        List<Mald> maladies = maladInstance.getLisMaladie(id, null);
        for (Mald maladie : maladies) {
            System.out.println("Nom de la maladie : " + maladie.getNom_Maladie());
        }
}
}
