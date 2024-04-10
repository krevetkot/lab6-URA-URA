package labs.secondSemester.commons.exceptions;


/**
 * Исключение, которое пробрасывается при возникновении ошибки во время сборки объекта класса.
 * Используется, когда заполнены не все поля, которые должны.
 *
 * @author Kseniya
 */
public class FailedBuildingException extends Exception {

    public FailedBuildingException(String message, Class<?> problemClass) {
        super(message + " Проблема в классе: " + problemClass);
    }
}
