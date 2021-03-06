<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="context" value="${pageContext.request.contextPath }" />



<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
<title>Review Write- Giants Box</title>

<!-- favicon -->
<link rel=" shortcut icon" href="${context}/resources/img/favicon.ico">
<link rel="icon" href="${context}/resources/img/favicon.ico">
<!-- //favicon -->

<link rel="stylesheet"
	href="${context}/resources/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css?family=Montserrat:400,400i,700,700i,600,600i">
<link rel="stylesheet"
	href="${context}/resources/fonts/font-awesome.min.css">
<link rel="stylesheet"
	href="${context}/resources/fonts/simple-line-icons.min.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/baguettebox.js/1.10.0/baguetteBox.min.css">
<link rel="stylesheet" href="${context}/resources/css/styles.min.css"
	rel="stylesheet" type="text/css">

<!--calendar-->
<link href="${context}/resources/css/datepicker.min.css"
	rel="stylesheet" type="text/css">
<!--//calendar-->

<!-- css/불러오는 jsp -->
<!-- //css/불러오는 jsp -->


</head>

<body>
	<div id="wrap">
<%-- 	<%@include  file="/cmn/inc/header.jsp" %> --%>
	<section>
		<!-- container -->
		<div class="container">
			<!-- 제목 -->
			<div class="page-header">
				<h2>게시판 등록</h2>
			</div>
			<!--// 제목 -->
			
			<!-- button -->	
			<div class="row text-right">
			    <label class="col-lg-2 col-md-2 col-sm-2 col-xs-2"></label>
				<div class="col-lg-10 col-md-10 col-sm-10 col-xs-10">
				    <!-- javascript click event -->
					<!-- <input  type="button" class="btn btn-primary btn-sm" value="등록" onclick="javascript:doInsert();" /> -->
					<!-- jquery click event -->
					<input  type="button" class="btn btn-primary btn-sm" value="등록" id="insert_btn" /> 				
					<input  type="button" class="btn btn-primary btn-sm" value="목록" id="move_list"  />
				</div>
			</div>
			<!--// button -->
			<form class="form-horizontal" name="save_frm" action="" method="post">
			    <!-- hidden: work_div,div=10(공지사항),read_cnt=0 -->
				<input type="hidden"  name="work_div" id="work_div"  />
				<input type="hidden"  name="div" id="div" value="10" />
				<input type="hidden"  name="read_cnt" id="read_cnt" value="0" />
				
				<div class="form-group">
					<label class="col-lg-2 col-md-2 col-sm-2 col-xs-2">이벤트번호</label>
				    <div class="col-lg-10 col-md-10 col-sm-10 col-xs-10">
				    	<input type="number" class="form-control" name="eventSeq" id="eventSeq" placeholder="eventSeq"  />
				    </div>
				</div>
				<div class="form-group">
					<label class="col-lg-2 col-md-2 col-sm-2 col-xs-2">제목</label>
				    <div class="col-lg-10 col-md-10 col-sm-10 col-xs-10">
				    	<input type="text" class="form-control" name="title" id="title" placeholder="제목"  />
				    </div>
				</div>
				<div class="form-group">
					<label class="col-lg-2 col-md-2 col-sm-2 col-xs-2">카테고리</label>
				    <div class="col-lg-10 col-md-10 col-sm-10 col-xs-10">
				    	<input type="text" class="form-control" name="category" id="category" placeholder="제목"  />
				    </div>
				</div>
				<div class="form-group">
					<label class="col-lg-2 col-md-2 col-sm-2 col-xs-2">작성자</label>
				    <div class="col-lg-3 col-md-3 col-sm-3 col-xs-3">
				    	<input type="text" class="form-control" name="writer" id="writer" placeholder="작성자"  />
				    </div>
				</div>	
				<div class="form-group">
					<label class="col-lg-2 col-md-2 col-sm-2 col-xs-2">내용</label>
					<div class="col-lg-10 col-md-10 col-sm-10 col-xs-10">
						<textarea rows="5" cols="40" name="context" id="context" class="form-control"></textarea>
					</div>
				</div>
				
			</form>
		</div>
		<!--// container -->
	</section>
