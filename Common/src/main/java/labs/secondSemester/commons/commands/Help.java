package labs.secondSemester.commons.commands;

import labs.secondSemester.commons.exceptions.IllegalValueException;
import labs.secondSemester.commons.managers.CommandManager;
import labs.secondSemester.commons.network.Response;

import java.util.Collection;
import java.util.Scanner;

/**
 * Команда help: выводит справку по доступным командам.
 *
 * @author Kseniya
 */
public class Help extends Command {
    private final CommandManager commandManager;

    public Help(CommandManager commandManager) {
        super("help", "вывести справку по доступным командам", false);
        this.commandManager = commandManager;
    }

    @Override
    public Response execute(String argument, boolean fileMode, Scanner scanner) throws IllegalValueException {
        Collection<Command> commands = commandManager.getCommandMap().values();
        Response response = new Response();
        response.add("Доступны команды: ");
        for (Command command : commands) {
            response.add(command.getName() + ": " + command.getDescription());
        }
        return response;
    }
}
