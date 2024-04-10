package labs.secondSemester.commons.objects.forms;

import labs.secondSemester.commons.exceptions.FailedBuildingException;
import labs.secondSemester.commons.exceptions.IllegalValueException;
import labs.secondSemester.commons.managers.Validator;
import labs.secondSemester.commons.objects.Coordinates;

import java.util.Scanner;

/**
 * Класс для формирования объектов типа {@link Coordinates}.
 *
 * @author Kseniya
 */
public class CoordinatesForm extends Form<Coordinates> {

    /**
     * Собирает объект класса {@link Coordinates}.
     *
     * @return новый объект класса {@link Coordinates}.
     * @throws IllegalValueException   - при недопустимом значении в одном из полей
     * @throws FailedBuildingException - при ошибке сборки
     */
    @Override
    public Coordinates build(Scanner scanner, boolean fileMode) throws IllegalValueException, FailedBuildingException {
        Long x = askLong(scanner, fileMode, "координату х", false);
        float y = askFloat(scanner, fileMode, "координату y");

        Coordinates newCoords = new Coordinates(x, y);
        if (!Validator.coordValidation(newCoords)) {
            throw new FailedBuildingException("Недопустимое значение в поле!", Coordinates.class);
        }
        return newCoords;
    }

}
