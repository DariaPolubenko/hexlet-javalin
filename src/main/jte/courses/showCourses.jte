@import org.example.hexlet.dto.courses.CoursesPage
@param CoursesPage page

@template.layout.page(
content = @`
    <h2>${page.getHeader()}</h2>
    @if(page.getCourses().isEmpty())
        <p>Пока не добавлено ни одного курса</p>
    @else
        @for(var course : page.getCourses())
            <div>
                <h3><a href="/courses/${course.getId()}">${course.getName()}</a></h3>
                <p>${course.getDescription()}</p>
            </div>
        @endfor
    @endif
    `
)
