package labs.secondSemester.commons.exceptions;

import lombok.Getter;

/**
 * Исключение, которое пробрасывается при получении недопустимого значения.
 * Используется при распознавании команды, проверки аргументов команды на валидность.
 *
 * @author Kseniya
 */
@Getter
public class IllegalValueException extends Exception {

    public IllegalValueException(String message) {
        super(message);
    }
}
