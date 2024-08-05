package org.example.hexlet;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;
import org.example.hexlet.controller.CoursesController;
import org.example.hexlet.controller.SessionsController;
import org.example.hexlet.controller.UsersController;
import org.example.hexlet.dto.MainPage;
import org.example.hexlet.repository.BaseRepository;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import static io.javalin.rendering.template.TemplateUtil.model;
import static org.example.hexlet.NamedRoutes.*;

public class App {

    public static void main(String[] args) throws Exception {
        //addCourses();

        var hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl("jdbc:h2:mem:hexlet_project;DB_CLOSE_DELAY=-1;");

        var dataSource = new HikariDataSource(hikariConfig);

        var url = App.class.getClassLoader().getResourceAsStream("schema.sql");
        var sql = new BufferedReader(new InputStreamReader(url))
                .lines().collect(Collectors.joining("\n"));

        try (var connection = dataSource.getConnection();
             var statement = connection.createStatement()) {
            statement.execute(sql);
        }
        BaseRepository.dataSource = dataSource;

        var app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
            config.fileRenderer(new JavalinJte());
        });

        app.get(mainPath(), ctx -> {
            var visited = Boolean.valueOf(ctx.cookie("visited"));
            String currentUser = ctx.sessionAttribute("currentUser");
            var page = new MainPage(visited, currentUser);
            ctx.render("index.jte", model("page", page));
            ctx.cookie("visited", String.valueOf(true));
        });

        app.get(helloPath(), ctx -> {
            var name = ctx.queryParamAsClass("name", String.class).getOrDefault("World");
            ctx.result("Hello, " + name + "!");
        });

        app.get(NamedRoutes.buildSessionsPath(), SessionsController::build);
        app.post(NamedRoutes.sessionsPath(), SessionsController::create);
        app.post(NamedRoutes.destroySessionsPath(), SessionsController::destroy);

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

    /*
    public static void addCourses() {
        List<Course> courses = new ArrayList<>(List.of(new Course("Java-разработчик", getDescription("javaDescription.txt")),
                new Course("PHP-разработчик", getDescription("phpDescription.txt")),
                new Course("Python-разработчик", getDescription("pythonDescription.txt"))));

        for (var course : courses) {
            CourseRepository.save(course);
        }
    }
     */

    public static String getDescription(String file) {
        var path = Paths.get("/Users/new/Desktop/Develop/HexletJavalin/src/main/resources/" + file).toAbsolutePath().normalize();
        try {
            return Files.readString(path);
        } catch (Exception e) {
            return "Описание отсутствует";
        }
    }
}
