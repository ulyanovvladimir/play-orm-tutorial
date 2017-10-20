package controllers;

import io.ebean.PagedList;
import models.Feature;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.*;

import play.twirl.api.Html;
import router.Routes;
import views.html.*;

import javax.inject.Inject;
import java.util.List;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

    private final FormFactory formFactory;

    @Inject
    public HomeController(FormFactory formFactory) {
        this.formFactory = formFactory;
    }

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index() {
        return ok(index.render("Your new application is ready."));
    }

    public Result list(){
        List<Feature> page = Feature.find.all();
        return ok(list.render(page));
    }


    public Result create(){
        Form<Feature> featureForm = formFactory.form(Feature.class);
        return ok(createForm.render(featureForm));
    }

    public Result save(){
        Form<Feature> featureForm = formFactory.form(Feature.class).bindFromRequest();
        if (featureForm.hasErrors()){
            return badRequest(createForm.render(featureForm));
        } else {
            //получаем из формы
            Feature f = featureForm.get();
            //сохраняем
            f.save();

            return redirect(routes.HomeController.list());
        }
    }

    public Result edit(Long id){
        Feature f = Feature.find.byId(id);
        if (f == null){
            return notFound();
        }
        Form<Feature> featureForm = formFactory.form(Feature.class).fill(f);

        return ok(editForm.render(id, featureForm));
    }

    public Result update(Long id){
        Form<Feature> featureForm = formFactory.form(Feature.class).bindFromRequest();
        if (featureForm.hasErrors()){
            return badRequest(editForm.render(id, featureForm));
        } else {
            Feature f1 = Feature.find.byId(id);
            if(f1 == null) return notFound();
            Feature f2 = featureForm.get();
            f1.title = f2.title;
            f1.description = f2.description;
            f1.update();
            return redirect(routes.HomeController.list());
        }
    }

    public Result delete(Long id){
        Feature f = Feature.find.byId(id);
        if (f == null){
            return notFound();
        }

        f.delete();
        return redirect(routes.HomeController.list());
    }
}