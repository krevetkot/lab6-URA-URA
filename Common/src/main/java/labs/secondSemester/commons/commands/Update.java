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
 * Команда update id {element}: обновляет значение элемента коллекции, id которого равен заданному.
 *
 * @author Kseniya
 */
public class Update extends Command {
    public Update() {
        super("update", "id {element}: обновить значение элемента коллекции, id которого равен заданному", true);
    }

    @Override
    public Response execute(String argument, boolean fileMode, Scanner scanner) throws IllegalValueException, NumberFormatException, NoSuchElementException {
        if (CollectionManager.getCollection().isEmpty()) {
            throw new NoSuchElementException("Коллекция пока что пуста");
        }

        long id = Long.parseLong(argument);
        Dragon oldDragon = CollectionManager.getById(id);

        if (oldDragon == null) {
            throw new NoSuchElementException("Нет элемента с таким ID.");
        }

        int index = CollectionManager.getCollection().indexOf(oldDragon);
        int oldID = oldDragon.getId();

        try {
            Dragon changedDragon = new DragonForm().build(scanner, fileMode);
            changedDragon.setId(oldID);
            CollectionManager.getCollection().set(index, changedDragon);
            Console.print("Элемент с ID " + id + " обновлен", fileMode);
        } catch (FailedBuildingException e) {
            Console.print(e.getMessage(), false);
        }

        return null;
    }
}
