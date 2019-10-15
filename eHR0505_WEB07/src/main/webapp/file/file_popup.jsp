<%@page import="kr.co.ehr.cmn.StringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="context" value="${pageContext.request.contextPath }" />
<%
	String fileId = StringUtil.nvl(request.getParameter("fileId"));
    if(fileId.equals("0")){
    	fileId = "";
    }

    String key = StringUtil.nvl(request.getParameter("boardId"));
%>  

<%--
  /**
  * @Class Name : form_template.jsp
  * @Description : Sample Register 화면
  * @Modification Information
  *
  *   수정일                   수정자                      수정내용
  *  -------    --------    ---------------------------
  *  2018.04.26            최초 생성
  *
  * author SIST 개발팀
  * since 2018.04.26
  *
  * Copyright (C) 2009 by KandJang  All right reserved.
  */
--%>  
<html lang="ko">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 위 3개의 메타 태그는 *반드시* head 태그의 처음에 와야합니다; 어떤 다른 콘텐츠들은 반드시 이 태그들 *다음에* 와야 합니다 -->
<title>File Upload</title>

<!-- 부트스트랩 -->
<link href="${context}/resources/css/bootstrap.min.css" rel="stylesheet">

<!-- IE8 에서 HTML5 요소와 미디어 쿼리를 위한 HTML5 shim 와 Respond.js -->
<!-- WARNING: Respond.js 는 당신이 file:// 을 통해 페이지를 볼 때는 동작하지 않습니다. -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
	<!-- div container -->
    <div class="container">
        <!-- div title -->
        <div class="page-header">
            <h1>File Upload</h1>
        </div>
        <!--// div title -->

        <!-- div title -->
        <form class="form-horizontal" action="${context}/file/do_save.do" name="saveForm" id="saveForm" method="post" enctype="multipart/form-data">
            <input type="hidden" class="form-control" name="work_div" id="work_div" value="com">
            <input type="hidden" class="form-control" name="fileId" id="fileId"  value="<%=fileId%>" >
            <div class="form-group">
                <label for="file01" class="col-sm-2 control-label">File</label>
                <div class="col-sm-8">
                    <input type="file" class="form-control" name="file01" id="file01" placeholder="File">
                </div>
            </div>
        </form>
        <div class="form-group text-center">
            <label for="doSave" class="col-sm-2 control-label"></label>
         <div class="col-sm-8">
             <button type="button" class="btn btn-default btn-sm" id="doSave">저장</button>
         </div>
        </div>
        
      </div>
<script src="${context}/resources/js/jquery-1.12.4.js"></script>
<script type="text/javascript">

	function closewin(fileData)
	{
	   opener.setChildValue(fileData); 
	   self.close();
	}
	
	function doSave(){

        var form = $('form')[0];//Form data read
        var formData = new FormData(form);
        //formData.append("file01",$("#file01")[0].files[0]);
        //console.log("formData:"+formData);
        $.ajax({
            type:"POST",
            url:"${context}/file/do_save.do",
            contentType:false,
            async:false,
            cache:false,
            processData:false,
            enctype:"multipart/form-data",
            data:formData,
            success: function(data){//통신이 성공적으로 이루어 졌을때 받을 함수
            	//alert(data);
            	console.log("data:"+data);
            	var parseData = $.parseJSON(data);
            	console.log("parseData:"+parseData);
                //if(parseData.msgId=="1"){
                //	window.opener.location.reload();
                //	self.close();
                //}else{
                  //  alert(parseData.msgMsg);
                //}
            },
            complete: function(data){//무조건 수행
            	  //alert("complete");
            },
            error: function(xhr,status,error){
             alert("error");
            }
        });         
	}
	
	
    $(document).ready(function(){
        //console.log("ready");
        $("#doSave").on("click",function(event){
        	if(confirm("등록 하시겠습니까?") == false) return;
        	event.preventDefault();
        	doSave();
        });

    });
</script>	
</body>
</html>