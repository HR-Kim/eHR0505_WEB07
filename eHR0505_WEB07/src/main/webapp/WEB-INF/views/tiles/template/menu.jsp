<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="context" value="${pageContext.request.contextPath}" />
<form name="topForm" id="topForm" action="logout.do" method="post" >
</form>
<nav class="navbar navbar-default" >
    <div class="container-fluid"> 
        <div class="navbar-header"> 
            <a class="navbar-brand" href="${context}/main/main.do">HRLab</a>
        </div>
		<c:choose>
			<c:when test="${not empty user && not empty user.name}">
		        <ul class="nav navbar-nav">
		            <li class="nav-item"><a class="nav-link" href="${context}/user/get_retrieve.do" >사용자관리</a></li>
		            <li class="nav-item"><a class="nav-link" href="${context}/board/get_retrieve.do" >게시판</a></li>
		            <li class="nav-item"><a class="nav-link" href="${context}/chart/pie_chart_view.do" >파이Chart</a></li>
		            <li class="nav-item"><a class="nav-link" href="${context}/chart/line_chart_view.do" >라인Chart</a></li>
		            <li class="nav-item"><a class="nav-link" href="${context}/board_attr/get_retrieve.do" >첨부게시판</a></li>
		        </ul>
		        <ul class="nav navbar-nav navbar-right">
		            <li><a href="#"><span class="glyphicon glyphicon-user"></span>${user.name}</a></li>
		            <li><a href="javascript:doLogout();"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
		        </ul>        	
			</c:when>
			<c:otherwise>
		        <ul class="nav navbar-nav">
		            <li class="nav-item"><a class="nav-link" href="${context}/board/get_retrieve.do" >게시판</a></li>		           
		        </ul>
		        <ul class="nav navbar-nav navbar-right">
		            <li><a href="${context}/login/NotLogged.do"><span class="glyphicon glyphicon-log-in"></span> LogIn</a></li>
		        </ul> 			
			</c:otherwise>
		</c:choose>
    </div>		
</nav>

<script type="text/javascript">
function doLogout(){
    if(false==confirm("로그 아웃 하시겠습니까?"))return;
    var frm = document.topForm;
    frm.action="<c:url value='/user/logout.do'/>";
    frm.submit();
  }
  
$(document).ready(function(){
    
    $("nav-tabs li a").on("click", function(){
          $("nav-tabs li a").removeClass("active");
          $(this).addClass("active")
    }); 
    
});//--document
</script>  