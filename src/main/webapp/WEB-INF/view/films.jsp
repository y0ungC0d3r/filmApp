<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

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

		<main role="main" class="container">
			<div class="container" style="margin-top: 6rem;">
                <form method="post" action="${contextPath}/films">
                    <div class="row">
                        <div class="col-md-11 col-8">
                            <div class="input-group mb-5">
                                <input type="text" class="form-control" name="film-title" placeholder="Wyszukaj film..." />
                                <div class="input-group-btn">
                                    <button class="btn btn-warning" type="submit"><i class="fa fa-search"></i></button>
                                </div>
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
                                        <option value="1" title="Od 1">1<i class="fas fa-star"></i></option>
                                        <option value="2" title="Od 2">2</option>
                                        <option value="3" title="Od 3">3</option>
                                        <option value="4" title="Od 4">4</option>
                                        <option value="5" title="Od 5">5</option>
                                    </optgroup>
                                    <optgroup data-icon="fas fa-star" label="Do" data-max-options="1">
                                        <option value="1" title="Do 1">1</option>
                                        <option value="2" title="Do 2">2</option>
                                        <option value="3" title="Do 3">3</option>
                                        <option value="4" title="Do 4">4</option>
                                        <option value="5" title="Do 5">5</option>
                                    </optgroup>
                                </select>
                            </div>
                        </div>
                        <div class="form-group row my-3">
                            <label for="" class="col-sm-2 col-form-label">Gatunek</label>
                            <div class="col-sm-10 form-group">
                                <select class="selectpicker" name="genre" title="Wybierz gatunek..." data-width="100%"
                                    data-size="8" data-live-search="true" multiple="true">
                                    <c:forEach items="${genres}" var="genre">
                                        <option value="${genre.id}">${genre.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group row my-3">
                            <label for="" class="col-sm-2 col-form-label">Kraje produkcji</label>
                            <div class="col-sm-10 form-group">
                                <select class="selectpicker" name="country" title="Wybierz kraje..." data-width="100%"
                                    data-size="8" data-live-search="true" multiple="true">
                                    <c:forEach items="${countries}" var="country">
                                        <option value="${country.codeId}">${country.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group row my-3">
                            <label for="" class="col-sm-2 col-form-label">Lata produkcji</label>
                            <div class="col-sm-10 form-group">
                                <select class="selectpicker" name="years" title="Wybierz lata..." data-width="100%" data-size="8" multiple="true">
                                    <c:forEach items="${years}" var="year">
                                        <option value="${year}">${year}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>

                        <hr class="border-light my-3" />

                        <div class="form-group row my-3">
                            <label for="" class="col-sm-2 col-form-label">Kraje produkcji</label>
                            <div class="col-sm-10 form-group">
                                <select class="selectpicker" name="sort-by" title="" data-width="100%">
                                    <optgroup label="ocena">
                                        <option value="rating-descending" title="ocena: najwyższa">najwyższa</option>
                                        <option value="rating-ascending" title="ocena: najniższa">najniższa</option>
                                    </optgroup>
                                    <optgroup label="rok produkcji">
                                        <option value="date-descending" title="rok produkcji: najnowszy">najnowszy</option>
                                        <option value="date-ascending" title="rok produkcji: najstarszy">najstarszy</option>
                                    </optgroup>
                                </select>
                            </div>
                        </div>
                    </div>
			    </form>

				<div class="row card-columns justify-content-center">

					<div class="col-xl-4 col-md-6 col-sm-12">
						<div class="card mb-5 film-card">
							<img class="card-img-top" src="images-w1400.jpg" alt="Card image cap">
							<div class="card-body">
								<h4 class="card-title green-font-color">Moonlight</h4>
								<h6 class="card-subtitle mb-2 text-muted">2017</h6>
				<!--<p class="card-text">Some quick example text to build on the</p>-->
							</div>
							<div class="card-footer text-right box-colors">
								<span class="orange-font-color">★</span> <span class="text-muted">7,2</span>
							</div>
						</div>
					</div>

					<div class="col-xl-4 col-md-6 col-sm-12">
						<div class="card mb-5 film-card">
							<img class="card-img-top" src="batman.jpg" alt="Card image cap">
							<div class="card-body">
								<h4 class="card-title green-font-color">Batman</h4>
								<h6 class="card-subtitle mb-2 text-muted">2017</h6>
				<!--<p class="card-text">Some quick example text to build on the</p>-->
							</div>
							<div class="card-footer text-right box-colors">
								<span class="orange-font-color">★</span> <span class="text-muted">7,2</span>
							</div>
						</div>
					</div>

					<div class="col-xl-4 col-md-6 col-sm-12">
						<div class="card mb-5 film-card">
							<img class="card-img-top" src="lord.jpg" alt="Card image cap">
							<div class="card-body">
								<h4 class="card-title green-font-color">Lord of war</h4>
								<h6 class="card-subtitle mb-2 text-muted">2017</h6>
				<!--<p class="card-text">Some quick example text to build on the</p>-->
							</div>
							<div class="card-footer text-right box-colors">
								<span class="orange-font-color">★</span> <span class="text-muted">7,2</span>
							</div>
						</div>
					</div>

					<div class="col-xl-4 col-md-6 col-sm-12">
						<div class="card mb-5 film-card">
							<img class="card-img-top" src="las.jpg" alt="Card image cap">
							<div class="card-body">
								<h4 class="card-title green-font-color">Las Vegas Parano</h4>
								<h6 class="card-subtitle mb-2 text-muted">2017</h6>
				<!--<p class="card-text">Some quick example text to build on the</p>-->
							</div>
							<div class="card-footer text-right box-colors">
								<span class="orange-font-color">★</span> <span class="text-muted">7,2</span>
							</div>
						</div>
					</div>

					<div class="col-xl-4 col-md-6 col-sm-12">
						<div class="card mb-5 film-card">
							<img class="card-img-top" src="fight_club.jpg" alt="Card image cap">
							<div class="card-body">
								<h4 class="card-title green-font-color">Fight Club</h4>
								<h6 class="card-subtitle mb-2 text-muted">2017</h6>
				<!--<p class="card-text">Some quick example text to build on the</p>-->
							</div>
							<div class="card-footer text-right box-colors">
								<span class="orange-font-color">★</span> <span class="text-muted">7,2</span>
							</div>
						</div>
					</div>

					<div class="col-xl-4 col-md-6 col-sm-12">
						<div class="card mb-5 film-card">
							<img class="card-img-top" src="inception.jpg" alt="Card image cap">
							<div class="card-body">
								<h4 class="card-title green-font-color">Inception</h4>
								<h6 class="card-subtitle mb-2 text-muted">2017</h6>
				<!--<p class="card-text">Some quick example text to build on the</p>-->
							</div>
							<div class="card-footer text-right box-colors">
								<span class="orange-font-color">★</span> <span class="text-muted">7,2</span>
							</div>
						</div>
					</div>

					<div class="col-xl-4 col-md-6 col-sm-12">
						<div class="card mb-5 film-card border-none">
							<img class="card-img-top" src="dunkirk.jpg" alt="Card image cap">
							<div class="card-body">
								<h4 class="card-title green-font-color">Dunkirk</h4>
								<h6 class="card-subtitle mb-2 text-muted">2017</h6>
				<!--<p class="card-text">Some quick example text to build on the</p>-->
							</div>
							<div class="card-footer text-right box-colors">
								<span class="orange-font-color">★</span> <span class="text-muted">7,2</span>
							</div>
						</div>
					</div>

				</div>

				<div class="pagination justify-content-center mb-5">
				  <a href="#">&laquo;</a>
				  <a href="#">1</a>
				  <a class="active" href="#">2</a>
				  <a href="#">3</a>
				  <a href="#">4</a>
				  <a href="#">5</a>
				  <a href="#">6</a>
				  <a href="#">&raquo;</a>
				</div>

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
	</body>
</html>
