package models;

import java.util.*;
import javax.persistence.*;

import io.ebean.*;
import play.data.format.*;
import play.data.validation.*;

@Entity
public class Feature extends Model {

    @Id
    public Long id;

    @Constraints.Required
    public String title;

    public String description;

    public static final Finder<Long, Feature> find = new Finder<>(Feature.class);
}