package labs.secondSemester.commons.commands;

import labs.secondSemester.commons.exceptions.IllegalValueException;
import labs.secondSemester.commons.managers.CollectionManager;
import labs.secondSemester.commons.network.Response;

import java.util.Scanner;

/**
 * Команда remove_first: удаляет удалить первый элемент из коллекции.
 *
 * @author Kseniya
 */
public class RemoveFirst extends Command {
    public RemoveFirst() {
        super("remove_first", "удалить первый элемент из коллекции", false);
    }

    @Override
    public Response execute(String argument, boolean fileMode, Scanner scanner) throws IllegalValueException {
        if (CollectionManager.getCollection().isEmpty()) {
            return new Response("Коллекция пуста.");
        } else {
            CollectionManager.getCollection().remove(0);
            return new Response("Первый элемент в коллекции удален.");
        }
    }
}
