<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>


<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="f"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="css/bootstrap.css">





<title>Film Database</title>
</head>
<body>

	<div>
		<h1>
			Film Database <small class="text-muted">By Louis Alder</small>
		</h1>
	</div>


	<div>
		<button id="addNewFilm" class="btn btn-primary">
			New Film
			<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16"
				fill="currentColor" class="bi bi-film" viewBox="0 0 16 16">
  <path
					d="M0 1a1 1 0 0 1 1-1h14a1 1 0 0 1 1 1v14a1 1 0 0 1-1 1H1a1 1 0 0 1-1-1V1zm4 0v6h8V1H4zm8 8H4v6h8V9zM1 1v2h2V1H1zm2 3H1v2h2V4zM1 7v2h2V7H1zm2 3H1v2h2v-2zm-2 3v2h2v-2H1zM15 1h-2v2h2V1zm-2 3v2h2V4h-2zm2 3h-2v2h2V7zm-2 3v2h2v-2h-2zm2 3h-2v2h2v-2z" />
</svg>
		</button>
	</div>

	<div id="FilmForm" style="display: none;">
		<div id="formContainer" class="container">
			<form method="POST" action="./films" class="form-group">

				<h2>Add a new film:</h2>
				<div class="form-group">
					<label for="title">Title</label> <input type="text" name="title"
						id="title" class="form-control" placeholder="Insert Title">
				</div>
				<div class="form-group">
					<label for="year">Year</label> <input type="text" name="year"
						id="year" class="form-control" placeholder="Insert Year">
				</div>
				<div class="form-group">
					<label for="director">Director</label> <input type="text"
						name="director" id="director" class="form-control"
						placeholder="Insert Director">
				</div>
				<div class="form-group">
					<label for="stars">Stars</label> <input type="text" name="stars"
						id="stars" class="form-control" placeholder="Insert Stars">
				</div>
				<div class="form-group">
					<label for="review">Review</label>
					<textarea name="review" id="review" class="form-control"
						placeholder="Insert Review"></textarea>
				</div>
				<button type="submit" class="btn btn-primary">Submit</button>
				

			</form>
					<button id="closeForm" class="btn btn-danger btn-sm">Close Form</button>
		</div>

	</div>






	<div class="container">
		<table class="table table-responsive" id="filmTable">
			<tr>
				<th>Title</th>
				<th>Year</th>
				<th>Director</th>
				<th>Stars</th>
				<th>Review</th>
				<th>Actions</th>
			</tr>


			<f:forEach items="${films}" var="f">
				<tr>
					<td><b>${f.title}</b></td>
					<td>${f.year}</td>
					<td>${f.director}</td>
					<td>${f.stars}</td>
					<td>${f.review}</td>
					<td><a href="update.jsp?id=${f.id}"
						class="btn btn-primary btn-sm"><svg
								xmlns="http://www.w3.org/2000/svg" width="16" height="16"
								fill="currentColor" class="bi bi-pencil" viewBox="0 0 16 16">
  <path
									d="M12.146.146a.5.5 0 0 1 .708 0l3 3a.5.5 0 0 1 0 .708l-10 10a.5.5 0 0 1-.168.11l-5 2a.5.5 0 0 1-.65-.65l2-5a.5.5 0 0 1 .11-.168l10-10zM11.207 2.5 13.5 4.793 14.793 3.5 12.5 1.207 11.207 2.5zm1.586 3L10.5 3.207 4 9.707V10h.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.5h.293l6.5-6.5zm-9.761 5.175-.106.106-1.528 3.821 3.821-1.528.106-.106A.5.5 0 0 1 5 12.5V12h-.5a.5.5 0 0 1-.5-.5V11h-.5a.5.5 0 0 1-.468-.325z" />
</svg></a>



						<form action="./delete" method="POST">
							<input type="hidden" name="id" value="${f.id}">
							<button type="submit" class="btn btn-danger btn-sm">
								<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16"
									fill="currentColor" class="bi bi-trash" viewBox="0 0 16 16">
  <path
										d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5Zm2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5Zm3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0V6Z" />
  <path
										d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1v1ZM4.118 4 4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4H4.118ZM2.5 3h11V2h-11v1Z" />
</svg>
							</button>
						</form></td>
				</tr>
			</f:forEach>
		</table>



	</div>



</body>
	<script src="./js/script.js"></script>
	

</html>