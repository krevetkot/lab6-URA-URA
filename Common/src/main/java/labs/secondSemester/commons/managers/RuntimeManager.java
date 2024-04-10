package labs.secondSemester.commons.managers;

import labs.secondSemester.commons.commands.Command;
import labs.secondSemester.commons.exceptions.IllegalValueException;
import labs.secondSemester.commons.network.Response;

import java.util.Scanner;

/**
 * Класс, реализующий запуск приложения.
 *
 * @author Kseniya
 */
public class RuntimeManager {
    /**
     * Менеджер команд
     */
    private static CommandManager commandManager;


    /**
     * Обрабатывает полученную команду, чтобы вызвать ее выполнение.
     *
     * @throws IllegalValueException - ошибка недопустимых данных, команду невозможно выполнить
     */

    public Response commandProcessing(Command command, boolean fileMode, Scanner scanner) throws IllegalValueException, ArrayIndexOutOfBoundsException, NumberFormatException {
        String argument = command.getStringArgument();
        if (command.isArgs()) {
            try {
                return command.execute(argument, fileMode, scanner);
            } catch (Exception var6) {
                return new Response(var6.getMessage());
            }
        } else {
            try {
                return command.execute(null, fileMode, scanner);
            } catch (IllegalValueException var7) {
                return new Response(var7.getMessage());
            }
        }
    }






}
