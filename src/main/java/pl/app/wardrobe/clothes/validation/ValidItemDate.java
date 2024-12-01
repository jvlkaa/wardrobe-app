package pl.app.wardrobe.clothes.validation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import pl.app.wardrobe.clothes.validation.validator.ItemDateValidator;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ItemDateValidator.class)
@Documented
public @interface ValidItemDate {
    String message() default "date must be later than {limit}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int limit() default 1900;
}
