<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
          crossorigin="anonymous">
    <title>Зарагистрироваться</title>
    <h3 style="margin-bottom: 30px"><a href="/">Главная</a></h3>
</head>
<body>
    <form action="/users/build" method="post">
        <div class="mb-3">
            <label>
                <input type="text" class="form-control" placeholder="Имя" name="name"/>
            </label>
        </div>
        <div class="mb-3">
            <label>
                <input type="email" class="form-control" placeholder="Email" required name="email" />
            </label>
        </div>
        <div class="mb-3">
            <label>
                <input type="password" class="form-control" placeholder="Пароль" required name="password" />
            </label>
        </div>
        <div class="mb-3">
            <label>
                <input type="password" class="form-control" placeholder="Подтверждение пароля" required name="passwordConfirmation" />
            </label>
        </div>
        <input type="submit" class="btn btn-primary mb-3" value="Зарегистрироваться"/>
    </form>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
            crossorigin="anonymous"></script>
    </body>
</html>
