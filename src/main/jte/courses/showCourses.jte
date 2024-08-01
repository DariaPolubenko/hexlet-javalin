@import org.example.hexlet.NamedRoutes
@import org.example.hexlet.dto.courses.CoursesPage
@param CoursesPage page

@template.layout.page(
    page = page,
    content = @`
    <form style="margin-top: 10px" action="/courses" method="get">
        <input type="search" name="term" value="${page.getTerm()}">
        <input type="submit" value="Искать">
    </form>
    <div class="fw-light" style="margin-top: 35px">
        @if(page.getHeader() != null)
            <h2 class="fw-light" style="margin-bottom: 15px">${page.getHeader()}</h2>
        @endif

        @if(page.getCourses().isEmpty())
            <p>Пока не добавлено ни одного курса</p>
        @else
            @for(var course : page.getCourses())
                <div>
                    <h3 class="fw-light"><a href="${NamedRoutes.coursesPath(course.getId())}">${course.getName()}</a></h3>
                    <p>${course.getDescription()}</p>
                </div>
            @endfor
        @endif
    </div>
    `
)
