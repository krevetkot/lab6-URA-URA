package labs.secondSemester.commons.commands;

import labs.secondSemester.commons.exceptions.IllegalValueException;
import labs.secondSemester.commons.managers.CollectionManager;
import labs.secondSemester.commons.network.Response;
import labs.secondSemester.commons.objects.Dragon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * Команда print_field_descending_age: выводит значения поля age всех элементов в порядке убывания.
 *
 * @author Kseniya
 */
public class PrintFieldDescendingAge extends Command {
    public PrintFieldDescendingAge() {
        super("print_field_descending_age", "вывести значения поля age всех элементов в порядке убывания", false);
    }

    @Override
    public Response execute(String argument, boolean fileMode, Scanner scanner) throws IllegalValueException {
        if (CollectionManager.getCollection().isEmpty()) {
            return new Response("Коллекция пуста.");
        } else {
            ArrayList<Long> ages = new ArrayList<>();
            for (Dragon element : CollectionManager.getCollection()) {
                ages.add(element.getAge());
            }
            ages.sort(Collections.reverseOrder());
            Response response = new Response("Возраста всех драконов в порядке убывания:");
            for (Long age : ages) {
                response.add(age + " ");
            }
            return response;
        }
    }
}
