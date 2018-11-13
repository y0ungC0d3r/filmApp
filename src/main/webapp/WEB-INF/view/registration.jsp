<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->

    <title>Create an account</title>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>

    <![endif]-->
</head>

<body>

    <form:form method="POST" modelAttribute="userForm">
        <h2>Create your account</h2>
        <spring:bind path="username">
            <div>${status.error ? 'has-error' : ''}</div>
                <form:input type="text" path="username" autofocus="true"></form:input>
                <form:errors path="username"></form:errors>
        </spring:bind>

        <spring:bind path="password">
            <div>${status.error ? 'has-error' : ''}</div>
                <form:input type="password" path="password" ></form:input>
                <form:errors path="password"></form:errors>
        </spring:bind>

        <spring:bind path="passwordConfirm">
            <div>${status.error ? 'has-error' : ''}</div>
                <form:input type="password" path="passwordConfirm" class="form-control"
                            placeholder="Confirm your password"></form:input>
                <form:errors path="passwordConfirm"></form:errors>
        </spring:bind>

        <button type="submit">Submit</button>
    </form:form>

</body>
</html>
