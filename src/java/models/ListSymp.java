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
public class ListSymp {
    
    private String id;
    
    private String nom;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public ListSymp() {
    }

    public ListSymp(String id, String nom) {
        this.id = id;
        this.nom = nom;
    }
    
    
         public List<ListSymp> getListS(Connection conn) throws  Exception{
           List<ListSymp> result = new ArrayList<>();
        String sql="  select * from symptome";
        //sql=sql.format(sql); 
        if(conn == null){
            conn=Connexion.getConnection();
        }
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while(rs.next()){
            String id = rs.getString("id");
            String nom = rs.getString("nom");
            result.add(new ListSymp(id,nom));
            conn.close();
        }
        
        return result;
    }
         
         
         
         public static void main(String[] args) throws Exception {
        
             ListSymp  list = new ListSymp();
             List<ListSymp> re = list.getListS(null);
             
             for (ListSymp listSymp : re) {
                 
                 System.out.println("valiny :"+listSymp.getId());
             }
             
    }
     
}
