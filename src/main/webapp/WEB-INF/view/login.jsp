<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
</head>
<body>
<h3>Login</h3>
<form method="POST" action="/login">
    <table>
        <tr>
            <td><input name="username"/></td>
        </tr>
        <tr>
            <td><input name="password"/></td>
        </tr>
        <tr>
            <td><input type="submit" value="Submit"/></td>
        </tr>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </table>
</form>
</body>
</html>