<%--     <%@include  file="/cmn/inc/footer.jsp" %>	 --%>
</div>	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${context}/resources/js/movie_func.js"></script>
    
    <!-- javascript -->
    <script type="text/javascript">

	    //모든 컨트롤(element)가 로딩이 완료되면.
		$(document).ready(function(){   
			console.log("document ready");   		
			
		});//document ready    
    
		//목록 Event감지
		$("#move_list").on("click",function(){
			console.log("move_list");
			//window.location.href="/EJDBC/board/board.do?work_div=doSelectList";
			moveToListView();
		});
		
		
		//목록화면으로 이동
		//?
		function moveToListView(){
			window.location.href="${context}/review/doSelectList.do";
		}
		
		
		
		
		//jquery event감지:id
		$("#insert_btn").on('click',function(){
			console.log("#insert_btn");

			var eventSeq = $("#eventSeq").val();
			console.log("eventSeq:"+eventSeq);
			if(null == eventSeq || eventSeq.trim().length==0){
				$("#eventSeq").focus();
				alert("eventSeq 입력하세요.");
				return;
			}
			
			var title = $("#title").val();
			console.log("title:"+title);
			if(null == title || title.trim().length==0){
				$("#title").focus();
				alert("제목을 입력하세요.");
				return;
			}
			
			var writer = $("#writer").val();
			console.log("writer:"+writer);
			if(null == writer || writer.trim().length==0){
				$("#writer").focus();
				alert("등록자를 입력하세요.");
				return;
			}
			
			var context = $("#context").val();
			console.log("context:"+context);
			if(null == context || context.trim().length==0){
				$("#context").focus();
				alert("내용을 입력하세요.");
				return;
			}		
				
			var category = $("#category").val();
			console.log("category:"+category);
			if(null == category || category.trim().length==0){
				$("#category").focus();
				alert("category 입력하세요.");
				return;
			}		
			
			//confirm : 확인
    		if( false==confirm("저장 하시겠습니까?"))return;
			
			
			//fiddler : web debuger(오후)
			//ajax->ReviewController.java 
			//return json   
			$.ajax({
				    type:"POST",
				    url:"${context}/review/doInsertView.do",
				    dataType:"html", 
				    data:{"work_div":"doInsert",
				    	  "eventSeq" :$("#eventSeq").val(),
				    	  "writer":$("#writer").val(),
				    	  "title":$("#title").val(),				    	  				          
				          "context":$("#context").val(),
				          "category":$("#category").val()
				    },
				    success:function(data){ //성공
				       //console.log("data="+data);
				       //alert("data:"+data);
				       
				       // - =jsonMsg={"msgId":"1","msgContents":"등록 되었습니다.","num":0,"totalCnt":0}
				       
				       //json 분리해서 변수
				       var jsonObj = JSON.parse(data);
				       console.log("msgId="+jsonObj.msgId);
				       console.log("msgContents="+jsonObj.msgContents);
				    
				       if(null !=jsonObj && jsonObj.msgId=="1"){
				    	   alert(jsonObj.msgContents);
				    	   //board_list.jsp로 이동 
				    	   //window.location.href="/EJDBC/board/board.do?work_div=doSelectList";
				    	   moveToListView();
				       }
				    },
				    error:function(xhr,status,error){
				     alert("error:"+error);
				    },
				    complete:function(data){
				    
				    }   
				  
			});//--ajax

		
			
		});//insert_btn  
		
    	function doInsert_javascript() {
    		//console.log("doInsert() call");
			//alert("doInsert");
			//var frm = document.save_frm;
			//alert("frm.div.value:"+frm.div.value);
			
			//form에 컨트롤에 값 가지고 오기
    		/*
    			1. 컨트롤 이름으로 가지고 오기
    			   document.컨트롤이름.value
    			2. 컨트롤의 id로 가지고 오기
    			   document.getElementById('컨트롤id').value
    		*/
    		var frm = document.save_frm;
			
    		console.log("1.컨트롤 이름으로 가지고 오기"+document.save_frm.div.value);
    		console.log("2.컨트롤의 id로 가지고"+document.getElementById('div').value);
    		
    		var title = frm.title.value;
    		console.log("before title:"+title);
    		
    		//제목
    		if(title==""){
    			alert("제목을 입력하세요.");
    			frm.title.focus();
    			return;
    		}
    		
    		var eventSeq = frm.writer.value;
    		if(eventSeq==""){
    			alert("eventSeq 입력하세요.");
    			frm.eventSeq.focus();
    			return;
    		}
    		
    		//등록자ID
    		var writer = frm.writer.value;
    		if(writer==""){
    			alert("등록자를 입력하세요.");
    			frm.writer.focus();
    			return;
    		}
    		
    		//내용
    		var context = frm.context.value;
    		if(context==""){
    			alert("내용을 입력하세요.");
    			frm.context.focus();
    			return;
    		}    
    		
    		//confirm : 확인
    		if( false==confirm("저장 하시겠습니까?"))return;
    		
    		
    		//작업구분 : doInsert
    		frm.work_div.value="doInsert";   
    		
    		//브라우저 to server로
    		//?
    		frm.action = "giants_box/review/doSelectList.do"
    		frm.submit();
	
		}
    </script>
    
    
    
    
    
    
    
    
    
    
  </body>
</html>