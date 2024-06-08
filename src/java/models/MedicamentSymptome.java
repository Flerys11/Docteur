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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.util.Pair;

/**
 *
 * @author Tmehyg
 */
public class MedicamentSymptome {
    
    private String idsymptome;
    
    private int idmedicament;
    
    private double prix;
    
    private String nom_medicament;
    
    private String nom_symptome;
    
    private double efficacite;

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

    public String getNom_symptome() {
        return nom_symptome;
    }

    public void setNom_symptome(String nom_symptome) {
        this.nom_symptome = nom_symptome;
    }

    public double getEfficacite() {
        return efficacite;
    }

    public void setEfficacite(double efficacite) {
        this.efficacite = efficacite;
    }
    

    public MedicamentSymptome() {
    }

    public MedicamentSymptome(String idsymptome, int idmedicament, double prix, String nom_medicament, String nom_symptome, double efficacite) {
        this.idsymptome = idsymptome;
        this.idmedicament = idmedicament;
        this.prix = prix;
        this.nom_medicament = nom_medicament;
        this.nom_symptome = nom_symptome;
        this.efficacite = efficacite;
    }

    @Override
    public String toString() {
        return "MedicamentSymptome{" + "idsymptome=" + idsymptome + ", idmedicament=" + idmedicament + ", prix=" + prix + ", nom_medicament=" + nom_medicament + ", nom_symptome=" + nom_symptome + ", efficacite=" + efficacite + '}';
    }
    
    
    
    
         public MedicamentSymptome[] getMedicamentSymptome(Connection connection) {
             List<MedicamentSymptome> donneeMSymptome = new ArrayList<>();
            PreparedStatement preparedStatement = null;
            ResultSet rs = null;

            try {
                    connection = getConnection();
                    preparedStatement = connection.prepareStatement("select * from v2_getMedicament_Symptome");
                    rs = preparedStatement.executeQuery();

                    while (rs.next()) {
                        String idsymptome = rs.getString("idsymptome");
                        int idmedicament = rs.getInt("idmedicament");
                        double prix = rs.getDouble("prix");
                        String nom_medicament = rs.getString("nom_medicament");
                        String nom_symptome = rs.getString("nom_symptome");
                        double efficacite = rs.getDouble("efficacite");
                        donneeMSymptome.add(new MedicamentSymptome(idsymptome, idmedicament, prix,nom_medicament,nom_symptome,efficacite));
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

               return donneeMSymptome.toArray(new MedicamentSymptome[0]);
           }
         
         
          public List<MedicamentSymptome> listCompart(int idpatient) throws Exception {
            List<MedicamentSymptome> result = new ArrayList<>();

            MedicamentSymptome medic = new MedicamentSymptome();
            MedicamentSymptome[] donneeMedic = medic.getMedicamentSymptome(null);

            Symptome symptome = new Symptome();
            Symptome[] donneSymptome = symptome.getSymptome(null, idpatient);
            
              for (Symptome symptome1 : donneSymptome) {              
              Map<Integer, Integer> occurrences = new HashMap<>();

                for (MedicamentSymptome medicamentSymptome : donneeMedic) {
                    int idMedicament = medicamentSymptome.getIdmedicament();
                    occurrences.put(idMedicament, occurrences.getOrDefault(idMedicament, 0) +1);

                }

                            double maxEtat = Double.MIN_VALUE; 
                            MedicamentSymptome maxEtatMedicament = null;
                            double prix = 0.0;
                            double etat = 0.0;
                            

                            for (MedicamentSymptome medicamentSymptome : donneeMedic) {
                                int idMedicament = medicamentSymptome.getIdmedicament();

                                if (occurrences.get(idMedicament) > 1) {
                                      etat = symptome1.getEtat() / medicamentSymptome.getEfficacite();
                                      System.out.println("et " +symptome1.getEtat());
                                      //Collections.sort(inverses, Collections.reverseOrder());
                                     //etat = sorted(donnee, key=lambda x: x[0] / x[1], reverse=True);
                                     prix = medicamentSymptome.getPrix() * etat;
                                     //System.out.println("etat "+ etat +" s "+  medicamentSymptome.getIdsymptome());

                                    if (etat > maxEtat) {
                                        maxEtat = etat;
                                        maxEtatMedicament = medicamentSymptome; 
                                        //System.out.println("etat "+ maxEtat);
                                    }
                                }
                            }

                            if (maxEtatMedicament != null) {
                                
                                result.add(new MedicamentSymptome(
                                    maxEtatMedicament.getIdsymptome(),
                                    maxEtatMedicament.getIdmedicament(),
                                    prix,
                                    maxEtatMedicament.getNom_medicament(),
                                    maxEtatMedicament.getNom_symptome(),
                                    etat
                                ));
                            }
              }
        
                return result;
              
          }
         
        
         
         public List<MedicamentSymptome> listCompart2(int idpatient) throws Exception {
             double etatf= 0.0;
             double prixf = 0.0;
             double somme = 0.0;
             List<MedicamentSymptome> result = new ArrayList<>();
             SymptomePatient sympt = new SymptomePatient();
             SymptomePatient[] donneSympt = sympt.getListSymPat(idpatient, null);
             MedicamentSymptome medic = new MedicamentSymptome();
             List<MedicamentSymptome> result1 = medic.listCompart(idpatient);
             
            for (SymptomePatient symptomePatient : donneSympt) {
                etatf = symptomePatient.getEtat() / symptomePatient.getEfficacite();
                prixf = etatf * symptomePatient.getPrix();
                //System.out.println("etatf " + etatf + " prixf " +prixf + " id "+ symptomePatient.getIdsymptome());
               
                if ((symptomePatient.getIdmedicament() == result1.get(0).getIdmedicament() && !symptomePatient.getIdsymptome().equals(result1.get(0).getIdsymptome()))
                        || (symptomePatient.getIdsymptome().equals(result1.get(0).getIdsymptome()) && symptomePatient.getIdmedicament() != result1.get(0).getIdmedicament())) {
                         somme += prixf;
                        System.out.println("prix " + prixf + " etat " + etatf +" ids "+symptomePatient.getIdsymptome() + " med " +symptomePatient.getNom_medicament());
                        
                }
            }
             System.out.println("mm " +result1.get(0).getPrix());
            
            if(result1.get(0).getPrix() > somme ) {
                for (SymptomePatient symptomePatient : donneSympt) {
                     //System.out.println("s " +somme);
                etatf = symptomePatient.getEtat() / symptomePatient.getEfficacite();
                prixf = etatf * symptomePatient.getPrix();
                    
                               result.add(new MedicamentSymptome(
                             symptomePatient.getIdsymptome(),
                             symptomePatient.getIdmedicament(),
                             prixf,
                             symptomePatient.getNom_medicament(),
                             symptomePatient.getNom_symptome(),
                             etatf
                                ));
                    }
                    
            }else {
                for (SymptomePatient symptomePatient : donneSympt) {
                     //System.out.println("s " +somme);
                etatf = symptomePatient.getEtat() / symptomePatient.getEfficacite();
                prixf = etatf * symptomePatient.getPrix();
                
                    if (!symptomePatient.getIdsymptome().equals(result1.get(0).getIdsymptome()) &&
                            symptomePatient.getIdmedicament() != result1.get(0).getIdmedicament()) {
                       // System.out.println("etatf "+etatf + " sy "+symptomePatient.getIdsymptome());
                        ///System.out.println("etatf2 "+result1.get(1).getEfficacite() + " sy "+symptomePatient.getIdsymptome());
                               result.add(new MedicamentSymptome(
                             symptomePatient.getIdsymptome(),
                             symptomePatient.getIdmedicament(),
                             prixf,
                             symptomePatient.getNom_medicament(),
                             symptomePatient.getNom_symptome(),
                             etatf
                                ));

                                result.add(new MedicamentSymptome(
                                       result1.get(0).getIdsymptome(),
                                       result1.get(0).getIdmedicament(),
                                       result1.get(0).getPrix(),
                                       result1.get(0).getNom_medicament(),
                                       result1.get(0).getNom_symptome(),
                                       result1.get(0).getEfficacite()
                                ));
                            }
                    }
                }
            return result;
         }    
        
            public List<MedicamentSymptome> TriePrix(int idpatient) throws Exception {
                List<MedicamentSymptome> modifiedList = new ArrayList<>();
                MedicamentSymptome medic = new MedicamentSymptome();

                List<MedicamentSymptome> donnePatient = medic.listCompart2(idpatient);
                Collections.sort(donnePatient, Comparator.comparing(MedicamentSymptome::getIdsymptome)
                        .thenComparing(MedicamentSymptome::getEfficacite));

                String currentIdsymptome = null;
                for (MedicamentSymptome medicamentSymptome : donnePatient) {
                    if (currentIdsymptome == null || !medicamentSymptome.getIdsymptome().equals(currentIdsymptome)) {
                        modifiedList.add(medicamentSymptome);
                        currentIdsymptome = medicamentSymptome.getIdsymptome();
                    }
                }

                // Créer une nouvelle liste avec les éléments modifiés
                List<MedicamentSymptome> resultList = new ArrayList<>();
                for (MedicamentSymptome medicamentSymptome : modifiedList) {
                    resultList.add(new MedicamentSymptome(
                        medicamentSymptome.getIdsymptome(),
                        medicamentSymptome.getIdmedicament(),
                        medicamentSymptome.getPrix(),
                        medicamentSymptome.getNom_medicament(),
                        medicamentSymptome.getNom_symptome(),
                        medicamentSymptome.getEfficacite()
                    ));
                }

                return resultList;
            }

         
         public static void main(String[] args) throws Exception {
        
             
             MedicamentSymptome medic = new MedicamentSymptome();
             
             MedicamentSymptome[] donnee = medic.getMedicamentSymptome(null);
             int id = 1;
             List<MedicamentSymptome> result1 = medic.TriePrix(id);
             
             for (MedicamentSymptome medicamentSymptome : result1) {
                 
                 System.out.println("etat "+ medicamentSymptome.getEfficacite() + " sympt "+medicamentSymptome.getIdsymptome()); 
                 
             }
             //List<MedicamentSymptome> result = medic.listCompart(id);
            // List<MedicamentSymptome> result = medic.TriePrix(id);
             
                    //System.out.println("vl "+result.get(0).getIdmedicament());
             /*for (MedicamentSymptome medicamentSymptome : result) {
                 
                 System.out.println("idme "+ medicamentSymptome.getIdmedicament() +" symp " +medicamentSymptome.getIdsymptome() + " effic "+medicamentSymptome.getEfficacite()
                 + " prix " + medicamentSymptome.getPrix());
             }*/
             
             /*for (MedicamentSymptome medicamentSymptome : donnee) {
                 
                 System.out.println("valiny " +medicamentSymptome.getNom_medicament());
                 
             }*/
    }
}
