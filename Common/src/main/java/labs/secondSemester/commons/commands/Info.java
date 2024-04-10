package labs.secondSemester.commons.commands;

import labs.secondSemester.commons.exceptions.IllegalValueException;
import labs.secondSemester.commons.managers.CollectionManager;
import labs.secondSemester.commons.network.Response;

import java.util.Scanner;

/**
 * Команда info: выводит в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.).
 *
 * @author Kseniya
 */
public class Info extends Command {

    public Info() {
        super("info", "вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)", false);
    }

    @Override
    public Response execute(String argument, boolean fileMode, Scanner scanner) throws IllegalValueException {
        if (CollectionManager.getCollection().isEmpty()) {
            return new Response("Коллекция пока что пуста. Тип коллекции: " + CollectionManager.getCollection().getClass());
        } else {
            Response response = new Response();
            response.add("Тип коллекции: " + CollectionManager.getCollection().getClass());
            response.add("Количество элементов: " + CollectionManager.getCollection().size());
            response.add("Дата инициализации: " + CollectionManager.getCollection().get(0).getCreationDate());
            return response;
        }
    }
}
