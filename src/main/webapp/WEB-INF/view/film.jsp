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
		<link rel="stylesheet" type="text/css" href="../resources/style.css">

		<title>${film.polishTitle} (${film.worldwideReleaseDate.getYear()})</title>
	</head>
	<body>

		<nav class="navbar navbar-expand-sm bg-dark navbar-dark fixed-top mb-5">
			<div class="container">
				<button class="navbar-toggler" data-toggle="collapse" data-target="#navbarCollapse">
					<span class="navbar-toggler-icon"></span>
				</button>
				<a href="#" class="navbar-brand logo pr-4">filmore</a>
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
				</div>
			</div>
		</nav>


    <div class="container" id="header" style="margin-top: 6rem">
			<div class="row justify-content-center">
				<div id="poster" class="col-lg-4 text-center">
					<div class="card mb-5 film-card mx-auto">
						<img class="card-img-top rounded" src="" alt="Card image cap">
					</div>
				</div>

				<div class="col-lg-5 mx-lg-0 ml-lg-5">
					<div id="title" class="mb-5 text-lg-left text-center">
						<h2 class="green-font-color d-inline-block">${film.polishTitle}</h2> <h3 class="orange-font-color d-inline-block">(${film.worldwideReleaseDate.getYear()})</h3>
						<h3 class="text-muted">${film.originalTitle}</h3>
					</div>

					<div id="wrapper" class="w-100 text-lg-left text-center">
						<div class="rounded box-colors shadow pl-4 pr-5 py-3 clearfix d-inline-block">
							<div class="rating-box d-inline-block">${film.averageRating} ★</div>
							<div class="text-muted d-inline-block ml-3">liczba głosów: ${film.numberOfVotes}</div>
							<div class="pt-3 text-left">Twoja ocena: </div>
							<form:form action="../change-rating" method="post" modelAttribute="filmRating">
								<fieldset class="rating">
									<form:radiobutton id="star-5" path="rating" value="5" /><label class="full" for="star-5" title="Badzo dobry"><i class="fas fa-star"></i></label>
									<form:radiobutton id="star-4" path="rating" value="4" /><label class="full" for="star-4" title="Dobry"><i class="fas fa-star"></i></label>
									<form:radiobutton id="star-3" path="rating" value="3" /><label class="full" for="star-3" title="Przeciętny"><i class="fas fa-star"></i></label>
									<form:radiobutton id="star-2" path="rating" value="2" /><label class="full" for="star-2" title="Słaby"><i class="fas fa-star"></i></label>
									<form:radiobutton id="star-1" path="rating" value="1" /><label class="full" for="star-1" title="Bardzo słaby"><i class="fas fa-star"></i></label>
								</fieldset>
								<form:hidden path = "user.id" />
								<form:hidden path = "film.id" />
							</form:form>
						</div>
					</div>

					<table class="film-data mx-lg-0 mx-auto mt-5">
						<tr>
							<th>Gatunek:</th>
							<td>
							    <c:forEach items="${film.genres}" var="genre" varStatus="loop">
                                    ${genre.name}
                                    ${!loop.last ? ',' : ''}
                               </c:forEach>
                           </td>
						</tr>
						<tr>
							<th>Produkcja:</th>
							<td>
							    <c:forEach items="${film.countries}" var="country" varStatus="loop">
							        ${country.name}
							        ${!loop.last ? ',' : ''}
							    </c:forEach>
							</td>
						</tr>
						<tr>
							<th>Premiera na świecie:</th>
							<td>${film.worldwideReleaseDate}</td>
						</tr>
						<tr>
							<th>Premiera w Polsce:</th>
							<td>${film.polishReleaseDate}</td>
						</tr>
						<tr>
							<th>Czas trwania:</th>
							<td>${film.runningTime} min.</td>
						</tr>
						<tr>
							<th>Box office:</th>
							<td>${film.boxOffice}</td>
						</tr>
					</table>

				</div>
			</div>

			<h4 class="my-4 green-font-color">Opis:</h4>

			<p class="description">${film.storyline}</p>

			<h4 class="my-4 green-font-color">Galeria:</h4>

			<div class="row text-center text-lg-left">
		    <div class="col-lg-3 col-6 m-0 p-0">
		      <a href="#" class="d-block h-100">
		            <img class="mw-100" src="https://source.unsplash.com/pWkk7iiCoDM/400x300" alt="">
		          </a>
		    </div>
		    <div class="col-lg-3 col-6 m-0 p-0">
		      <a href="#" class="d-block h-100">
		            <img class="mw-100" src="https://source.unsplash.com/aob0ukAYfuI/400x300" alt="">
		          </a>
		    </div>
		    <div class="col-lg-3 col-6 m-0 p-0">
		      <a href="#" class="d-block h-100">
		            <img class="mw-100" src="https://source.unsplash.com/EUfxH-pze7s/400x300" alt="">
		          </a>
		    </div>
		    <div class="col-lg-3 col-6 m-0 p-0">
		      <a href="#" class="d-block h-100">
		            <img class="mw-100" src="https://source.unsplash.com/M185_qYH8vg/400x300" alt="">
		          </a>
		    </div>
		    <div class="col-lg-3 col-6 m-0 p-0">
		      <a href="#" class="d-block h-100">
		            <img class="mw-100" src="https://source.unsplash.com/sesveuG_rNo/400x300" alt="">
		          </a>
		    </div>
		    <div class="col-lg-3 col-6 m-0 p-0">
		      <a href="#" class="d-block h-100">
		            <img class="mw-100" src="https://source.unsplash.com/AvhMzHwiE_0/400x300" alt="">
		          </a>
		    </div>
		    <div class="col-lg-3 col-6 m-0 p-0">
		      <a href="#" class="d-block h-100">
		            <img class="mw-100" src="https://source.unsplash.com/2gYsZUmockw/400x300" alt="">
		       </a>
		    </div>
		    <div class="col-lg-3 col-6 m-0 p-0">
		      <a href="C:\Users\wowow\Desktop\cards\images-w1400.jpg" class="d-block h-100" data-toggle="lightbox" data-gallery="example-gallery">
		            <img class="mw-100" src="https://source.unsplash.com/EMSDtjVHdQ8/400x300" alt="" class="img-fluid">
		          </a>
		    </div>
		  </div>


			<div id="accordion" class="mt-5 shadow rounded">

				<div class="card box-colors">
					<div class="card-header" id="headingOne">
						<h5 class="mb-0">
							<button class="btn transparent-button green-font-color" data-toggle="collapse" data-target="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
							<strong>Obsada aktorska</strong>
							</button>
						</h5>
					</div>
					<div id="collapseOne" class="collapse show" aria-labelledby="headingOne" data-parent="#accordion">
						<div class="card-body dark-blue">
							<table class="w-100">
								<tr>
									<td class="w-50">Brad Pitt</td>
									<td class="w-25">jako:</td>
									<td class="w-25">Tyler Durden</td>
								</tr>
							</table>
						</div>
					</div>
				</div>

				<div class="card box-colors rounded-bottom">
					<div class="card-header" id="headingTwo">
						<h5 class="mb-0">
							<button class="btn transparent-button green-font-color" data-toggle="collapse" data-target="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
								<strong>Twórcy</strong>
							</button>
						</h5>
					</div>
					<div id="collapseTwo" class="collapse" aria-labelledby="headingTwo" data-parent="#accordion">
						<div class="card-body dark-blue">
							<table class="w-100">
								<tr>
									<td class="w-75">David Fincher</td>
									<td class="w-25">Reżyser</td>
								</tr>
							</table>
						</div>
					</div>
				</div>

			</div>

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
