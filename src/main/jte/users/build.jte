@import org.example.hexlet.dto.users.BuildUserPage
@param BuildUserPage page

@template.layout.page(
content = @`
    @if(page.getErrors() != null)
        <ul>
            @for(var validator : page.getErrors().values())
                @for(var error : validator)
                    <li>${error.getMessage()}</li>
                @endfor
            @endfor
        </ul>
    @endif

    <p style="margin-top: 35px" class="fw-light">Добавление пользователя</p>

    <form action="/users" method="post" style="margin-top: 15px">
        <div class="mb-3">
            <label>
                <input type="text" class="form-control" placeholder="Имя" name="name" value="${page.getName()}"/>
            </label>
        </div>

        <div class="mb-3">
            <label>
                <input type="email" class="form-control" placeholder="Email" required name="email" value="${page.getEmail()}"/>
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