package org.example.hexlet.controller;

import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import io.javalin.validation.ValidationException;
import org.example.hexlet.NamedRoutes;
import org.example.hexlet.dto.courses.BuildCoursePage;
import org.example.hexlet.dto.courses.CoursePage;
import org.example.hexlet.dto.courses.CoursesPage;
import org.example.hexlet.model.Course;
import org.example.hexlet.repository.CourseRepository;

import static io.javalin.rendering.template.TemplateUtil.model;

public class CoursesController {
    public static void show(Context ctx) {
        var term = ctx.queryParam("term");

        if (term != null) {
            var normalizedTerm = term.toLowerCase();
            var filterCourses = CourseRepository.getEntities().stream()
                    .filter(c -> {
                        var name =c.getName().toLowerCase();
                        var description = c.getDescription().toLowerCase();
                        return name.contains(normalizedTerm) || description.contains(normalizedTerm);
                    }).toList();

            var page = new CoursesPage(filterCourses);
            page.setTerm(term);
            ctx.render("courses/showCourses.jte", model("page", page));

        } else {
            String flash = ctx.consumeSessionAttribute("flash");
            var header = "Курсы по программированию";
            var page = new CoursesPage(CourseRepository.getEntities());
            page.setHeader(header);
            page.setFlash(flash);
            ctx.render("courses/showCourses.jte", model("page", page));
        }
    }

    public static void build(Context ctx) {
        String flash = ctx.consumeSessionAttribute("flash2");
        var page = new BuildCoursePage();
        page.setFlash(flash);
        ctx.render("courses/build.jte", model("page", page));
    }

    public static void find(Context ctx) {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var course = CourseRepository.find(id).orElseThrow(() -> new NotFoundResponse("Course with id = " + id + " not found"));
        var page = new CoursePage(course);
        ctx.render("courses/showCourse.jte", model("page", page));

    }

    public static void create(Context ctx) {
        try {
            var name = ctx.formParamAsClass("name", String.class)
                    .check(value -> value.length() > 2, "Имя не может содержать менее 3 символов")
                    .get();
            var description = ctx.formParamAsClass("description", String.class)
                    .check(value -> value.length() > 10, "Описание не может содержать менее 10 символов")
                    .get();
            var course = new Course(name, description);
            CourseRepository.save(course);
            ctx.sessionAttribute("flash", "Курс успешно добавлен!");
            ctx.redirect(NamedRoutes.coursesPath());

        } catch (ValidationException e) {
            var name = ctx.formParam("name");
            var description = ctx.formParam("description");

            ctx.sessionAttribute("flash2", "Курс не был добавлен!");
            var page = new BuildCoursePage(name, description, e.getErrors());
            ctx.render("courses/build.jte", model("page", page));
        }
    }
}
