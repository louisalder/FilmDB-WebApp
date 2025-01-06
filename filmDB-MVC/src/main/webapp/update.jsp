<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>


<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>




<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="css/bootstrap.css">
<script src="js/bootstrap.js"></script>
<title>Modify Film</title>
</head>
<body>





	<%
	String id = request.getParameter("id");
	%>

	<h2>Modify Existing Film:</h2>


	<div class='modifyContainer'>
		<form method="POST" action="./update" class="form-group">


			<input type="hidden" name="id" value="<%=id%>">

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
	</div>



	<button onclick="window.location.href = 'films';"
		class="btn btn-primary">Go Back</button>
		


</body>

			<style>
#modifyContainer {
	position: fixed;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	background-color: rgba(255, 255, 255, 0.5);
	backdrop-filter: blur(10px);
	/* Applies a blur effect to the background */
	display: flex;
	justify-content: center;
	align-items: center;
}
</style>
</html>

