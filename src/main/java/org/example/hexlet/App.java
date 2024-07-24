package org.example.hexlet;

import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;
import org.apache.commons.text.StringEscapeUtils;
import org.example.hexlet.dto.courses.CoursePage;
import org.example.hexlet.dto.courses.CoursesPage;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static io.javalin.rendering.template.TemplateUtil.model;


public class App {
    private static Long countCourse = 1L;

    private static List<Course> courses;

    static {
        try {
            courses = new ArrayList<>(List.of(
                    addCourse("Java-разработчик", getDescription("javaDescription.txt")),
                    addCourse("PHP-разработчик", getDescription("phpDescription.txt")),
                    addCourse("Python-разработчик", getDescription("pythonDescription.txt"))));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        var app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
            config.fileRenderer(new JavalinJte());
        });

        app.get("/", ctx -> ctx.result("Welcome!"));
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
            var header = "Курсы по программированию";
            var page = new CoursesPage(courses, header);
            ctx.render("courses/showCourses.jte", model("page", page));
        });

        app.get("/courses/{id}", ctx -> {
            var id = ctx.pathParamAsClass("id", Integer.class).get();
            int intId = id;

            for (var course : courses) {
                if (course.getId() == intId) {
                    var page = new CoursePage(course);
                    ctx.render("courses/showCourse.jte", model("page", page));
                    break;
                }
            }
        });

        //test attacks
        app.get("/users/{id}", ctx -> {
            var id = ctx.pathParam("id");
            var escapedId = StringEscapeUtils.escapeHtml4(id);
            ctx.contentType("text/html");
            ctx.result(escapedId);
        });


        app.get("/attack/{text}", ctx -> {
            var text = ctx.pathParam("text");
            ctx.contentType("html");
            ctx.render("attack.jte", model("text", text));
        });

        app.start(7070);
    }

    public static Course addCourse(String name, String description) {
        var course = new Course(name, description);
        course.setId(countCourse);
        countCourse ++;

        return course;
    }

    public static String getDescription(String file) {
        var path = Paths.get("/Users/new/Desktop/Develop/HexletJavalin/src/main/resources/" + file).toAbsolutePath().normalize();

        try {
            return Files.readString(path);
        } catch (Exception e) {
            return "Описание отсутствует";
        }
    }
}
