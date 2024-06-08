<%-- 
    Document   : list
    Created on : 7 janv. 2024, 21:56:35
    Author     : Tmehyg
--%>
<%@ page import="models.Patient" %>
<%@ page import="models.ListSymp" %>
<%@ page import="java.util.List" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% List<Patient> result = (List<Patient>)request.getAttribute("result"); 
   //Double valeurCoutTotal = (Double) request.getAttribute("valeur_cout_total");
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
    <table class="table">
      <thead>
        <tr>
          <th scope="col">NUMERO</th>
          <th scope="col">NOM</th>
          <th scope="col">AGE</th>
        </tr>
      </thead>
      <tbody>
<%
    if (result != null && !result.isEmpty()) {
        for (int i = 0; i < result.size(); i++) {
            Patient indice = result.get(i);
%>          
        <tr>
          <th scope="row"><%= indice.getId() %></th>
          <td><%= indice.getNom() %></td>
          <td><%= indice.getAge() %> </td>
          <td><a href="GetResultat?id=<%= indice.getId() %>"> Voir Details</a> </td>
        </tr>
<%
    }
} else {
%>
<p>Aucun produit trouvé.</p>
<%
    }
%>

<div class="container mt-5">
    
      </tbody>
    </table>
  <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal">
    Ajout Parametre
  </button> 

<div class="modal" id="myModal">
    <div class="modal-dialog d-flex align-items-center">
      <div class="modal-content">
  
        <div class="modal-header">
          <h4 class="modal-title">Ajouter un Parametre</h4>
          <button type="button" class="close" data-dismiss="modal">&times; </button>
        </div>
        
        <form action="NewServlet" method="POST">
            <div class="modal-body">
                
                    <div class="col-auto">
                        <label for="inputPassword2" class="visually-hidden">Patient</label>
                                 <select class="form-control" id="basic-default-name" name="idpatient">
                                    <% 
                                           for (int i = 0; i < result.size(); i++) {
                                               Patient indice = result.get(i);
                                        %>
                                        <option value="<%= indice.getId() %>"><%= indice.getNom() %></option>
                                        <%
                                           }
                                        %>
                                  </select>
                    </div>
                                  <br>
                    <div class="col-auto">
                        <label for="inputPassword2" class="visually-hidden">Parametre</label>
                                   <select class="form-control" id="basic-default-name" name="symp">
                                    <% List<ListSymp> li = (List<ListSymp>) request.getAttribute("re");
                                           for (int i = 0; i < li.size(); i++) {
                                               ListSymp indice = li.get(i);
                                        %>
                                        <option value="<%= indice.getId() %>"><%= indice.getNom() %></option>
                                        <%
                                           }
                                        %>
                                  </select>
                    </div> 
                                  <br>
                    <div class="col-auto">
                        <label for="inputPassword2" class="visually-hidden">Etat</label>
                        <input type="number" class="form-control" id="inputPassword2" name="etat" placeholder="Etat">
                    </div>
            </div>

            <div class="modal-footer">
            <button type="submit" class="btn btn-primary">Ajouter</button>
            <button type="button" class="btn btn-secondary" data-dismiss="modal">Fermer</button>
        </form>
        </div>
  
      </div>
    </div>
  </div>
</div>   

    </body>
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</html>
