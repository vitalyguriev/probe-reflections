
package beans;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)

public @interface DBTableMap {
    String tableName();
}