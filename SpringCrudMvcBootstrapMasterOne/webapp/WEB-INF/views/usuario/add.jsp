<%@page import="br.com.java.model.Usuario.Funcao"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
 prefix="sec"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta charset="UTF-8">
<title><spring:message code="criar.titulo" /></title>
<link href="<spring:url value="/resources/css/bootstrap.min.css" />"
 rel="stylesheet">
<link href="<spring:url value="/resources/css/geral.css" />"
 rel="stylesheet">
<link href="<spring:url value="/resources/css/jquery-ui.min.css" />"
 rel="stylesheet">
</head>
<body>

 <div class="container">
  <a href="?lang=en"> <img alt="English"
   src="<spring:url value="/resources/images/eua.jpg"    />"></a> <a
   href="?lang=pt_br"><img alt="Portugues"
   src="<spring:url value="/resources/images/brasil.jpg" />"></a>
  <c:url var="post_url" value="/usuario/add/salvar" />
  <form:form id="form-add" action="${post_url}"
   modelAttribute="usuario" method="post" class="form-signin">

   <h2 class="form-signin-heading">
    <spring:message code="criar.titulo" />
   </h2>
   <c:if test="${param.message != null }">
    <span class="alert alert-sucess alert-warning-login">${param.message}</span>
   </c:if>
   <c:if test="${not empty message}"><div class="message green">${message}</div></c:if>

   <form:hidden path="id"/>

   <!--- entrada nome -->
   <form:errors path="nome" element="div" class=" alert alert-warning" />
   <spring:message code="criar.input.nome" var="nameLabel" />
   <form:input path="nome" class="form-control"
    placeholder="${nameLabel }" required="true" autofocus="true" />

   <!-- Entarada Data Nascimento -->
   <form:errors path="DataNascimento" element="div"
    class="alert alert-warning" />
   <spring:message code="criar.input.dataNascimento" var="dataNascimento" />
   <form:input path="dataNascimento" readonly="readonly"
    id="dataNascimento-input" class="form-control"
    placeholder="${dataNascimento }" required="true" />

   <!-- Entrada Nome -->
   <form:errors path="nomeUsuario" element="div"
    class="alert alert-teste alert-warning" />
    
   <div id="user-alert" style="display: none"
    class="alert alert-warning"></div>
   <spring:message code="criar.input.nomeUsuario" var="nomeUsuariolabel" />
   <form:input path="nomeUsuario" class="form-control"
    placeholder="${nomeUsuariolabel }" required="true"
    onblur="checkNomeUsuarioExiste{this.value}" />

   <!-- Entrada Senha -->
   <form:errors path="password" element="div"
    class="alert alert-warning" />
   <spring:message code="criar.input.password" var="passwordLabel" />
   <form:password path="password" id="password"
    onblur="checkPasswords()" class="form-control"
    placeholder="${passwordLabel }" required="true" />
   <div id="password-alert" style="display: none;"
    class="alert alert-warning"></div>
   <spring:message code="criar.input.confirmpassword"
    var="confirmPasswordLabel" />
   <input type="password" id="confirm-password"
    onblur="checkPasswords()" name="confirmarSenha" class="form-control"
    placeholder="${confirmPasswordLabel }" required="required" />

   <!-- Entrada Função -->
   <form:errors path="funcao" element="div" class="alert alert-warning" />
   <c:forEach items="<%=Funcao.values() %>" var="funcao">
    <spring:message code="usuario.funcao.${Funcao.nome()}" var="label" />
    <form:radiobutton path="funcao" value="${funcao }" label="${label }"
     required="true" />&nbsp
          </c:forEach>

   <div class="form-actions">
    <button type="submit" id="btn-salvar"
     class="btn btn-lg btn-primary btn-block">
     <spring:message code="criar.input.cadastra"/>
    </button>
    <sec:authorize access="isAnonymous()" var="usuarioDeslogado"/>
    <c:choose>
     <c:when test="${usuarioDeslogado }">
      <c:url var="login_url" value="/login/" />
      <a href="${login_url }" class="btn btn-lg btn-primary btn-block"><spring:message
        code="login.titulo" /></a>
     </c:when>
     <c:otherwise>
      <c:url var="home_url" value="/" />
      <a href="${home_url }" class="btn btn-lg btn-primay btn-block"><spring:message
        code="criar.input.voltar" /></a>
     </c:otherwise>
    </c:choose>
   </div>
  </form:form>
  <script type="text/javascript"
   src="<spring:url value="/resources/js/jquery.js" />"></script>
  <script type="text/javascript"
   src="<spring:url value="/resources/js/jquery-ui.min.js" />"></script>
  <script type="text/javascript">

  $( document ).ready(function() {
        var date = new Date();
           $( "#dataNascimento-input" ).datepicker({ 
             yearRange: date.getFullYear() - 150 + ":" +  date.getFullYear(),
             changeMonth: true,
             changeYear: true, 
             maxDate: "+0 +0",
             dateFormat: "dd/mm/yy",
           }).attr('readonly','readonly');
       });

             $('form-add').submit(function(){
                 return checkPasswords();
             });

             function checkPasswords() {
           var password = $("#password").val();
           var confirmPassword = $("#confirm-password").val();
           if(password && confirmPassword && password == confirmPassword){
            $("#password-alert").hide().html("");
            return true;
           } else if(password && confirmPassword && password != confirmPassword){
            $("#password-alert").show().html("<spring:message code='criar.message.senhasIguais'/>");
            return false;
           } else {
            $("#password-alert").hide().html("");
            return false;
            
           }
          }
             function checkNomeUsuarioExiste(nomeUsuario){
           if(username){
            
            var url = "<spring:url value="/usuario/checkusuarionome" />";
             $.ajax({
             url:url,
             data: {nomeUsuario:nomeUsuario},
          type : "GET",
             success: function(data){
             
              if(data === 'true'){ 
               $(":submit").attr("disabled", true);
               $("#user-alert").show().html("<spring:message code='criar.message.usuarioExistente.parte1'/><strong>" + nomeUsuario + "</strong><spring:message code='criar.message.usuarioExistente.parte2'/>"  );
              } else {
                  $(":submit").removeAttr("disabled");
               $("#user-alert").hide().html("");
              }
                    //alert("Data: " + data );
             } 
               });
           }
          }
 </script>

 </div>
</body>
</html>