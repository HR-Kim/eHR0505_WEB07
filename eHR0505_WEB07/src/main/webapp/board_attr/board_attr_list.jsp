<%@page import="java.util.ArrayList"%>
<%@page import="kr.co.ehr.code.service.Code"%>
<%@page import="java.util.List"%>
<%@page import="kr.co.ehr.cmn.StringUtil"%>
<%@page import="kr.co.ehr.user.service.Search"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:set var="context" value="${pageContext.request.contextPath }" />
<%--
  /**
  * @Class Name : board_attr_list.jsp
  * @Description : 첨부형 게시판
  * @Modification Information
  *
  *   수정일                   수정자                      수정내용
  *  -------    --------    ---------------------------
  *  2018.10.07            최초 생성
  *
  * author SIST 개발팀
  * since 2018.04.26
  *
  * Copyright (C) 2009 by KandJang  All right reserved.
  */
--%>
<%
	/** 페이지 사이즈 */
	String pageSize   = "10"  ; 	
	/** 페이지 번호 */
	String pageNum    = "1"  ;	
	/** 검색조건 */
	String searchDiv  = "" ;
	/** 검색어 */
	String searchWord = "" ;
	/** 확장자 */
	String ext = "xls" ;	
	
	Search vo = (Search)request.getAttribute("vo");
	if(null !=vo){
		pageSize   = StringUtil.nvl(vo.getPageSize()+"","10");
		pageNum    = StringUtil.nvl(vo.getPageNum()+"","1");
		searchDiv  = StringUtil.nvl(vo.getSearchDiv(),"");
		searchWord = StringUtil.nvl(vo.getSearchWord(),"");		
	}else{
		pageSize   = "10";
		pageNum    = "1";
		searchDiv  = "";
		searchWord = "";
	}
	
	String extParam = (String)request.getAttribute("ext");
	if(extParam !=null) ext = extParam;
	
	//페이지사이즈
	List<Code> listPageSize=(request.getAttribute("listPageSize")==null)?
			(List<Code>)new ArrayList<Code>():(List<Code>)(request.getAttribute("listPageSize"));
	//게시판 검색 구분
	List<Code> listBoardSearch=(request.getAttribute("listBoardSearch")==null)?
			(List<Code>)new ArrayList<Code>():(List<Code>)(request.getAttribute("listBoardSearch"));
		
	//엑셀타입	
	List<Code> listExcelType=(request.getAttribute("listExcelType")==null)?
			(List<Code>)new ArrayList<Code>():(List<Code>)(request.getAttribute("listExcelType"));	
		
	//paging 
	//maxNum, currPageNo, rowPerPage, bottomCount, url, scriptName	
	int maxNum      = 0;//totalCnt
	int bottomCount = 10;
	int currPageNo  = 1;//pageNum
	int rowPerPage  = 10;//pageSize
	
	String url      = request.getContextPath()+"/board_attr/get_retrieve.do";
	String scriptName ="search_page";
	
	//totalCnt
	String tmpTotalCnt=(request.getAttribute("totalCnt")==null)?"0"
			                                :request.getAttribute("totalCnt").toString();
	
	maxNum = Integer.valueOf(tmpTotalCnt);
	currPageNo = Integer.valueOf(pageNum);
	rowPerPage = Integer.valueOf(pageSize);
	

