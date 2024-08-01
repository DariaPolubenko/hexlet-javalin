package org.example.hexlet.controller;

import io.javalin.http.Context;

import static org.example.hexlet.NamedRoutes.mainPath;

public class SessionsController {

    public static void build(Context ctx) {
        ctx.render("sessions/build.jte");
    }

    public static void create(Context ctx) {
        var nickname = ctx.formParam("nickname");
        var password = ctx.formParam("password");

        // Тут должна быть проверка пароля

        ctx.sessionAttribute("currentUser", nickname);
        ctx.redirect(mainPath());
    }

    public static void destroy(Context ctx) {
        ctx.sessionAttribute("currentUser", null);
        ctx.redirect("/");
    }
}