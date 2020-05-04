<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>

<style>
	#dropBox {
		min-height: 180px;
		border : 1px solid #aaa;
	}

	#dropBox img {
		height: 150px;
		padding: 5px;
	}
</style>

<div class="content">
	<div class="row">
		<div class="col-sm-12">
			<div class="box box-primary">
				<div class="box-header">
					<h3 class="box-title">REGISTER</h3>
				</div>
				<form role="form" action="register" method="post" enctype="multipart/form-data">
					<div class="box-body">
						<div class="form-group">
							<label>Title</label>
							<input type="text" name="title" class="form-control" placeholder="Enter Title"/>
						</div>
						<div class="form-group">
							<label>Content</label>
							<textarea cols="30" rows="5" placeholder="Enter Content" name="content" class="form-control"></textarea>
						</div>
						<div class="form-group">
							<label>Writer</label>
							<input type="text" name="writer" class="form-control" placeholder="Enter Writer"/>
						</div>
						<div class="form-group">
							<label>Image Files</label>
							<input type="file" name="imageFiles" class="form-control" multiple="multiple" id="files"/>
							<div id="dropBox"></div>
						</div>
					</div>
					<div class="box-footer">
						<button type="submit" class="btn btn-primary">Submit</button>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>

<script>
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