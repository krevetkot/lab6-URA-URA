package labs.secondSemester.commons.commands;

import labs.secondSemester.commons.exceptions.FailedBuildingException;
import labs.secondSemester.commons.exceptions.IllegalValueException;
import labs.secondSemester.commons.managers.CollectionManager;
import labs.secondSemester.commons.managers.Console;
import labs.secondSemester.commons.network.Response;
import labs.secondSemester.commons.objects.Dragon;
import labs.secondSemester.commons.objects.forms.DragonForm;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Команда insert_at index {element}: добавляет новый элемент в заданную позицию.
 *
 * @author Kseniya
 */
public class InsertAt extends Command {
    public InsertAt() {
        super("insert_at", "index {element}: добавить новый элемент в заданную позицию", true);
    }

    @Override
    public Response execute(String argument, boolean fileMode, Scanner scanner)
            throws NoSuchElementException, NumberFormatException, IllegalValueException {

        if (CollectionManager.getCollection().isEmpty()) {
            throw new NoSuchElementException("Коллекция пока что пуста");
        }

        int index = Integer.parseInt(argument);

        if (index > CollectionManager.getCollection().size()) {
            throw new NoSuchElementException("Индекс должен быть меньше или равен размеру коллекции. Размер = "
                    + CollectionManager.getCollection().size());
        }

        if (index < 0) {
            throw new NoSuchElementException("Агрумент index должен быть больше или равен нулю.");
        }

        DragonForm newDragon = new DragonForm();
        try {
            Dragon buildedDragon = newDragon.build(scanner, fileMode);
            if (!CollectionManager.getCollection().contains(buildedDragon)) {
                CollectionManager.getCollection().add(buildedDragon);
                Console.print("Спасибо, ваши данные приняты!", fileMode);
            } else {
                Console.print("Такой дракон уже есть в коллекции.", false);
            }
        } catch (FailedBuildingException e) {
            Console.print(e.getMessage(), false);
        }
        return null;
    }

}
