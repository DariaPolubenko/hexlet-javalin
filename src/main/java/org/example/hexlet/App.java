package org.example.hexlet;

import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;
import io.javalin.validation.ValidationException;
import org.example.hexlet.dto.courses.CoursePage;
import org.example.hexlet.dto.courses.CoursesPage;
import org.example.hexlet.dto.users.BuildUserPage;
import org.example.hexlet.dto.users.UsersPage;
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

        app.get("/", ctx -> ctx.render("index.jte"));

        app.get("/hello", ctx -> {
            var name = ctx.queryParamAsClass("name", String.class).getOrDefault("World");
            ctx.result("Hello, " + name + "!");
        });

        app.get("users/{id}/post/{postId}", ctx -> {
            var userId = ctx.pathParamAsClass("id", Long.class).get();
            var postId = ctx.pathParamAsClass("postId", Long.class).get();
            ctx.result("User ID: " + userId + "\nPost Id: " + postId);
        });

        app.get("/courses", ctx -> {
            var term = ctx.queryParam("term");

            if (term != null) {
                var filterCourses = new ArrayList<Course>();
                var normalizedTerm = term.toLowerCase();

                for (var course : CourseRepository.getEntities()) {
                    var name = course.getName().toLowerCase();
                    var description = course.getDescription().toLowerCase();

                    if (name.contains(normalizedTerm) || description.contains(normalizedTerm)) {
                        filterCourses.add(course);
                    }
                }
                var page = new CoursesPage(filterCourses);
                page.setTerm(term);
                ctx.render("courses/showCourses.jte", model("page", page));
            } else {
                var header = "Курсы по программированию";
                var page = new CoursesPage(CourseRepository.getEntities());
                page.setHeader(header);
                ctx.render("courses/showCourses.jte", model("page", page));
            }
        });

        app.get("/courses/build", ctx -> {
            ctx.render("courses/build.jte");
        });

        app.post("/courses", ctx -> {
            var name = ctx.formParam("name");
            var description = ctx.formParam("description");

            var course = new Course(name, description);
            CourseRepository.save(course);
            ctx.redirect("/courses");
        });

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
            var page = new BuildUserPage();
            ctx.render("users/build.jte", model("page", page));
        });

        app.post("/users", ctx -> {
            var name = ctx.formParam("name");
            var email = ctx.formParam("email").trim().toLowerCase();;

            try {
                var passwordConfirmation = ctx.formParam("passwordConfirmation");
                var password = ctx.formParamAsClass("password", String.class)
                        .check(value -> value.equals(passwordConfirmation), "Пароли не совпадают")
                        .get();
                var user = new User(name, email, password);
                UserRepository.save(user);
                ctx.redirect("/users");
            } catch (ValidationException e) {
                var page = new BuildUserPage(name, email, e.getErrors());
                ctx.render("users/build.jte", model("page", page));
            }
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
