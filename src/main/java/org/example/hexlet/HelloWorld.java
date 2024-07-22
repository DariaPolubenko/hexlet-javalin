package org.example.hexlet;

import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;

import java.util.ArrayList;
import java.util.List;

import static io.javalin.rendering.template.TemplateUtil.model;


public class HelloWorld {

    private static Long countCourse = 1L;

    public static void main(String[] args) {
        var app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
            config.fileRenderer(new JavalinJte());
        });

        app.get("/users", ctx -> ctx.result("GET /users"));
        app.post("/users", ctx -> ctx.result("POST /users"));


        app.get("/hello", ctx -> {
            var name = ctx.queryParamAsClass("name", String.class).getOrDefault("World");
            ctx.result("Hello, " + name + "!");
        });


        app.get("users/{id}/post/{postId}", ctx -> {
            var userId = ctx.pathParamAsClass("id", Long.class).get();
            var postId = ctx.pathParamAsClass("postId", Long.class).get();
            ctx.result("User ID: " + userId + "\nPost Id: " + postId);
        });


        app.get("/render", ctx -> ctx.render("index.jte"));

        app.get("/courses", ctx -> {
            var courses = new ArrayList<>(List.of(
                    addCourse("Java-разработчик", "Описание"),
                    addCourse("PHP-разработчик", "Описание"),
                    addCourse("Python-разработчик", "Описание")));

            var header = "Курсы по программированию";
            var page = new CoursesPage(courses, header);
            ctx.render("index2.jte", model("page", page));
        });

        app.get("/courses/{id}", ctx -> {
            var id = ctx.pathParam("id");
            var course = new Course("Java-разработчик", "Описание");
            var page = new CoursePage(course);
            ctx.render("show.jte", model("page", page));
        });

        app.start(7070);
    }

    public static Course addCourse(String name, String description) {
        var course = new Course(name, description);
        course.setId(countCourse);
        countCourse ++;

        return course;
    }
}
