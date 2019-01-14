<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="userId" value="${pageContext.request.userPrincipal.name}" />

<!doctype html>
<html lang="pl">
	<head>
		<!-- Required meta tags -->
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
		<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.4.2/css/all.css" integrity="sha384-/rXc/GQVaYpyDdyxK+ecHPVYJSN9bmVFBvjA/9eOB+pb3F2w2N6fc5qB9Ew5yIns" crossorigin="anonymous">
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.13.2/css/bootstrap-select.min.css">
		<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Pacifico">
		<link rel="stylesheet" type="text/css" href="resources/style.css">

		<title>Moje oceny</title>
	</head>
	<body>

		<nav class="navbar navbar-expand-sm bg-dark navbar-dark fixed-top mb-5">
			<div class="container">
				<button class="navbar-toggler" data-toggle="collapse" data-target="#navbarCollapse">
					<span class="navbar-toggler-icon"></span>
				</button>
				<a href="${contextPath}" class="navbar-brand logo pr-4">filmore</a>
				<div class="collapse navbar-collapse" id="nabarCollapse">
					<ul class="navbar-nav">
						<li class="nav-item">
							<a href="#" class="nav-link" style="color: #EC7149;">Filmy</a>
						</li>
						<li class="nav-item">
							<a href="#" class="nav-link">Ludzie kina</a>
						</li>
						<li class="nav-item">
							<a href="#" class="nav-link">Rankingi</a>
						</li>
					</ul>

                    <ul class="navbar-nav ml-auto">
					    <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" data-toggle="dropdown"
                                aria-haspopup="true" aria-expanded="false">
                                <span style="font-size: 130%"><i class="fas fa-user green-font-color"></i> ${userId}</span>
                            </a>
                            <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
                                <a class="dropdown-item" href="${contextPath}/ratings">Moje oceny</a>
                                <div class="dropdown-divider"></div>
                                <a class="dropdown-item" href="${contextPath}/logout">Wyloguj się</a>
                            </div>
					    </li>
					</ul>
				</div>
			</div>
		</nav>


    <div class="container" id="header" style="margin-top: 6rem">

			<form method="get" action="${contextPath}/ratings">
				<div class="btn-group btn-group-toggle mb-4" data-toggle="buttons">
					<label class="btn btn-lg btn-warning ${empty filmsRatings ? '' : 'active'}">
						<input type="radio" name="rating-category" value="films" id="option1" autocomplete="off" ${empty filmsRatings ? '' : 'checked'}> Filmy
					</label>
					<label class="btn btn-lg btn-warning ${empty peopleRatings ? '' : 'active'}">
						<input type="radio" name="rating-category" value="people" id="option2" autocomplete="off" ${empty peopleRatings ? '' : 'checked'}> Ludzie kina
					</label>
				</div>
			</form>

			<c:choose>
	  		<c:when test="${not empty filmsRatings}">
					<c:forEach items="${filmsRatings}" var="ratingObj">
						<div class="row justify-content-center mb-3">

							<div class="col-10 box-colors py-2 rounded shadow">
								<div class="row">
									<div class="col-10 my-auto">
										<span style="font-size: 120%;">
											<a href="${contextPath}/films/${ratingObj.film.id}">${ratingObj.film.polishTitle} (${ratingObj.film.worldwideReleaseDate.getYear()})</a>
										</span>
									</div>
									<div class="col-2 text-center">
										<c:forEach var = "i" begin = "1" end = "${ratingObj.rating}">
											<span class="orange-font-color" style="font-size: 130%;">★</span>
										</c:forEach>
										<span class="d-block text-muted">${ratingObj.dateOfRating}</span>
									</div>
								</div>
							</div>

						</div>
					</c:forEach>
				</c:when>
				<c:when test="${not empty peopleRatings}">
					<c:forEach items="${peopleRatings}" var="ratingObj">
						<div class="row justify-content-center mb-3">

							<div class="col-10 box-colors py-2 rounded shadow">
								<div class="row">
									<div class="col-10 my-auto">
										<span style="font-size: 120%;">
											<a href="${contextPath}/people/${ratingObj.person.id}">${ratingObj.person.stageName}</a>
										</span>
									</div>
									<div class="col-2 text-center">
										<c:forEach var = "i" begin = "1" end = "${ratingObj.rating}">
											<span class="orange-font-color" style="font-size: 130%;">★</span>
										</c:forEach>
										<span class="d-block text-muted">${ratingObj.dateOfRating}</span>
									</div>
								</div>
							</div>

						</div>
					</c:forEach>
				</c:when>
			</c:choose>

		</div>

    <footer class="footer font-small py-2">
      <div class="container">
  			<div class="footer-copyright text-center text-muted py-3">© 2018 Copyright:
  				<a href="https://www.linkedin.com/in/wojciech-%C5%9Bmigowski-1b22b2140">Wojciech Śmigowski</a>
        </div>
      </div>
		</footer>

	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>

	<script src="https://cdnjs.cloudflare.com/ajax/libs/ekko-lightbox/5.3.0/ekko-lightbox.min.js"></script>
	<script type="text/javascript">
		$('input[type=radio]').on('change', function() {
			$(this).closest("form").submit();
		});

		$(document).on('click', '[data-toggle="lightbox"]', function(event) {
		  event.preventDefault();
		  $(this).ekkoLightbox();
	  	});
	</script>
	</body>
</html>
