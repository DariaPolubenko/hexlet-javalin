package org.example.hexlet.controller;

import io.javalin.http.Context;
import io.javalin.validation.ValidationException;
import org.apache.commons.text.StringEscapeUtils;
import org.example.hexlet.NamedRoutes;
import org.example.hexlet.dto.users.BuildUserPage;
import org.example.hexlet.dto.users.UsersPage;
import org.example.hexlet.model.User;
import org.example.hexlet.repository.UserRepository;

import static io.javalin.rendering.template.TemplateUtil.model;
import static org.apache.commons.lang3.StringUtils.capitalize;

public class UsersController {
    public static void showUsers(Context ctx) {
        var users = UserRepository.getEntities();
        String flash = ctx.consumeSessionAttribute("flash");
        var page = new UsersPage(users);
        page.setFlash(flash);
        ctx.render("users/showUsers.jte", model("page", page));
    }

    public static void build(Context ctx) {
        String flash = ctx.consumeSessionAttribute("flash");
        var page = new BuildUserPage();
        page.setFlash(flash);
        ctx.render("users/build.jte", model("page", page));
    }

    public static void getPost(Context ctx) {
        var userId = ctx.pathParamAsClass("id", Long.class).get();
        var postId = ctx.pathParamAsClass("postId", Long.class).get();
        ctx.result("User ID: " + userId + "\nPost Id: " + postId);
    }

    public static void testAttack(Context ctx) {
        var id = ctx.pathParam("id");
        var escapedId = StringEscapeUtils.escapeHtml4(id);
        ctx.contentType("text/html");
        ctx.result(escapedId);
    }

    public static void create(Context ctx) {
        var name = capitalize(ctx.formParam("name"));
        var email = ctx.formParam("email").trim().toLowerCase();
        try {
            var passwordConfirmation = ctx.formParam("passwordConfirmation");
            var password = ctx.formParamAsClass("password", String.class)
                    .check(value -> value.equals(passwordConfirmation), "Пароли не совпадают")
                    .get();
            var user = new User(name, email, password);
            UserRepository.save(user);
            ctx.sessionAttribute("flash", "Пользователь успешно добавлен");
            ctx.redirect(NamedRoutes.usersPath());
        } catch (ValidationException e) {
            var page = new BuildUserPage(name, email, e.getErrors());
            ctx.render("users/build.jte", model("page", page));
        }
    }
}
