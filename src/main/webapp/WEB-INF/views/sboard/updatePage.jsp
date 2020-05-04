<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>

<style>

.fileWrap {
	float: left;
	position: relative;
	margin: 0 5px;
}

.check {
	position: absolute;
	top: 0;
	right: 0;
	margin: 1px !important;
}

#dropBox img{
	height: 100px;
	margin: 5px;
}
	
</style>

<div class="content">
	<div class="row">
		<div class="col-sm-12">
			<div class="box box-primary">
				<div class="box-header">
					<h3 class="box-title">MODIFY</h3>
				</div>
				<form role="form" action="updatePage" method="post" enctype="multipart/form-data">
					<input type="hidden" name="page" value="${cri.page}"/>
					<input type="hidden" name="searchType" value="${cri.searchType}"/>
					<input type="hidden" name="keyword" value="${cri.keyword}"/>
					<div class="box-body">
						<div class="form-group">
							<label>Bno</label>
							<input type="text" class="form-control" value="${board.bno }" readonly="readonly"/>
							<input type="hidden" name="bno" value="${board.bno }"/>
						</div>
						<div class="form-group">
							<label>Title</label>
							<input type="text" name="title" class="form-control" value="${board.title }"/>
						</div>
						<div class="form-group">
							<label>Content</label>
							<textarea cols="30" rows="5" name="content" class="form-control">${board.content }</textarea>
						</div>
						<div class="form-group">
							<label>Writer</label>
							<input type="text" name="writer" class="form-control" value="${board.writer }" readonly="readonly"/>
						</div>
						<div class="box-footer">
							<h3>Delete Image File</h3>
							<c:forEach var="file" items="${board.files }">
								<div class="fileWrap">
									<img src="displayFile?filename=${file }"/>
									<input type="checkbox" name="check" class="check" value="${file }"/>
								</div>
							</c:forEach>
						</div>
						<div class="box-footer">
							<h3>Add Image File</h3>
							<input type="file" name="imageFiles" class="form-control" multiple="multiple" id="files"/>
							<div id="dropBox"></div>
						</div>
						<div class="box-footer">
							<button type="submit" class="btn btn-warning">Modify</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
<script>
	$(".check").click(function() {
		if($(this).prop("checked")) {
			$(this).parent().css("opacity", "0.5");			
		} else {
			$(this).parent().css("opacity", "1");						
		}
	})
	
	$("#files").change(function(){
		var file = $(this)[0].files; // $(this)[0] : javascript객체
		
		$("#dropBox").empty();
	
		$(file).each(function(i, obj) {
			var reader = new FileReader(); //javascript 객체
			reader.readAsDataURL(file[i]);
			reader.onload = function(e){
				var $img = $("<img>").attr("src", e.target.result); //e.target.result == reader.result -> 똑같은 값이 가져옴
				$("#dropBox").append($img);
			} 
		});
	})
	
</script>

<%@ include file="../include/footer.jsp" %>