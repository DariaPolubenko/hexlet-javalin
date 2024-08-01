@import org.example.hexlet.NamedRoutes
@import org.example.hexlet.dto.MainPage
@param MainPage page


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
    @if(page.getCurrentUser() == null)
        <div class="btn-group row">
            <p class="d-inline-flex gap-1">
                <a href="${NamedRoutes.buildSessionsPath()}" class="btn btn-primary mb-3">Войти</a>
            </p>
        </div>
</div>
    @else
        <div class="btn-group row">
            <p class="d-inline-flex gap-1">
                <a href="${NamedRoutes.mainPath()}" class="btn btn-primary mb-3">Главная</a>
                <a href="${NamedRoutes.coursesPath()}" class="btn btn-primary mb-3">Курсы</a>
                <a href="${NamedRoutes.buildCoursesPath()}" class="btn btn-primary mb-3">Добавить курс</a>
                <a href="${NamedRoutes.usersPath()}" class="btn btn-primary mb-3">Пользователи</a>
                <a href="${NamedRoutes.buildUsersPath()}" class="btn btn-primary mb-3">Добавить пользователя</a>
            </p>
        </div>
    <p>Добро пожаловать, ${page.getCurrentUser()}.
        Чтобы разлогиниться, удалите куку JSESSIONID из браузера</p>
    @endif
    <h3 style="margin-top: 25px" class="fw-light" class="text-body-emphasis">Привет, Хекслет!</h3>
    <p>Javalin + jte</p>
    @if(!page.isVisited())
        <p>Это сообщение показывается только один раз. Если вы хотите увидеть его снова, сотрите куки</p>
    @endif
<footer>
    <a href="https://github.com/DariaPolubenko">GitHub</a>
</footer>
</body>
</html>
