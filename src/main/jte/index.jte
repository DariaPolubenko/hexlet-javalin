@import org.example.hexlet.dto.MainPage
@param MainPage page

@template.layout.page(
content = @`
    <h3 style="margin-top: 25px" class="fw-light" class="text-body-emphasis">Привет, Хекслет!</h3>
    <p>Javalin + jte</p>
    @if(!page.isVisited())
        <p>Это сообщение показывается только один раз. Если вы хотите увидеть его снова, сотрите куки</p>
    @endif
`
)
