@import org.example.hexlet.dto.BasePage
@import org.example.hexlet.NamedRoutes
@import gg.jte.Content
@param Content content
@param BasePage page = null

<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
          crossorigin="anonymous">
    <title>Hexlet Javalin Example</title>
</head>
<body class="container">
    <p class="fw-light" style="margin-top: 10px">Hexlet Javalin Example</p>
    <div class="btn-group row">
        <p class="d-inline-flex gap-1">
            <a href="${NamedRoutes.mainPath()}" class="btn btn-outline-primary rounded-1 fw-light">Главная</a>
            <a href="${NamedRoutes.coursesPath()}" class="btn btn-outline-primary rounded-1 fw-light">Курсы</a>
            <a href="${NamedRoutes.buildCoursesPath()}" class="btn btn-outline-primary rounded-1 fw-light">Добавить курс</a>
            <a href="${NamedRoutes.usersPath()}" class="btn btn-outline-primary rounded-1 fw-light">Пользователи</a>
            <a href="${NamedRoutes.buildUsersPath()}" class="btn btn-outline-primary rounded-1 fw-light">Добавить пользователя</a>
        </p>
    </div>
    @if(page != null && page.getFlash() != null)
        <div class="alert alert-success" role="alert">
            ${page.getFlash()}
        </div>
    @endif
    ${content}
    <footer>
        <a href="https://github.com/DariaPolubenko">GitHub</a>
    </footer>
</body>
</html>
