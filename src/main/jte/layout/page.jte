@import gg.jte.Content
@param Content content

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
<body>

<div class="container text-center">
    <div class="row align-items-center">
        <div class="col">
            <p>Hexlet Javalin Example</p>
        </div>
        <div class="col">
            <p><a href="/general">Главная</a></p>
        </div>
        <div class="col">
            <p><a href="/courses">Курсы</a></p>
        </div>
        <div class="col">
            <p><a href="/users/build">Добавить пользователя</a></p>
        </div>
    </div>
</div>
${content}
<footer>
    <a href="https://github.com/DariaPolubenko">GitHub</a>
</footer>
</body>
</html>
