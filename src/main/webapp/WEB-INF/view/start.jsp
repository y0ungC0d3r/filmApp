<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!doctype html>
<html lang="pl">
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
		<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.4.2/css/all.css" integrity="sha384-/rXc/GQVaYpyDdyxK+ecHPVYJSN9bmVFBvjA/9eOB+pb3F2w2N6fc5qB9Ew5yIns" crossorigin="anonymous">
		<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Pacifico">
        <link type="text/css" rel="stylesheet" href="${contextPath}/resources/style.css">

		<title>Start</title>
	</head>
	<body>

		<div class="container-fluid">
			<div class="row text-center box-colors shadow pb-4 mb-5">
				<div class="col-12">
					<i class="logo">filmore</i>
				</div>
			</div>
			<div class="row justify-content-center mb-5">
				<div class="col-lg-5 rounded mx-4 mb-4 p-0 box-colors shadow">
					<div class="clearfix border-bottom border-light dark-blue px-4 py-2 mb-4 rounded-top">
						<div class="float-left">
							<h2>Logowanie</h2>
							<p>Wypełnij nazwę użytkownika i hasło, aby się zalogować:</p>
						</div>
						<div class="float-right p-2">
							<i class="fa fa-lock display-4 text-warning" style="opacity: 0.7"></i>
						</div>
					</div>

					<div class="px-4 pb-4">
						<form method="post" action="/login">
					        <div class="form-group">
                                <input type="text" class="form-control" placeholder="Nazwa użytkownika">
                            </div>

					        <div class="form-group">
                                <input type="password" class="form-control" placeholder="Hasło">
                            </div>
							<button class="btn btn-warning btn-block mt-4" type="submit">Zaloguj się</button>
						</form>
					</div>
				</div>

				<div class="col-lg-5 rounded mx-4 p-0 box-colors shadow">
					<div class="clearfix border-bottom border-light dark-blue px-4 py-2 mb-4 rounded-top">
						<div class="float-left">
							<h2>Rejestracja</h2>
							<p>Wypełnij poniższe pola, aby uzyskać dostep do serwisu:</p>
						</div>
						<div class="float-right p-2">
							<i class="fas fa-pencil-alt display-4 text-warning" style="opacity: 0.7"></i>
						</div>
					</div>

					<div class="px-4 pb-4">
						<form:form method="post" action="register" modelAttribute="userForm">
						    <div class="form-group">
                                <spring:bind path="username">
                                    <form:input type="text" class="form-control ${status.error ? 'is-invalid' : 'is-valid'}" path="username" placeholder="Nazwa użytkownika"></form:input>
                                    <div class="invalid-feedback">
                                        <form:errors path="username"></form:errors>
                                    </div>
                                </spring:bind>
						    </div>
						    <div class="form-group">
                                <spring:bind path="password">
                                    <form:input type="password" class="form-control ${status.error ? 'is-invalid' : 'is-valid'}" path="password" placeholder="Hasło"></form:input>
                                    <div class="invalid-feedback">
                                        <form:errors path="password"></form:errors>
                                    </div>
                                </spring:bind>
						    </div>
						    <div class="form-group">
                                <spring:bind path="passwordConfirm">
                                    <form:input type="password" class="form-control ${status.error ? 'is-invalid' : 'is-valid'}" path="passwordConfirm" placeholder="Potwierdź hasło"></form:input>
                                    <div class="invalid-feedback">
                                        <form:errors path="passwordConfirm"></form:errors>
                                    </div>
                                </spring:bind>
						    </div>
							<button class="btn btn-warning btn-block mt-4" type="submit">Zarejestruj się</button>
						</form:form>
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
	</body>
</html>
