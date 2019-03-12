<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="username" value="${pageContext.request.userPrincipal.name}" />

<!doctype html>
<html lang="pl">
	<head>
		<!-- Required meta tags -->
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
		<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.4.2/css/all.css" integrity="sha384-/rXc/GQVaYpyDdyxK+ecHPVYJSN9bmVFBvjA/9eOB+pb3F2w2N6fc5qB9Ew5yIns" crossorigin="anonymous">
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.13.2/css/bootstrap-select.min.css">
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ekko-lightbox/5.3.0/ekko-lightbox.css">
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
				<a href="${contextPath}" class="navbar-brand logo pr-4">filmore</a>
				<div class="collapse navbar-collapse" id="nabarCollapse">
					<ul class="navbar-nav">
                        <li class="nav-item">
							<a href="${contextPath}/films" class="nav-link">Filmy</a>
						</li>
						<li class="nav-item">
							<a href="${contextPath}/people" class="nav-link">Ludzie kina</a>
						</li>
						<li class="nav-item">
							<a href="#" class="nav-link">Rankingi</a>
						</li>
					</ul>

                    <ul class="navbar-nav ml-auto">
					    <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" data-toggle="dropdown"
                                aria-haspopup="true" aria-expanded="false">
                                <span class="text-white" style="font-size: 130%"><i class="fas fa-user"></i> ${username}</span>
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
			<div class="row justify-content-center">
				<div id="poster" class="col-lg-4 text-center">
					<div class="card mb-5 film-card mx-auto">
						<img class="card-img-top rounded" src="../${posterPath}" alt="Card image cap">
					</div>
				</div>

				<div class="col-lg-5 mx-lg-0 ml-lg-5">
					<div id="title" class="mb-5 text-lg-left text-center">
						<h2 class="d-inline-block"><a href="#" class="green-font-color film-link">${film.polishTitle}</a></h2>
						<h3 class="orange-font-color d-inline-block">(${film.worldwideReleaseDate.getYear()})</h3>
						<h3 class="text-muted">${film.originalTitle}</h3>
					</div>

					<div id="wrapper" class="w-100 text-lg-left text-center">
						<div class="rounded box-colors shadow px-4 py-3 clearfix d-inline-block">
                            <c:choose>
                                <c:when test="${now.isBefore(film.worldwideReleaseDate)}">
                                    <h5>Film czeka na premiere</h5>
                                </c:when>
                                <c:otherwise>
                                   <div class="rating-box d-inline-block">${film.averageRating} ★</div>
                                    <div class="text-muted d-inline-block ml-3">liczba głosów: ${film.numberOfVotes}</div>
                                    <div class="pt-3 text-left">Twoja ocena: </div>
                                    <form:form action="../change-film-rating" method="post" modelAttribute="filmRating">
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
                                </c:otherwise>
                            </c:choose>
						</div>
					</div>

					<table class="film-data mx-lg-0 mx-auto mt-5">
					    <c:if test="${not empty film.genres}">
                            <tr>
                                <th>Gatunek:</th>
                                <td>
                                    <c:forEach items="${film.genres}" var="genre" varStatus="loop">
                                        ${genre.name}
                                        ${!loop.last ? ',' : ''}
                                   </c:forEach>
                               </td>
                            </tr>
						</c:if>
						<c:if test="${not empty film.countries}">
                            <tr>
                                <th>Produkcja:</th>
                                <td>
                                    <c:forEach items="${film.countries}" var="country" varStatus="loop">
                                        ${country.name}
                                        ${!loop.last ? ',' : ''}
                                    </c:forEach>
                                </td>
                            </tr>
						</c:if>
						<c:if test="${not empty film.worldwideReleaseDate}">
                            <tr>
                                <th>Premiera na świecie:</th>
                                <td>${film.worldwideReleaseDate}</td>
                            </tr>
						</c:if>
						<c:if test="${not empty film.polishReleaseDate}">
                            <tr>
                                <th>Premiera w Polsce:</th>
                                <td>${film.polishReleaseDate}</td>
                            </tr>
						</c:if>
						<c:if test="${not empty film.runningTime}">
                            <tr>
                                <th>Czas trwania:</th>
                                <td>${film.runningTime} min.</td>
                            </tr>
						</c:if>
						<c:if test="${not empty film.boxOffice}">
                            <tr>
                                <th>Box office:</th>
                                <td>${film.boxOffice}</td>
                            </tr>
						</c:if>
					</table>

				</div>
			</div>

			<h4 class="my-4 green-font-color">Opis:</h4>

            <c:choose>
                <c:when test="${not empty film.storyline}">
                    <p class="description text-justify">${film.storyline}</p>
                </c:when>
                <c:otherwise>
                    <p class="description">Film nie posiada jeszcze opisu fabuły.</p>
                </c:otherwise>
            </c:choose>

			<h4 class="my-4 green-font-color">Galeria:</h4>

        <div class="row text-center text-lg-left">
            <c:forEach var="path" items="${imagesPaths}">
                <div class="col-lg-3 col-6 m-0 p-0">
                  <a href="../${path.value}" class="d-block h-100" data-toggle="lightbox" data-gallery="example-gallery">
                    <img class="mw-100" src="../${path.key}" alt="" class="img-fluid">
                  </a>
                </div>
            </c:forEach>
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
						<div class="card-body dark-blue p-0">
							<c:forEach items="${film.filmActorsAssociation}" var="actor" varStatus="loop">
								<c:choose>
									<c:when test="${loop.first}">
										<div class="clearfix p-2 bg-dark">
											<div class="float-left w-1-3">
											    <a href="${contextPath}/people/${actor.person.id}">${actor.person.stageName}</a>
											</div>
											<div class="float-left w-1-3 text-center">jako:</div>
											<div class="float-left w-1-3 text-right">${actor.character}</div>
										</div>
									</c:when>
									<c:otherwise>
										<div class="clearfix p-2 ${loop.index % 2 == 0 ? '' : 'bg-dark'}">
											<div class="float-left w-50">
											    <a href="${contextPath}/people/${actor.person.id}">${actor.person.stageName}</a>
											</div>
											<div class="float-left w-50 text-right">${actor.character}</div>
										</div>
									</c:otherwise>
								</c:choose>
							</c:forEach>
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
						<div class="card-body dark-blue p-0">
							<c:set var="counter" value="0" />
							<c:forEach items="${film.filmDirectors}" var="director">
								<div class="clearfix p-2 ${counter % 2 == 0 ? '' : 'bg-dark'}">
									<div class="float-left w-50">
									    <a href="${contextPath}/people/${director.id}">${director.stageName}</a>
									</div>
									<div class="float-left w-50 text-right">reżyseria</div>
								</div>
								<c:set var="counter" value="${counter + 1}" />
							</c:forEach>
							<c:forEach items="${film.filmScreenwriters}" var="screenwriter">
								<div class="clearfix p-2 ${counter % 2 == 0 ? '' : 'bg-dark'}">
									<div class="float-left w-50">
									    <a href="${contextPath}/people/${screenwriter.id}">${screenwriter.stageName}</a>
									</div>
									<div class="float-left w-50 text-right">scenariusz</div>
								</div>
								<c:set var="counter" value="${counter + 1}" />
							</c:forEach>
							<c:forEach items="${film.filmCinematographers}" var="cinematographer">
								<div class="clearfix p-2 ${counter % 2 == 0 ? '' : 'bg-dark'}">
									<div class="float-left w-50">
									    <a href="${contextPath}/people/${cinematographer.id}">${cinematographer.stageName}</a>
									</div>
									<div class="float-left w-50 text-right">zdjęcia</div>
								</div>
								<c:set var="counter" value="${counter + 1}" />
							</c:forEach>
							<c:forEach items="${film.filmMusicians}" var="musician">
								<div class="clearfix p-2 ${counter % 2 == 0 ? '' : 'bg-dark'}">
									<div class="float-left w-50">
									    <a href="${contextPath}/people/${musician.id}">${musician.stageName}</a>
									</div>
									<div class="float-left w-50 text-right">muzyka</div>
								</div>
								<c:set var="counter" value="${counter + 1}" />
							</c:forEach>
							<c:forEach items="${film.filmEditors}" var="editor">
								<div class="clearfix p-2 ${counter % 2 == 0 ? '' : 'bg-dark'}">
									<div class="float-left w-50">
									    <a href="${contextPath}/people/${editor.id}">${editor.stageName}</a>
									</div>
									<div class="float-left w-50 text-right">montaż</div>
								</div>
								<c:set var="counter" value="${counter + 1}" />
							</c:forEach>
							<c:forEach items="${film.filmProducers}" var="producer">
								<div class="clearfix p-2 ${counter % 2 == 0 ? '' : 'bg-dark'}">
									<div class="float-left w-50">
									    <a href="${contextPath}/people/${producer.id}">${producer.stageName}</a>
									</div>
									<div class="float-left w-50 text-right">produkcja</div>
								</div>
								<c:set var="counter" value="${counter + 1}" />
							</c:forEach>
						</div>
					</div>
				</div>

			</div>

                <form action="../add-film-comment" method="post">
                    <div class="row justify-content-center mt-4" id="comment-section">

                        <div class="col-11 my-3">
                            <h4 class="my-4"><span class="green-font-color">Komentarze</span> <span style="color: lightgray">(${film.comments.size()})</span></h4>
                        </div>
                        <div class="col-10 pb-4">
                            <textarea class="form-control" name="content" rows="4" placeholder="Napisz komentarz..."></textarea>
                        </div>

                         <input type="hidden" name="film-id" value="${film.id}" />

                        <div class="col-10 pb-4">
                            <input class="btn btn-warning btn-block" type="submit" value="Publikuj" />
                        </div>
                    </div>
                </form>

                <form action="../delete-comment" method="post">
                    <div class="row justify-content-center mt-4">
                        <c:forEach items="${film.comments}" var="comment">
                            <div class="col-10 box-colors py-2 px-4 rounded shadow mx-5 mb-4" style="word-wrap: break-word">
                                <a style="font-size: 120%;" class="orange-font-color" href="../ratings?username=${comment.user.username}">
                                    ${comment.user.username}</a>
                                <span class="ml-2 text-muted">
                                    ${comment.dateOfPublish.toLocalDate()} ${comment.dateOfPublish.toLocalTime()}
                                </span>
                                <c:if test="${comment.user.username.equals(username)}">
                                    <div class="float-right clearfix">
                                        <button type="submit" class="btn btn-danger btn-sm" name="comment-id" value="${comment.id}">
                                            <i class="fa fa-times" aria-hidden="true"></i>
                                        </button>
                                    </div>
                                </c:if>

                                <div class="w-100 pb-2 pt-2" style="color: lightgray">
                                    ${comment.content}
                                </div>
                            </div>
                        </c:forEach>
                    </div>
			    </form>


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
