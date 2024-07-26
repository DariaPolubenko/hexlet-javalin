@template.layout.page(
content = @`
    <p style="margin-top: 35px" class="fw-light">Добавление пользователя</p>
    <form style="margin-top: 15px" action="/users/build" method="post">
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
`
)