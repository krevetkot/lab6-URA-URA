package labs.secondSemester.commons.commands;

import labs.secondSemester.commons.exceptions.IllegalValueException;
import labs.secondSemester.commons.managers.CollectionManager;
import labs.secondSemester.commons.network.Response;
import labs.secondSemester.commons.objects.Dragon;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Команда max_by_killer: выводит любой объект из коллекции, значение поля killer которого является максимальным.
 *
 * @author Kseniya
 */
public class MaxByKiller extends Command {
    public MaxByKiller() {
        super("max_by_killer", "вывести любой объект из коллекции, значение поля killer которого является максимальным", false);
    }

    @Override
    public Response execute(String argument, boolean fileMode, Scanner scanner) throws IllegalValueException {
        if (CollectionManager.getCollection().isEmpty()) {
            return new Response("Коллекция пуста.");
        } else {
            ArrayList<Dragon> dragons = CollectionManager.getCollection().stream()
                    .filter(x -> x.getKiller() != null)
                    .sorted((d1, d2) -> Long.valueOf(d1.getKiller().getCountKilledDragons() - d2.getKiller().getCountKilledDragons())
                            .intValue()).collect(Collectors.toCollection(ArrayList::new));

            return new Response(dragons.get(dragons.size()-1).toString());
        }
    }
}
