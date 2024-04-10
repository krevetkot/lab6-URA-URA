package labs.secondSemester.commons.managers;

import labs.secondSemester.commons.commands.Command;
import labs.secondSemester.commons.exceptions.IllegalValueException;
import labs.secondSemester.commons.objects.Coordinates;
import labs.secondSemester.commons.objects.Dragon;
import labs.secondSemester.commons.objects.Person;

/**
 * Класс, проверяющий объекты на валидность, согласно предоставленным условиям.
 *
 * @author Kseniya
 */
public class Validator {
    /**
     * Проверяет, что все необходимые поля экземпляра класса {@link Dragon} не равны null.
     *
     * @param dragon - проверяемый дракон
     * @return результат проверки
     */
    public static boolean dragonValidation(Dragon dragon) {
        if (dragon == null) {
            return false;
        }
        if (dragon.getKiller() == null){
            return !dragon.getName().isBlank() && dragon.getCoordinates() != null
                    && dragon.getAge() != null
                    && dragon.getWeight() != null
                    && coordValidation(dragon.getCoordinates());
        }
        return !dragon.getName().isBlank() && dragon.getCoordinates() != null
                && dragon.getAge() != null
                && dragon.getWeight() != null
                && personValidation(dragon.getKiller())
                && coordValidation(dragon.getCoordinates());
    }

    /**
     * Проверяет, что все необходимые поля экземпляра класса {@link Person} не равны null.
     *
     * @param person - проверяемый дракон
     * @return результат проверки
     */
    public static boolean personValidation(Person person) {
        if (person == null) {
            return true;
        }
        return !person.getName().isBlank() && person.getEyeColor() != null
                && person.getNationality() != null;
    }

    /**
     * Проверяет, что все необходимые поля экземпляра класса {@link Coordinates} не равны null.
     *
     * @param coords - проверяемый дракон
     * @return результат проверки
     */
    public static boolean coordValidation(Coordinates coords) {
        if (coords == null) {
            return false;
        }
        return coords.getX() != null;
    }

    public void commandValidation(Command command, String[] arguments) throws IllegalValueException, ArrayIndexOutOfBoundsException, NumberFormatException {
        if (command != null) {
            if (command.isArgs()) {
                if (arguments[0].isEmpty()) {
                    throw new ArrayIndexOutOfBoundsException("У команды " + command.getName() + " должен быть аргумент.");
                }

                if (arguments.length > 1) {
                    throw new IllegalValueException("У команды " + command.getName() + " должен быть только один аргумент.");
                }

                if (!command.getName().equals("execute_file")) {
                    try {
                        Float.parseFloat(arguments[0]);
                    } catch (NumberFormatException var4) {
                        throw new NumberFormatException("Аргумент команды " + command.getName() + " должен быть числом.");
                    }
                }
            } else if (!arguments[0].isBlank()) {
                throw new IllegalValueException("У команды " + command.getName() + " не должно быть аргументов.");
            }

        } else {
            throw new IllegalValueException("Команда не найдена.");
        }
    }
}
