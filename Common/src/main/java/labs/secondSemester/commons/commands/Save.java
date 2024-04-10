package labs.secondSemester.commons.commands;

import jakarta.xml.bind.JAXBException;
import labs.secondSemester.commons.exceptions.IllegalValueException;
import labs.secondSemester.commons.managers.CollectionManager;
import labs.secondSemester.commons.network.Response;

import java.io.IOException;
import java.util.Scanner;

/**
 * Команда save: сохраняет коллекцию в файл.
 *
 * @author Kseniya
 */
public class Save extends Command {
    public Save() {
        super("save", "сохранить коллекцию в файл", false);
    }

    @Override
    public Response execute(String argument, boolean fileMode, Scanner scanner) throws IllegalValueException {
        try {
            CollectionManager.saveCollection();
        } catch (JAXBException | IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
