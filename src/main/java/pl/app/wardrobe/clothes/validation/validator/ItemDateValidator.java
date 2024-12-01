package pl.app.wardrobe.clothes.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import pl.app.wardrobe.clothes.domain.DateHolder;
import pl.app.wardrobe.clothes.validation.ValidItemDate;

public class ItemDateValidator implements ConstraintValidator<ValidItemDate, DateHolder> {
    private int limit;

    @Override
    public void initialize(ValidItemDate constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        limit = constraintAnnotation.limit();
    }

    @Override
    public boolean isValid(DateHolder value, ConstraintValidatorContext context) {
        return value.getPurchaseDate().getYear() >= limit;
    }

}
