package org.example.hexlet;

import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;
import org.example.hexlet.controller.CoursesController;
import org.example.hexlet.controller.UsersController;
import org.example.hexlet.model.Course;
import org.example.hexlet.repository.CourseRepository;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static io.javalin.rendering.template.TemplateUtil.model;
import static org.example.hexlet.NamedRoutes.*;


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

        app.get(mainPath(), ctx -> ctx.render("index.jte"));

        app.get(helloPath(), ctx -> {
            var name = ctx.queryParamAsClass("name", String.class).getOrDefault("World");
            ctx.result("Hello, " + name + "!");
        });

        app.get(coursesPath(), CoursesController::show);
        app.get(buildCoursesPath(), CoursesController::build);
        app.post(coursesPath(), CoursesController::create);
        app.get(coursesPath("{id}"), CoursesController::find);

        app.get(usersPath(), UsersController::showUsers);
        app.get(usersPath("{id}", "{postId}"), UsersController::getPost);
        app.get(buildUsersPath(), UsersController::build);
        app.post(usersPath(), UsersController::create);

        //test attacks
        app.get(usersPath("{id}"), UsersController::testAttack);

        app.get("/attack/{text}", ctx -> {
            var text = ctx.pathParam("text");
            ctx.contentType("html");
            ctx.render("attack.jte", model("text", text));
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
