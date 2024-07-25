package org.example.hexlet;

import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;
import org.apache.commons.lang3.StringUtils;
import org.example.hexlet.dto.models.CoursesPage;
import org.example.hexlet.dto.models.UsersPage;
import org.example.hexlet.model.Course;
import org.example.hexlet.model.User;
import org.example.hexlet.repository.CourseRepository;
import org.example.hexlet.repository.UserRepository;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static io.javalin.rendering.template.TemplateUtil.model;


public class App {
    private static Long countCourse = 1L;

    private static List<Course> courses = new ArrayList<>(List.of(new Course("Java-разработчик", getDescription("javaDescription.txt")),
            new Course("PHP-разработчик", getDescription("phpDescription.txt")),
            new Course("Python-разработчик", getDescription("pythonDescription.txt"))));

    public static void main(String[] args) {
        for (var course : courses) {
            CourseRepository.save(course);
        }

        var app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
            config.fileRenderer(new JavalinJte());
        });

        app.get("/", ctx -> ctx.result("Welcome!"));

        app.get("/hello", ctx -> {
            var name = ctx.queryParamAsClass("name", String.class).getOrDefault("World");
            ctx.result("Hello, " + name + "!");
        });

        app.get("users/{id}/post/{postId}", ctx -> {
            var userId = ctx.pathParamAsClass("id", Long.class).get();
            var postId = ctx.pathParamAsClass("postId", Long.class).get();
            ctx.result("User ID: " + userId + "\nPost Id: " + postId);
        });

        app.get("/general", ctx -> ctx.render("index.jte"));

        app.get("/courses", ctx -> {
            var term = ctx.queryParam("term");
            String header = "";

            List<Course> filterCourses;
            if (term != null) {
                filterCourses = CourseRepository.getEntities().stream()
                        .filter(c -> StringUtils.startsWithIgnoreCase(c.getName(), term) || StringUtils.startsWithIgnoreCase(c.getDescription(), term))
                        .toList();
            } else {
                filterCourses = CourseRepository.getEntities();
                header = "Курсы по программированию";
            }
            var page = new CoursesPage(filterCourses, header, term);
            ctx.render("courses/showCourses.jte", model("page", page));
        });
/*
        app.get("/courses/{id}", ctx -> {
            int id = ctx.pathParamAsClass("id", Integer.class).get();

            for (var course : CourseRepository.getEntities()) {
                if (course.getId() == id) {
                    var page = new CoursePage(course);
                    ctx.render("courses/showCourse.jte", model("page", page));
                    break;
                }
            }
        });
 */

        app.get("/courses/build", ctx -> {
            ctx.render("courses/build.jte");
        });

        app.post("/courses/build", ctx -> {
            var name = ctx.formParam("name");
            var description = ctx.formParam("description");

            var course = new Course(name, description);
            CourseRepository.save(course);
            ctx.redirect("/courses");
        });

        /*
        //test attacks
        app.get("/users/{id}", ctx -> {
            var id = ctx.pathParam("id");
            var escapedId = StringEscapeUtils.escapeHtml4(id);
            ctx.contentType("text/html");
            ctx.result(escapedId);
        });
         */

        app.get("/attack/{text}", ctx -> {
            var text = ctx.pathParam("text");
            ctx.contentType("html");
            ctx.render("attack.jte", model("text", text));
        });

        app.get("/users/build", ctx -> {
            ctx.render("users/build.jte");
        });

        app.post("/users/build", ctx -> {
            var name = ctx.formParam("name");
            var email = ctx.formParam("email").trim().toLowerCase();;
            var password = ctx.formParam("password");
            var passwordConfirmation = ctx.formParam("passwordConfirmation");

            var user = new User(name, email, password);
            UserRepository.save(user);
            ctx.redirect("/users");
        });

        app.get("/users", ctx -> {
            var users = UserRepository.getEntities();
            var page = new UsersPage(users);
            ctx.render("users/showUsers.jte", model("page", page));
        });

        app.start(7070);
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
