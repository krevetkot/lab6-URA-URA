package labs.secondSemester.commons.commands;

import labs.secondSemester.commons.exceptions.IllegalValueException;
import labs.secondSemester.commons.managers.CollectionManager;
import labs.secondSemester.commons.network.Response;

import java.util.Scanner;

/**
 * Команда clear: очищает коллекцию.
 *
 * @author Kseniya
 */
public class Clear extends Command {
    public Clear() {
        super("clear", "очистить коллекцию", false);
    }

    @Override
    public Response execute(String argument, boolean fileMode, Scanner scanner) throws IllegalValueException {
        if (CollectionManager.getCollection().isEmpty()) {
            return new Response("Коллекция и так пуста.");
        } else {
            CollectionManager.getCollection().clear();
            return new Response("Коллекция очищена.");
        }
    }
}
