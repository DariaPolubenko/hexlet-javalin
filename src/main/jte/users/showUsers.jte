@import org.example.hexlet.dto.models.UsersPage
@param UsersPage page

@template.layout.page(
content = @`
    @if(page.getUsers().isEmpty())
        <p>Пока не добавлено ни одного пользователя</p>
    @else
        @for(var user : page.getUsers())
            <div>
                <h3><a href="/courses/${user.getId()}">${user.getName()}</a></h3>
                <p>${user.getEmail()}</p>
            </div>
        @endfor
    @endif
`
)
