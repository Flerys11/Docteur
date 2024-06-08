<%-- 
    Document   : list
    Created on : 7 janv. 2024, 21:56:35
    Author     : Tmehyg
--%>
<%@ page import="models.SymptomePatient" %>
<%@ page import="models.MedicamentSymptome" %>
<%@ page import="models.Mald" %>
<%@ page import="java.util.List" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% SymptomePatient[] result = (SymptomePatient[])request.getAttribute("result"); 
    List<Mald> result2 = (List<Mald>)request.getAttribute("result2"); 
   Double valeurCoutTotal = (Double) request.getAttribute("prix_total");
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
      <link rel="stylesheet" href="assets/vendor/css/core.css" class="template-customizer-core-css" />
    <link rel="stylesheet" href="assets/vendor/css/theme-default.css" class="template-customizer-theme-css" />
        <title>JSP Page</title>
    </head>
    <body>

<div class="container">
   <h1>Resultats</h1>
   <h3>Nom du Patient : <%= result[0].getNom_patient() %></h3>
<%
    if (result2 != null && result2.size() > 0) {
        for (int i = 0; i < result2.size(); i++) {
            Mald indice = result2.get(i);
%>  
            <h3>Maladie : <%= indice.getNom_Maladie() %></h3>
<%
        }
    } else {
%>
        <p>Aucune maladie trouvée.</p>
<%
    }
%>

    <table class="table">
      <thead>
        <tr>
          <th scope="col">NUMERO</th>
          <th scope="col">NOM</th>
          <th scope="col">SYMPTOME</th>
          <th>NOM_MEDICAMENT</th>
          <th scope="col">UNITE</th>
        </tr>
      </thead>
      <tbody>
     <% List<MedicamentSymptome> l = (List<MedicamentSymptome>) request.getAttribute("result1");
        for (int i = 0; i < l.size(); i++) {
          MedicamentSymptome indice = l.get(i);
      %>
                                     

          <tr>
              <th> <%= indice.getIdmedicament() %></th>
              <th> <%= indice.getNom_symptome() %></th>
              <th><%= indice.getIdsymptome() %></th>
              <th><%= indice.getNom_medicament() %></th>
              <th> <%= indice.getEfficacite() %></th>
          </tr>
         <%
           }
         %>
      </tbody>
    </table>


         
</div>   

    </body>
</html>
