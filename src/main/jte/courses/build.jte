@import org.example.hexlet.NamedRoutes
@import org.example.hexlet.dto.courses.BuildCoursePage
@param BuildCoursePage page

@template.layout.page(
content = @`
    @if(page.getErrors() != null)
        <div class="alert alert-danger" role="alert">
            <ul>
                @for(var validator : page.getErrors().values())
                    @for(var error : validator)
                        <li>${error.getMessage()}</li>
                    @endfor
                @endfor
            </ul>
        </div>
    @endif

    <p style="margin-top: 35px" class="fw-light">Добавление курса</p>
    <form action="${NamedRoutes.coursesPath()}" method="post">
        <div class="mb-3">
            <label>
                <input type="text" class="form-control" placeholder="Название" name="name" value="${page.getName()}"/>
            </label>
        </div>
        <div class="mb-3">
            <label>
                <input type="text" class="form-control" placeholder="Описание" name="description" value="${page.getDescription()}"/>
            </label>
        </div>
        <input type="submit" class="btn btn-primary mb-3" value="Добавить"/>
    </form>
    `
)