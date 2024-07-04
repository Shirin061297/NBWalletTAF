package db.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Аннотация для указания имени таблицы, связанной с классом.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface TableI {
    String name();
}
