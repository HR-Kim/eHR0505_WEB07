<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>   
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
					<button type="button" class="btn btn-default btn-sm" id="doUpdate">수정</button>
					<button type="button" class="btn btn-default btn-sm" id="doDelete">삭제</button>
				</div>
			</div>
		</div>
		<!-- div title -->
		<form class="form-horizontal" name="boardEditFrm" id="boardEditFrm"
			method="post" action="do_update.do">
			<div class="form-group invisible" >
					<input type="hidden" class="form-control hidden" name="boardId" id="boardId"
						value="${vo.boardId }">
					<input type="text" class="form-control hidden" name="fileId" id="fileId"
                        value="<c:out value='${vo.fileId }' />">	
			</div>
  
			<div class="form-group">
				<label for="title" class="col-xs-2 col-sm-2 col-md-2 col-lg-2 control-label">제목</label>
				<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8">
					<input type="text" class="form-control" name="title" id="title"
						placeholder="제목" value="<c:out value='${vo.title }' />">
				</div>
			</div>
            <div class="form-group">
                <label for="attrFile" class="col-sm-2 control-label">파일첨부</label>
                <div class="col-sm-8">
                    <button type="button" data-target="#layerpop" data-toggle="modal" 
                     class="btn btn-default btn-sm" id="attrFile" >파일</button>
                </div>
            </div>
			<div class="form-group">
				<label for="readCnt" class="col-sm-2 control-label">조회수</label>
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
            <div class="form-group">
                <label for="listFileTable" class="col-sm-2 control-label">첨부파일</label>
                <div class="table-responsive col-xs-8 col-sm-8 col-md-8 col-lg-8">
                    <table class="table  table-striped table-bordered table-hover" id="listFileTable">
                        <tbody></tbody>
                    </table>
                </div>
                <!--// Grid영역 -->
            </div>
			</form>

            <!-- Modal  -->
			<div class="modal fade" id="layerpop" >
			  <div class="modal-dialog">
			    <div class="modal-content">
			      <!-- header -->
			      <div class="modal-header">
			        <!-- 닫기(x) 버튼 -->
			        <button type="button" class="close" data-dismiss="modal">×</button>
			        <!-- header title -->
			        <h4 class="modal-title">File Upload</h4>
			      </div>
                  <!-- body -->
                  <div class="modal-body">
                    <form class="form-horizontal" action="${context}/file/do_save.do" name="saveFileForm" id="saveFileForm" method="post" enctype="multipart/form-data">
                        <input type="hidden" class="form-control" name="work_div" id="work_div" value="com">
                        <input type="hidden" class="form-control" name="attrFileId" id="attrFileId"   value="<c:out value='${vo.fileId }' />">
                        
                        <input type="hidden" class="form-control" name="orgFileNm" id="orgFileNm"     ">
                        <input type="hidden" class="form-control" name="saveFileNm" id="saveFileNm"   ">
                        
                        <div class="custom-file">
                          <input type="file" class="custom-file-input" id="file01" name="file01">
                        </div>
                    </form>
                  </div>
                  <!-- Footer -->
			      <div class="modal-footer">
			        <button type="button" class="btn btn-default"  data-dismiss="modal" id="doFileUpload">저장</button>
			        <button type="button" class="btn btn-default"  data-dismiss="modal">취소</button>
			      </div>
			    </div>
			  </div>
			</div>
            <!--// Modal  -->

				<!--// Grid영역 -->
			</div>				
		

	</div>
	<!--// div container -->
	<!-- jQuery (부트스트랩의 자바스크립트 플러그인을 위해 필요합니다) -->
	<script src="${context}/resources/js/jquery-1.12.4.js"></script>
	
	<!-- jQuery validate -->
	<script src="${context}/resources/js/jquery.validate.js"></script>
	<!-- 모든 컴파일된 플러그인을 포함합니다 (아래), 원하지 않는다면 필요한 각각의 파일을 포함하세요 -->
	<script src="${context}/resources/js/bootstrap.min.js"></script>


	<script type="text/javascript">
	//fileID Update
    function doSaveFileId(){
        //alert($("#boardId").val()+'doSaveFileId'+$("#fileId").val());
        if('0'==$("#attrFileId").val() && $("#fileId").val()=='0')return;
        
        $.ajax({
              type : "POST",
              url : "${context}/board_attr/do_update.do",
              dataType : "html",
              data : {
                  "boardId" : $("#boardId").val(),
                  "fileId": $("#fileId").val(),
                  "title" : $("#title").val(),
                  "contents" : $("#contents").val(),
                  "regId" : $("#regId").val()
              },
              success : function(data) {
                  var jData = JSON.parse(data);
                  if (null != jData && jData.msgId == "1") {
                      //alert(jData.msgMsg);
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
      }
	//파일목록 조회
    function getFileList(fileId){
        alert("getFileList:"+fileId);
        
        $.ajax({
            type:"POST",
            url:"${context}/file/do_retrieve.do",
            dataType:"html",
            data:{
            "work_div":"do_retrieve",
            "fileId":fileId
           }, 
         success: function(data){
           var parseData = $.parseJSON(data);
           $("#listFileTable tbody tr").remove();
           $.each(parseData, function (index, item) {
               console.log(item);
               //json=[{"fileId":"201910127f8046b91e6348f2ba77c4d457738f00","orgFileNm":"KakaoTalk_풍경.jpg","saveFileNm":"C:\\HR_FILE\\2019\\10\\KakaoTalk_풍경16.jpg","fSize":55194.0,"ext":"jpg","regDt":"2019/10/12 15:11:46","totalCnt":0,"num":1}]
               $("#listFileTable > tbody:last").append("<tr>"+ 
                       "<td class='text-right hidden-xs hidden-sm hidden-md hidden-lg'>" + <c:out value="item.fileId"></c:out> + "</td>" +
                       "<td class='text-right hidden-xs hidden-sm hidden-md hidden-lg'>" + <c:out value="item.num"></c:out> + "</td>" +
                       "<td class='text-left org-file-name'>" + <c:out value="item.orgFileNm"></c:out> + "</td>" +
                       "<td class='text-left hidden-xs hidden-sm hidden-md hidden-lg'>" + <c:out value="item.saveFileNm"></c:out> + "</td>" +
                       "<td class='text-left'>" + "<fmt:formatNumber  groupingUsed='true' value='${item.fSize}' />" + "</td>" +   
                       "<td class='text-center hidden-xs hidden-sm hidden-md hidden-lg'>" + <c:out value="item.ext"></c:out> + "</td>" +      
                       "<td class='text-center hidden-xs hidden-sm hidden-md hidden-lg'>" + <c:out value="item.regDt"></c:out> + "</td>" +
                       "<td class='text-center'><button type='button'  class='btn btn-default btn-sm btn-danger' >X</button></td>" +
                       "</tr>"); 
           });
           
         }, 
         complete:function(data){
          
         },
         error:function(xhr,status,error){
             alert("error:"+error);
         }
        }); 
        //--ajax            
    }	
    //파일 다운로드 
    $("#listFileTable>tbody").on("click",".org-file-name",function(e){
        e.preventDefault();
        if(!confirm('파일다운로드 하시겠습니까?')) return;
        var tr      = $(this).parent();//button parent
        var tds     = tr.children()
        
        //console.log("tds:"+tds);
        var fileId         = tds.eq(0).text();
        var num            = tds.eq(1).text();
        var orgFileNm      = tds.eq(2).text();
        var saveFileNm     = tds.eq(3).text();
        console.log("fileId:"+fileId);
        console.log("num:"+num);
        console.log("orgFileNm:"+orgFileNm);
        console.log("saveFileNm:"+saveFileNm);
        
        var frm = document.saveFileForm;
        frm.action = "${context}/file/download.do";
        frm.orgFileNm.value = orgFileNm;
        frm.saveFileNm.value= saveFileNm;
        frm.submit();

    });
    
    //파일삭제
    $("#listFileTable>tbody").on("click",".btn-danger",function(e){

        if (confirm("삭제 하시겠습니까?") == false) return;
        var tr      = $(this).parent().parent();//button parent/td/tr
        var tds     = tr.children()
        
        //console.log("tds:"+tds);
        var fileId         = tds.eq(0).text();
        var num            = tds.eq(1).text();
        var orgFileNm      = tds.eq(2).text();
        var saveFileNm     = tds.eq(3).text();
        //alert(fileId+"|"+num);
        $.ajax({
            type : "POST",
            url : "${context}/file/do_delete.do",
            dataType : "html",
            data : {
                "fileId" : fileId,
                "num": num,
                "orgFileNm": orgFileNm,
                "saveFileNm": saveFileNm
            },
            success : function(data) {
                console.log("data:"+data);
                var jData = JSON.parse(data);
                if (null != jData && jData.msgId == "1") {
                    //alert(jData.msgMsg);
                    //선택한 row삭제
                    tr.remove();
                    fileIdNullUpdate();
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

	    //파일업로드
	    $("#doFileUpload").on("click",function(event){
	        if(confirm("등록 하시겠습니까?") == false) return;
	        event.preventDefault();
	        doUploadFile();
	    });
	    
	    function doUploadFile(){

            var form = $('form')[2];//Form data read
            var formData = new FormData(form);
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
                    if(data.msgId=="1"){
                        var tmpFileId = $("#fileId").val();
                        $("#attrFileId").val(data.msgMsg);
                        $("#fileId").val(data.msgMsg);
                        //FileList
                        getFileList($("#fileId").val());
                        
                    }else{
                        alert(data.msgMsg);
                    }
                },
                complete: function(data){//무조건 수행
                      //alert("complete");
                },
                error: function(xhr,status,error){
                    alert("error");
                }
            });         
        }       
	    
	
	    
		function fileIdNullUpdate(){
			$.ajax({
                type : "POST",
                url : "${context}/board_attr/do_fileIdNullUpdate.do",
                dataType : "html",
                data : {
                    "boardId" : $("#boardId").val()
                },
                success : function(data) {
                    var jData = JSON.parse(data);
                    if (null != jData && jData.msgId == "1") {
                        //alert(jData.msgMsg);
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
		}
		
	    

	
		//목록
		$("#doRetrieve").on("click", function() {
			if (confirm("목록으로 이동 하시겠습니까?") == false)return;

			location.href = "${context}/board_attr/get_retrieve.do";
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
					"boardId" : $("#boardId").val(),
					"fileId": $("#fileId").val(),
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
			console.log("fileId:" + $("#fileId").val());
			if (confirm("수정 하시겠습니까?") == false)
				return;

			$.ajax({
				type : "POST",
				url : "${context}/board_attr/do_update.do",
				dataType : "html",
				data : {
					"boardId" : $("#boardId").val(),
					"fileId": $("#fileId").val(),
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
			console.log("fileId:" + $("#fileId").val());
			if (confirm("삭제 하시겠습니까?") == false)	return;

			$.ajax({
				type : "POST",
				url : "${context}/board_attr/do_delete.do",
				dataType : "html",
				data : {
					"boardId" : $("#boardId").val(),
					"fileId": $("#fileId").val()
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
		
        //파일목록 조회
        function getFileList(fileId){
            //alert("getFileList:"+fileId);
            
            $.ajax({
                type:"POST",
                url:"${context}/file/do_retrieve.do",
                dataType:"html",
                data:{
                "work_div":"do_retrieve",
                "fileId":fileId
               }, 
             success: function(data){
               var parseData = $.parseJSON(data);
               $("#listFileTable tbody tr").remove();
               $.each(parseData, function (index, item) {
                   //json=[{"fileId":"201910127f8046b91e6348f2ba77c4d457738f00","orgFileNm":"KakaoTalk_풍경.jpg","saveFileNm":"C:\\HR_FILE\\2019\\10\\KakaoTalk_풍경16.jpg","fSize":55194.0,"ext":"jpg","regDt":"2019/10/12 15:11:46","totalCnt":0,"num":1}]
                   $("#listFileTable > tbody:last").append("<tr>"+ 
                           "<td class='text-right hidden-xs hidden-sm hidden-md hidden-lg'>" + <c:out value="item.fileId"></c:out> + "</td>" +
                           "<td class='text-right hidden-xs hidden-sm hidden-md hidden-lg'>" + <c:out value="item.num"></c:out> + "</td>" +
                           "<td class='text-left org-file-name'>" + <c:out value="item.orgFileNm"></c:out> + "</td>" +
                           "<td class='text-left hidden-xs hidden-sm hidden-md hidden-lg'>" + <c:out value="item.saveFileNm"></c:out> + "</td>" +
                           "<td class='text-right'>" + <c:out value="item.fSize"></c:out> + "&nbsp; byte </td>" +
                           "<td class='text-center hidden-xs hidden-sm hidden-md hidden-lg'>" + <c:out value="item.ext"></c:out> + "</td>" +
                           "<td class='text-center hidden-xs hidden-sm hidden-md hidden-lg'>" + <c:out value="item.regDt"></c:out> + "</td>" +
                           "<td class='text-center'><button type='button'  class='btn btn-default btn-sm btn-danger' >X</button></td>" +
                           "</tr>"); 
               });
               
               doSaveFileId();
               
             },
             complete:function(data){
              
             },
             error:function(xhr,status,error){
                 alert("error:"+error);
             }
            }); 
            //--ajax            
        }
        
		$(document).ready(function() {
			//alert('ready');
			if('${vo.fileId}' != '' ){
			 getFileList('${vo.fileId }');
			}
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