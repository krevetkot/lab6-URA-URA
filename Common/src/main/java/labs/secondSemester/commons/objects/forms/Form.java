package labs.secondSemester.commons.objects.forms;

import labs.secondSemester.commons.exceptions.FailedBuildingException;
import labs.secondSemester.commons.exceptions.IllegalValueException;
import labs.secondSemester.commons.managers.Console;

import java.util.Scanner;

/**
 * Абстрактный класс для формирования объектов.
 *
 * @param <T> Класс формируемого объекта
 * @author Kseniya
 */
public abstract class Form<T> {

    /**
     * Собирает объект класса.
     *
     * @return новый объект класса T
     * @throws IllegalValueException   - при недопустимом значении в одном из полей
     * @throws FailedBuildingException - при ошибке сборки
     */
    public abstract T build(Scanner scanner, boolean fileMode) throws IllegalValueException, FailedBuildingException;

    /**
     * Формирует объект класса Enum.
     *
     * @param scanner   - используемый сканер
     * @param fileMode  - режим ввода: если true, значит данные принимаются из файла,
     *                  если false, данные принимаются интерактивно из консоли
     * @param values    - массив значений перечисления
     * @param enumName  - название класса Enum
     * @param canBeNull - может ли сформированный объект быть null
     * @return сформированный объект класса Enum
     * @throws IllegalValueException - пробрасывается при недопустимом значении
     */
    public static <S extends Enum<S>> Enum<S> askEnum(Scanner scanner, boolean fileMode, Enum<S>[] values, String enumName, boolean canBeNull)
            throws IllegalValueException {
        Console.print("Введите " + enumName + ". Возможные варианты: ", fileMode);
        StringBuilder enumValues = new StringBuilder();
        for (Enum<S> value : values) {
            enumValues.append(value).append(" ");
        }
        if (canBeNull) {
            enumValues.append("null(пустая строка)");
        }

        Console.print(enumValues.toString(), fileMode);

        while (true) {
            String str = scanner.nextLine().trim();
            for (Enum<S> value : values) {
                if (value.toString().equals(str)) {
                    return value;
                } else if (str.isEmpty() && canBeNull) {
                    return null;
                }
            }
            if (fileMode) {
                throw new IllegalValueException("Введено недопустимое значение.");
            }
            Console.print("Такого значения нет, попробуйте еще раз.", fileMode);
        }
    }

    /**
     * Формирует число типа Long.
     *
     * @param scanner         - используемый сканер
     * @param fileMode        - режим ввода: если true, значит данные принимаются из файла,
     *                        если false, данные принимаются интерактивно из консоли
     * @param name            - название класса Enum
     * @param greaterThanZero - должно ли сформированное число быть больше нуля
     * @return сформированное число типа Long
     * @throws IllegalValueException - пробрасывается при недопустимом значении
     */
    public static Long askLong(Scanner scanner, boolean fileMode, String name, Boolean greaterThanZero)
            throws IllegalValueException {
        Console.print("Введите " + name + ":", fileMode);
        Long res = null;
        while (true) {
            String str = scanner.nextLine().trim();
            try {
                res = Long.parseLong(str);
                if (greaterThanZero) {
                    if (res > 0) {
                        return res;
                    }
                    if (fileMode) {
                        throw new IllegalValueException("Введено недопустимое значение.");
                    }
                    Console.print("Значение должно быть больше нуля! Попробуйте еще раз.", fileMode);
                } else {
                    return res;
                }
            } catch (NumberFormatException e) {
                if (fileMode) {
                    throw new IllegalValueException("Значение должно быть числом типа Long.");
                }
                Console.print("Значение должно быть числом типа Long! Попробуйте еще раз.", fileMode);
            }
        }
    }

    /**
     * Формирует значение типа Boolean.
     *
     * @param scanner  - используемый сканер
     * @param fileMode - режим ввода: если true, значит данные принимаются из файла,
     *                 если false, данные принимаются интерактивно из консоли
     * @param name     - название класса Enum
     * @return сформированное значение типа Boolean
     * @throws IllegalValueException - пробрасывается при недопустимом значении
     */
    public static Boolean askBoolean(Scanner scanner, boolean fileMode, String name) throws IllegalValueException {
        Console.print("Введите " + name + ":", fileMode);
        String str = null;
        while (true) {
            str = scanner.nextLine().trim();
            if (str.equals("true") || str.equals("false")) {
                return Boolean.parseBoolean(str);
            }
            if (fileMode) {
                throw new IllegalValueException("Введено недопустимое значение.");
            }
            Console.print("Можно ввести только true или false! Попробуйте еще раз.", fileMode);
        }
    }

    /**
     * Формирует число типа float.
     *
     * @param scanner  - используемый сканер
     * @param fileMode - режим ввода: если true, значит данные принимаются из файла,
     *                 если false, данные принимаются интерактивно из консоли
     * @param name     - название класса Enum
     * @return сформированное число типа float
     * @throws IllegalValueException - пробрасывается при недопустимом значении
     */
    public static float askFloat(Scanner scanner, boolean fileMode, String name) throws IllegalValueException {
        Console.print("Введите " + name + ":", fileMode);
        while (true) {
            String str = scanner.nextLine().trim();
            float res;
            try {
                res = Float.parseFloat(str);
                return res;
            } catch (NumberFormatException e) {
                if (fileMode) {
                    throw new IllegalValueException("Введено недопустимое значение.");
                }
                Console.print("Значение должно быть числом типа float! Попробуйте еще раз.", fileMode);
            }
        }
    }

    /**
     * Формирует строку.
     *
     * @param scanner    - используемый сканер
     * @param fileMode   - режим ввода: если true, значит данные принимаются из файла,
     *                   если false, данные принимаются интерактивно из консоли
     * @param name       - название класса Enum
     * @param canBeEmpty - может ли строка быть пустой
     * @return сформированная строка
     * @throws IllegalValueException - пробрасывается при недопустимом значении
     */
    public static String askString(Scanner scanner, boolean fileMode, String name, boolean canBeEmpty) throws IllegalValueException {
        Console.print("Введите " + name + ":", fileMode);
        while (true) {
            String str = scanner.nextLine().trim();
            if (str.isBlank() && !canBeEmpty) {
                if (fileMode) {
                    throw new IllegalValueException("Введено недопустимое значение.");
                }
                Console.print("Строка не может быть пустой! Попробуйте еще раз.", fileMode);
            } else if (str.isBlank()) {
                return null;
            } else {
                return str;
            }
        }
    }
}
