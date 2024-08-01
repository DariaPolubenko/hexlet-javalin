@import org.example.hexlet.NamedRoutes

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
<form action="${NamedRoutes.sessionsPath()}" method="post">
  <div class="mb-3">
    <label>
      <input type="text" class="form-control" placeholder="Ник" name="nickname" />
    </label>
  </div>
  <div class="mb-3">
    <label>
      <input type="password" class="form-control" placeholder="Пароль" name="password" />
    </label>
  </div>
  <div class="mb-3">
    <label>
      <input type="submit" class="btn btn-primary mb-3" value="Отправить"/>
    </label>
  </div>
</form>
<footer>
  <a href="https://github.com/DariaPolubenko">GitHub</a>
</footer>
</body>
</html>
