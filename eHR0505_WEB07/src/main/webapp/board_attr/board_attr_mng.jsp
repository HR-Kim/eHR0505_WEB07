<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:set var="delete" value="<spring:message code='message.com.delete'/>"  />
<c:set var="context" value="${pageContext.request.contextPath }" />
<%--
  /**
  * @Class Name : board_attr_mng.jsp
  * @Description : board_attr_mng Register 화면
  * @Modification Information
  *
  *   수정일                   수정자                      수정내용
  *  -------    --------    ---------------------------
  *  2019.10.08            최초 생성
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
<title>첨부게시판 관리</title>

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
			<h1>게시관리</h1>
		</div>
		<!--// div title -->
		<!-- Button Area -->
		<div class="row">
			<div class="col-lg-10 col-sm-10 col-xs-10">
				<div class="text-right">
					<button type="button" class="btn btn-default btn-sm"
						id="doRetrieve">목록</button>
					<button type="button" class="btn btn-default btn-sm" id="doInit">초기화</button>
					<button type="button" class="btn btn-default btn-sm" id="doSave" disabled="disabled">등록</button>
					<button type="button" class="btn btn-default btn-sm" id="doUpdate">수정</button>
					<button type="button" class="btn btn-default btn-sm" id="doDelete">삭제</button>
				</div>
			</div>
		</div>
		<div class="col-lg-10"></div>
		<!-- div title -->
		<form class="form-horizontal" name="boardEditFrm" id="boardEditFrm"
			method="post" action="do_update.do">
			<div class="form-group invisible" >
					<input type="hidden" class="form-control invisible" name="boardId" id="boardId"
						value="${vo.boardId }">
			</div>

			<div class="form-group">
				<label for="title" class="col-xs-2 col-sm-2 col-md-2 col-lg-2 control-label">제목</label>
				<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8">
					<input type="text" class="form-control" name="title" id="title"
						placeholder="제목" value="<c:out value='${vo.title }' />">
				</div>
			</div>
			<div class="form-group">
				<label for="fileId" class="col-sm-2 control-label">파일첨부</label>
				
				<div class="col-sm-7">
				    <button type="button" class="btn btn-default btn-sm" id="attrFile">파일</button>	
					<input type="hidden" class="form-control invisible	" name="fileId" id="fileId"
						placeholder="파일첨부"  disabled="disabled" value="<c:out value='${vo.fileId }' />">
				</div>
				
			</div>
						
			<div class="form-group">
				<label for="inputEmail3" class="col-sm-2 control-label">조회수</label>
				<div class="col-sm-8">
					<input type="text" class="form-control" name="readCnt" id="readCnt"
						placeholder="조회수" value="<c:out value='${vo.readCnt }' />"
						disabled="disabled">
				</div>
			</div>
			<div class="form-group">
				<label for="inputEmail3" class="col-sm-2 control-label">내용</label>
				<div class="col-sm-8">
					<textarea class="form-control" name="contents" id="contents"
						rows="7" placeholder="내용"><c:out
							value="${vo.contents }" /></textarea>
				</div>
			</div>
			<div class="form-group">
				<label for="inputEmail3" class="col-sm-2 control-label">등록자
					아이디</label>
				<div class="col-sm-8">
					<input type="text" class="form-control" name="regId" id="regId"
						placeholder="등록자 아이디" value="<c:out value='${vo.regId }' />"
						disabled="disabled">
				</div>
			</div>
			<div class="form-group">
				<label for="inputEmail3" class="col-sm-2 control-label">등록일</label>
				<div class="col-sm-8">
					<input type="text" class="form-control" name="regDt" id="regDt"
						placeholder="등록일" value="<c:out value='${vo.regDt }' />"
						disabled="disabled">
				</div>
			</div>
			${listFile}
			<c:if test="${listFile.size()>0 }">
			<div class="form-group">
				<label for="inputEmail3" class="col-xs-2 col-sm-2 col-md-2 col-lg-2 control-label">파일첨부</label>
				<!-- Grid영역 -->
				
				<div class="table-responsive col-xs-8 col-sm-8 col-md-8 col-lg-8">
					<table class="table  table-striped table-bordered table-hover" id="listFileTable">
						<thead >
						    <th class="hidden-xs hidden-sm hidden-md hidden-lg"  >BOARD_ATTR_ID</th>
						    <th class="hidden-xs hidden-sm hidden-md hidden-lg"  >NUM<</th>
							<th class="text-center col-md-5 col-xs-5">원본파일명</th>
							<th class="text-center col-md-4 col-xs-4"  style="display:none;">저장파일명</th>
							<th class="text-center col-md-2 col-xs-2">파일사이즈</th>
							<th class="text-center col-md-1 col-xs-1">삭제</th>
						</thead>
						<tbody>
							<c:choose>
								<c:when test="${listFile.size()>0 }">
									<c:forEach var="vo" items="${listFile}">
										<tr> 
											<td class="hidden-xs hidden-sm hidden-md hidden-lg"  data-visible="false"><c:out value="${vo.fileId }"/></td>
											<td class="hidden-xs hidden-sm hidden-md hidden-lg"  data-visible="false"><c:out value="${vo.num }"/></td>
											<td class="text-left"><c:out value="${vo.orgFileNm }"/></td>
											<td class="text-left"  style="display:none;"><c:out value="${vo.saveFileNm }"/></td>
											<td class="text-right"><c:out value="${vo.fSize }"/>byte</td>
											<td class="text-center"><button type="button" name="${vo.num}" class="btn btn-default btn-sm btn-danger" >X</button></td>
										</tr>
									</c:forEach>
								</c:when>
								<c:otherwise>
								</c:otherwise>
							</c:choose>
						</tbody>
					</table>
				</div>
				</c:if>
				<!--// Grid영역 -->
			</div>				
		</form>
	</div>
	<!--// div container -->
	<!-- jQuery (부트스트랩의 자바스크립트 플러그인을 위해 필요합니다) -->
	<script src="${context}/resources/js/jquery-1.12.4.js"></script>
	
	<!-- jQuery validate -->
	<script src="${context}/resources/js/jquery.validate.js"></script>
	<!-- 모든 컴파일된 플러그인을 포함합니다 (아래), 원하지 않는다면 필요한 각각의 파일을 포함하세요 -->
	<script src="${context}/resources/js/bootstrap.min.js"></script>


	<script type="text/javascript">
	   
	    $("#listFileTable>tbody>tr").on("click","button",function(){
	    	if (confirm('<spring:message code="message.msg.will_you_do_it" />') == false)return;
	    	var fileNum = $(this).attr('name');//file num
	    	var fileId = $("#fileId").val();//fileId
	    	//console.log("fileId:"+fileId);
	    	//console.log("fileNum:"+fileNum);
	    	
	    	
	    	
	    	
	    });
	
		//목록
		$("#doRetrieve").on("click", function() {
			if (confirm("목록으로 이동 하시겠습니까?") == false)return;

			location.href = "${context}/board_attr/get_retrieve.do";
		});

		//초기환
		$("#doInit").on("click", function() {
			//alert("doInit");
			//input data clear
			$("#boardId").val("")
			$("#title").val("");
			$("#contents").val("");
			$("#regId").val("");
			$("#readCnt").val("");
			$("#regDt").val("")

			//버튼제어:등록,수정,삭제
			$("#doUpdate").prop("disabled", true);
			$("#doDelete").prop("disabled", true);
			$("#doSave").prop("disabled", false);

			$("#regId").prop("disabled", false);

		});

		//등록	    
		$("#doSave").on("click", function() {
			  
			//validation
			if($("#boardEditFrm").valid()==false)return;
			
			if (confirm("등록 하시겠습니까?") == false)
				return;

			$.ajax({
				type : "POST",
				url : "${context}/board_attr/do_save.do",
				dataType : "html",
				data : {
					"title" : $("#title").val(),
					"contents" : $("#contents").val(),
					"regId" : $("#regId").val()
				},
				success : function(data) {
					var jData = JSON.parse(data);
					if (null != jData && jData.msgId == "1") {
						alert(jData.msgMsg);
						location.href = "${context}/board_attr/get_retrieve.do";

					} else {
						alert(jData.msgId + "|" + jData.msgMsg);
					}
				},
				complete : function(data) {

				},
				error : function(xhr, status, error) {
					alert("error:" + error);
				}
			});
			//--ajax  

		});

		//수정:submit->control->board_attr_mng.jsp: (성공)?board_list.jsp:board_mng.jsp
		//삭제:submit->
		$("#doUpdate").on("click", function() {
			//validation
			if($("#boardEditFrm").valid()==false)return;
			
			console.log("boardId:" + $("#boardId").val());
			if (confirm("수정 하시겠습니까?") == false)
				return;

			$.ajax({
				type : "POST",
				url : "${context}/board_attr/do_update.do",
				dataType : "html",
				data : {
					"boardId" : $("#boardId").val(),
					"title" : $("#title").val(),
					"contents" : $("#contents").val(),
					"regId" : $("#regId").val()
				},
				success : function(data) {
					var jData = JSON.parse(data);
					if (null != jData && jData.msgId == "1") {
						alert(jData.msgMsg);
						location.href = "${context}/board_attr/get_retrieve.do";

					} else {
						alert(jData.msgId + "|" + jData.msgMsg);
					}
				},
				complete : function(data) {

				},
				error : function(xhr, status, error) {
					alert("error:" + error);
				}
			});
			//--ajax  

		});

		//삭제:submit->
		$("#doDelete").on("click", function() {
			//validation
			console.log("boardId:" + $("#boardId").val());
			if (confirm("삭제 하시겠습니까?") == false)
				return;

			$.ajax({
				type : "POST",
				url : "${context}/board_attr/do_delete.do",
				dataType : "html",
				data : {
					"boardId" : $("#boardId").val()
				},
				success : function(data) {
					var jData = JSON.parse(data);
					if (null != jData && jData.msgId == "1") {
						alert(jData.msgMsg);
						location.href = "${context}/board_attr/get_retrieve.do";

					} else {
						alert(jData.msgId + "|" + jData.msgMsg);
					}
				},
				complete : function(data) {

				},
				error : function(xhr, status, error) {
					alert("error:" + error);
				}
			});
			//--ajax  

		});

		$(document).ready(function() {
			//form validate
			$("#boardEditFrm").validate({
				rules: {					
					title: {
						required: true,
						minlength: 2,
						maxlength: 100
					},
					contents: {
						required: true,
						minlength: 2,
						maxlength: 1000000
					}
				},
				messages: {
					title: {
						required: "제목을 입력 하세요.",
						minlength: $.validator.format("{0}자 이상 입력 하세요."),
						maxlength: $.validator.format("{0}자 내로 입력 하세요.")
					},
					contents: {
						required: "내용을 입력 하세요.",
						minlength: $.validator.format("{0}자 이상 입력 하세요."),
						maxlength: $.validator.format("{0}자 내로 입력 하세요.")
					}
				},
				errorPlacement : function(error, element) {
				     //do nothing
				    },
				    invalidHandler : function(form, validator) {
				     var errors = validator.numberOfInvalids();
				     if (errors) {
				      alert(validator.errorList[0].message);
				      validator.errorList[0].element.focus();
				     }
				}

			});			
			
			
			
		});
	</script>
</body>
</html>