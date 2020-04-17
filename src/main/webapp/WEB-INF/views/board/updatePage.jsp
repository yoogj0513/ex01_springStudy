<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>

<div class="content">
	<div class="row">
		<div class="col-sm-12">
			<div class="box box-primary">
				<div class="box-header">
					<h3 class="box-title">MODIFY</h3>
				</div>
				<form role="form" action="updatePage" method="post">
					<input type="hidden" name="page" value="${cri.page}"/>
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
							<button type="submit" class="btn btn-primary">Submit</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>

<%@ include file="../include/footer.jsp" %>