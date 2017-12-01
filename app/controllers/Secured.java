package controllers;

import controllers.routes;
import play.mvc.*;
import play.mvc.Http.Context;

public class Secured extends Security.Authenticator {
    /**
     * Механизм аутентификации
     *
     * @param ctx контекст запроса.
     * @return строку-email для текущего пользователя, хранящуюся в сессии при аутентификации. В случае ее отсутствия возвращает null
     */
    @Override
    public String getUsername(Context ctx) {
        return ctx.session().get("email");
    }

    /**
     * Перенаправление в случае неуспеха аутентификации. Как правило перенаправляет на форму логина
     *
     * @param ctx контекст
     * @return перенаправление на страницу логина
     */

	@Override
	public Result onUnauthorized(Context ctx) {
		return redirect(routes.Auth.login());
	}
}