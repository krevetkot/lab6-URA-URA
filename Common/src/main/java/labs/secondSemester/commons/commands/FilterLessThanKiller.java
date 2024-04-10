package labs.secondSemester.commons.commands;

import labs.secondSemester.commons.exceptions.IllegalValueException;
import labs.secondSemester.commons.managers.CollectionManager;
import labs.secondSemester.commons.network.Response;
import labs.secondSemester.commons.objects.Dragon;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Команда filter_less_than_killer: killer: выводит элементы, значение поля killer которых меньше заданного.
 *
 * @author Kseniya
 */
public class FilterLessThanKiller extends Command {
    public FilterLessThanKiller() {
        super("filter_less_than_killer", "killer: вывести элементы, значение поля killer которых меньше заданного", true);
    }

    @Override
    public Response execute(String argument, boolean fileMode, Scanner scanner) throws IllegalValueException, NumberFormatException, NoSuchElementException {
        if (CollectionManager.getCollection().isEmpty()) {
            return new Response("Коллекция пуста.");
        } else {
            Long killer = Long.parseLong(argument);

            if (killer <= 0) {
                throw new NoSuchElementException("Агрумент killer должен быть больше нуля.");
            }
            boolean flag = true;
            Response response = new Response();
            for (Dragon element : CollectionManager.getCollection()) {
                if (element.getKiller() != null) {
                    if (element.getKiller().getCountKilledDragons() < killer) {
                        response.add(element.toString());
                        flag = false;
                    }
                }
            }
            if (flag) {
                return new Response("Нет подходящих элементов.");
            }
            return response;
        }
    }

}