%>
<html lang="ko">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 위 3개의 메타 태그는 *반드시* head 태그의 처음에 와야합니다; 어떤 다른 콘텐츠들은 반드시 이 태그들 *다음에* 와야 합니다 -->
<title>첨부게시판</title>

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
			<h1><spring:message code="message.board.board_title_list"/></h1>
		</div>
		<!--// div title -->
		
		<!-- 검색영역 -->
		<div class="row">
			<div class="col-md-12 text-right">
				<form class="form-inline" name="boardFrm" id="boardFrm" method="get">
					<input type="hidden" name="pageNum" id="pageNum" value="${vo.pageNum }"/>
					<input type="hidden" name="boardId" id="boardId" />
					<input type="hidden" name="fileId"  id="fileId" />
					<div class="form-group">
					    <!-- 페이지 사이즈 -->
						<%=StringUtil.makeSelectBox(listPageSize, "pageSize", pageSize, false) %>
						<!-- 검색구분 --> 
						<%=StringUtil.makeSelectBox(listBoardSearch, "searchDiv", searchDiv, true) %>
						<input type="text" class="form-control input-sm" id="searchWord"
							name="searchWord" onKeyUp="keywordSearch()" placeholder='<spring:message code="message.com.search"/>' />	
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<button type="button" class="btn btn-default btn-sm"
							id="doRetrieve"><spring:message code="message.com.retrieve"/></button>						
						<!-- 엑셀구분 --> 
						<%=StringUtil.makeSelectBox(listExcelType, "ext", ext, false) %>	
						<input type="button" class="btn btn-default btn-sm" id="doExcel" value='<spring:message code="message.com.exceldown"/>' />
						<button type="button" class="btn btn-default btn-sm"
                            id="doSave">등록</button>
					</div>
						<div id="suggest">
       		   				<div id="suggestList"></div>
  			   			</div>
				</form>

			</div>
		</div>
		<!--// 검색영역 -->

		<!-- Grid영역 -->
		<div class="table-responsive">
			<table class="table  table-striped table-bordered table-hover" id="listTable">
				<thead class="bg-primary">
				    <th class="text-center col-md-1 col-xs-1" style="display:none;">BOARD_ID</th>
				    <th class="text-center col-md-1 col-xs-1" style="display:none;">FILE_ID</th>
					<th class="text-center col-md-1 col-xs-1"><spring:message  code="message.com.num"/></th>
					<th class="text-center col-md-9 col-xs-6 "><spring:message code="message.board.title"/></th>
					<th class="text-center col-md-1 col-xs-1"><spring:message  code="message.board.reg_id"/></th>
					<th class="text-center col-md-1 col-xs-1"><spring:message  code="message.board.read_cnt"/></th>
					<th class="text-center col-md-1 col-xs-2"><spring:message  code="message.board.reg_dt"/></th>
				</thead>
				<!--fileId <img alt="" src='<c:url value="/resources/images/file/file.gif" />'  > -->
				<tbody>
					<c:choose>
						<c:when test="${list.size()>0 }">
							<c:forEach var="vo" items="${list}">
								<tr>
									<td class="text-center" style="display:none;"><c:out value="${vo.boardId }"/></td>
									<td class="text-center" style="display:none;"><c:out value="${vo.fileId }"/></td>
									<td class="text-center"><c:out value="${vo.num }"/></td>
									<td class="text-left"><c:out value="${vo.title }"/>&nbsp;<c:if test="${'0'!= vo.fileId}"><span class="glyphicon glyphicon-floppy-disk"></c:if></td>
									<td class="text-left"><c:out value="${vo.regId }"/></td>
									<td class="text-right"><c:out value="${vo.readCnt }"/></td>
									<td class="text-center"><c:out value="${vo.regDt }"/></td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr><td class="text-center" colspan="99"><spring:message code="message.msg.no_data"/></td></tr>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>
		</div>
		<!--// Grid영역 -->

		<!-- pagenation -->
		<div class="text-center">
			<%=StringUtil.renderPaing(maxNum, currPageNo, rowPerPage, bottomCount, url, scriptName) %>
		</div>
		<!--// pagenation -->

	<!--// div container -->
	<!-- jQuery (부트스트랩의 자바스크립트 플러그인을 위해 필요합니다) -->
	<script src="${context}/resources/js/jquery-1.12.4.js"></script>
	<!-- 모든 컴파일된 플러그인을 포함합니다 (아래), 원하지 않는다면 필요한 각각의 파일을 포함하세요 -->
	<script src="${context}/resources/js/bootstrap.min.js"></script>
	<script type="text/javascript">
		var loopSearch = true;

		function keywordSearch(){
			if(loopSearch == false)return;
			//alert($("#searchDiv").val()+"|"+$("#searchWord").val());
			
			if($("#searchDiv").val()=="" || $("#searchDiv").val()=="20")return;
			if($("#searchWord").val()=="")return;
	        $.ajax({
	              type : "GET",
	              url  : "${context}/board_attr/keywordSearch.do",
	              dataType : "html",
	              async : true,
	              data  : {
	            	  "searchDiv"  : $("#searchDiv").val(),
	                  "searchWord" : $("#searchWord").val()
	              },
	              success : function(data) {
	            	  alert(data);
	                  var jData = JSON.parse(data);
	                  
	                  displayResult(jData);
	              },
	              complete : function(data) {

	              },
	              error : function(xhr, status, error) {
	                  alert("error:" + error);
	              }
	          }); 
			
		}
	
		function displayResult(jsonInfo){
			var count = jsonInfo.length;
			if(count > 0) {
			    var html = '';
			    for(var i in jsonInfo){
				   html += "<a href=\"javascript:select('"+jsonInfo[i]+"')\">"+jsonInfo[i]+"</a><br/>";
			    }
			    $("#suggestList").empty();
			    $("#suggestList").append(html);
			    //show('suggest');
			}else{
			    //hide('suggest');
			} 
		}
		
		function select(selectedKeyword) {
			 $("#searchWord").val(selectedKeyword);
			 //document.boardFrm.searchWord.value=selectedKeyword;
			 loopSearch = false;
			 hide('suggest');
		}
		
		function show(elementId) {
			 var element = document.getElementById(elementId);
			 if(element) {
			  element.style.display = 'block';
			 }
			}
		
		function hide(elementId){
		   var element = document.getElementById(elementId);
		   if(element){
			  element.style.display = 'none';
		   }
		}
		
		//paging
		function search_page(url, pageNum){
			var frm = document.boardFrm;
	    	frm.pageNum.value = pageNum;
	    	frm.action = url;
	    	frm.submit();
		}
	
	
		//grid click
		$("#listTable>tbody").on("click","tr",function(){
			//alert("listTable");
			var trs = $(this);
			var td  = trs.children();
			if(null==td || td.length==1)return;
			//alert("td.length:"+td.length);
			
			
			var boardId = td.eq(0).text();
			var fileId = td.eq(1).text();
			//console.log("boardId:"+boardId);
			var frm = document.boardFrm;
			frm.boardId.value=boardId;
			frm.fileId.value=fileId;
			frm.action = "${context}/board_attr/do_selectOne.do";
			frm.submit();
			
		});
	
	
	    function doRetrieve(){
	    	var frm = document.boardFrm;
	    	frm.pageNum.value = 1;
	    	frm.action = "${context}/board_attr/get_retrieve.do";
	    	frm.submit();
	    }
	    
	    function doExcelDown(){
	    	var frm = document.boardFrm;
			frm.action = "${context}/board_attr/do_exceldown.do";
	    	frm.submit();
	    }
	    
	    
	    //
	    $("#doExcel").on("click",function(){
	    	//alert("doExcel");
	    	if(false==confirm('엑셀저장 하시겠습니까?'))return;
	    	doExcelDown();
	    });
	    
	    //등록으로 이동
        $("#doSave").on("click",function(){
            var frm = document.boardFrm;
            frm.action = "${context}/board_attr/board_attr_reg.do";
            frm.submit();
        });
	    
	
		$("#doRetrieve").on("click",function(){
			
			doRetrieve();
		});
		
	
	
		$(document).ready(function() {
			//alert("ready");
		});
	</script>
</body>
</html>