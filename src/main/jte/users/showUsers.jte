@import org.example.hexlet.NamedRoutes
@import org.example.hexlet.dto.users.UsersPage
@param UsersPage page

@template.layout.page(
page = page,
content = @`
    @if(page.getUsers().isEmpty())
        <p>Пока не добавлено ни одного пользователя</p>
    @else
        @for(var user : page.getUsers())
            <div>
                <h3><a href="${NamedRoutes.usersPath(user.getId())}">${user.getName()}</a></h3>
                <p>${user.getEmail()}</p>
            </div>
        @endfor
    @endif
`
)
