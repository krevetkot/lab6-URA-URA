package labs.secondSemester.commons.commands;

import labs.secondSemester.commons.exceptions.IllegalValueException;
import labs.secondSemester.commons.network.Response;

import java.util.Scanner;

/**
 * Команда exit: завершает программу без сохранения в файл.
 *
 * @author Kseniya
 */
public class Exit extends Command {
    public Exit() {
        super("exit", "завершить программу (без сохранения в файл)", false);
    }

    @Override
    public Response execute(String argument, boolean fileMode, Scanner scanner) throws IllegalValueException {
        System.out.println("До свидания! Приходите еще.");
        System.exit(0);
        return null;
    }
}

//должна завершать только клиента
