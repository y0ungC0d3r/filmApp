<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
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
		<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Pacifico">
		<link rel="stylesheet" type="text/css" href="resources/style.css">

		<title>Filmy</title>
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

		<main role="main" class="container">
			<div class="container" style="margin-top: 6rem;">
                <form method="post" action="${contextPath}/films" id="film-criteria">
                    <div class="row">
                        <div class="col-md-11 col-8">
                            <div class="input-group mb-5">
                                <input type="text" class="form-control" name="title" placeholder="Wyszukaj film..."
                                    <c:if test="${not empty title}">value="${title}"</c:if>
                                />
                                <button class="btn btn-warning" type="submit"><i class="fa fa-search"></i></button>
                            </div>
                        </div>

                        <div class="col-md-1 col-1">
                            <!--<a class="btn btn-warning btn-block d-lg-block d-md-none d-xs-none d-none"
                                data-toggle="collapse" href="#collapseSearch" role="button" aria-expanded="false" aria-controls="collapseSearch">
                          Wyszukiwanie zaawansowane
                      </a>-->
                            <a class="btn btn-warning" data-toggle="collapse" href="#collapseSearch" role="button" aria-expanded="false" aria-controls="collapseSearch">
                          <i class="fas fa-sliders-h"></i>
                      </a>
                        </div>
                    </div>

                    <div class="collapse rounded box-colors shadow mb-5 px-5 py-2" id="collapseSearch">

                        <div class="form-group row my-3">
                            <label for="rating-picker" class="col-sm-2 col-form-label">Ocena</label>
                            <div class="col-sm-10 form-group">
                                <select id="rating-picker" class="selectpicker" name="rating" title="Wybierz przedział..."
                                        data-width="100%" multiple="true">
                                    <optgroup data-icon="fas fa-star" label="Od" data-max-options="1">
                                        <option value="floor-1" title="Od 1" <c:if test="${not empty floor && floor == 1}">selected</c:if>>1</option>
                                        <option value="floor-2" title="Od 2" <c:if test="${not empty floor && floor == 2}">selected</c:if>>2</option>
                                        <option value="floor-3" title="Od 3" <c:if test="${not empty floor && floor == 3}">selected</c:if>>3</option>
                                        <option value="floor-4" title="Od 4" <c:if test="${not empty floor && floor == 4}">selected</c:if>>4</option>
                                        <option value="floor-5" title="Od 5" <c:if test="${not empty floor && floor == 5}">selected</c:if>>5</option>
                                    </optgroup>
                                    <optgroup data-icon="fas fa-star" label="Do" data-max-options="1">
                                        <option value="roof-1" title="Do 1" <c:if test="${not empty roof && roof == 1}">selected</c:if>>1</option>
                                        <option value="roof-2" title="Do 2" <c:if test="${not empty roof && roof == 2}">selected</c:if>>2</option>
                                        <option value="roof-3" title="Do 3" <c:if test="${not empty roof && roof == 3}">selected</c:if>>3</option>
                                        <option value="roof-4" title="Do 4" <c:if test="${not empty roof && roof == 4}">selected</c:if>>4</option>
                                        <option value="roof-5" title="Do 5" <c:if test="${not empty roof && roof == 5}">selected</c:if>>5</option>
                                    </optgroup>
                                </select>
                            </div>
                        </div>
                        <div class="form-group row my-3">
                            <label for="" class="col-sm-2 col-form-label">Gatunek</label>
                            <div class="col-sm-10 form-group">
                                <select class="selectpicker" name="genres" title="Wybierz gatunek..." data-width="100%"
                                    data-size="8" data-live-search="true" multiple="true">
                                    <c:forEach items="${genres}" var="genreEntry">
                                        <option value="${genreEntry.key.id}" <c:if test="${genreEntry.value.booleanValue()}">selected</c:if>>
                                            ${genreEntry.key.name}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group row my-3">
                            <label for="" class="col-sm-2 col-form-label">Kraje produkcji</label>
                            <div class="col-sm-10 form-group">
                                <select class="selectpicker" name="countries" title="Wybierz kraje..." data-width="100%"
                                    data-size="8" data-live-search="true" multiple="true">
                                    <c:forEach items="${countries}" var="countryEntry">
                                        <option value="${countryEntry.key.id}" <c:if test="${countryEntry.value.booleanValue()}">selected</c:if>>
                                            ${countryEntry.key.name}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group row my-3">
                            <label for="" class="col-sm-2 col-form-label">Lata produkcji</label>
                            <div class="col-sm-10 form-group">
                                <select class="selectpicker" name="years" title="Wybierz lata..." data-width="100%" data-size="8" multiple="true">
                                    <c:forEach items="${years}" var="yearEntry">
                                        <option value="${yearEntry.key}" <c:if test="${yearEntry.value.booleanValue()}">selected</c:if>>
                                            ${yearEntry.key}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>

                        <hr class="border-light my-3" />

                        <div class="form-group row my-3">
                            <label for="" class="col-sm-2 col-form-label">Sortuj według...</label>
                            <div class="col-sm-10 form-group">
                                <select class="selectpicker" name="sort_by" title="" data-width="100%">
                                    <optgroup label="ocena">
                                        <option value="rating-descending" title="ocena: najwyższa"
                                            <c:if test="${not empty sort_by && sort_by == 'rating-descending'}">selected</c:if>>
                                            najwyższa
                                        </option>
                                        <option value="rating-ascending" title="ocena: najniższa"
                                            <c:if test="${not empty sort_by && sort_by == 'rating-ascending'}">selected</c:if>>
                                            najniższa
                                        </option>
                                    </optgroup>
                                    <optgroup label="rok produkcji">
                                        <option value="date-descending" title="rok produkcji: najnowszy"
                                            <c:if test="${not empty sort_by && sort_by == 'date-descending'}">selected</c:if>>
                                            najnowszy
                                        </option>
                                        <option value="date-ascending" title="rok produkcji: najstarszy"
                                            <c:if test="${not empty sort_by && sort_by == 'date-ascending'}">selected</c:if>>
                                            najstarszy
                                        </option>
                                    </optgroup>
                                </select>
                            </div>
                        </div>
                    </div>
			    </form>

				<div class="row card-columns justify-content-center">
                    <c:if test="${empty films}"><h1 class="green-font-color">Nie znaleziono żadnych filmów</h1></c:if>

					<c:forEach items="${films}" var="film">
						<div class="col-xl-4 col-md-6 col-sm-12">
							<div class="card mb-5 film-card">
								<img class="card-img-top" src="${posterPaths[film.id]}" alt="Card image cap">
								<div class="card-body">
									<h4 class="card-title"><a href="${contextPath}/films/${film.id}" class="green-font-color film-link">${film.polishTitle}</a></h4>
									<h6 class="card-subtitle mb-2 text-muted">${film.worldwideReleaseDate.getYear()}</h6>
								</div>
								<div class="card-footer text-right box-colors">
									<span class="orange-font-color">★</span> <span class="text-muted">
									    ${empty film.averageRating ? "Brak" : film.averageRating}
									</span>
								</div>
							</div>
						</div>
					</c:forEach>
			</div>
			<c:if test="${fn:length(pagination) > 1}">
                <div class="pagination justify-content-center mb-5">
                    <c:forEach items="${pagination}" var="p" varStatus="loop">
                        <c:if test="${loop.first && p != page}">
                            <button name="page" value="${page - 1}" form="film-criteria">&laquo;</button>
                        </c:if>

                        <button <c:choose>
                                    <c:when test="${p == page}">class="active" disabled</c:when>
                                    <c:otherwise>name="page" value="${p}"</c:otherwise>
                                </c:choose> form="film-criteria">
                            ${p}
                        </button>

                        <c:if test="${loop.last && p != page}">
                            <button name="page" value="${page + 1}" form="film-criteria">&raquo;</button>
                        </c:if>
                    </c:forEach>
                </div>
            </c:if>
            </div>
		</main>

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
	<!-- Latest compiled and minified JavaScript -->
	<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.13.2/js/bootstrap-select.min.js"></script>
	<!--<script>
	    $('button').on('click', function() {
            $('#film-criteria').submit();
        });
     </script>-->
	</body>
</html>
