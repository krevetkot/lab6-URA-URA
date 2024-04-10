package labs.secondSemester.commons.commands;

import labs.secondSemester.commons.exceptions.IllegalValueException;
import labs.secondSemester.commons.network.Response;

import java.util.Scanner;

/**
 * Команда execute_file file_name: считает и исполняет скрипт из указанного файла.
 *
 * @author Kseniya
 */
public class ExecuteFile extends Command {

    public ExecuteFile() {
        super("execute_file", "file_name: считать и исполнить скрипт из указанного файла", true);
    }


    @Override
    public Response execute(String argument, boolean fileMode, Scanner scanner) throws IllegalValueException {
        return null;
    }

}
