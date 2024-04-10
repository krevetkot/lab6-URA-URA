package labs.secondSemester.commons.commands;

import labs.secondSemester.commons.exceptions.IllegalValueException;
import labs.secondSemester.commons.managers.CollectionManager;
import labs.secondSemester.commons.network.Response;

import java.util.Collections;
import java.util.Scanner;

/**
 * Команда reorder: отсортировывает коллекцию в порядке, обратном нынешнему.
 *
 * @author Kseniya
 */
public class Reorder extends Command {
    public Reorder() {
        super("reorder", "отсортировать коллекцию в порядке, обратном нынешнему", false);
    }

    @Override
    public Response execute(String argument, boolean fileMode, Scanner scanner) throws IllegalValueException {
        Collections.reverse(CollectionManager.getCollection());
        return new Response("Коллекция отсортирована в обратном порядке.");
    }


}
