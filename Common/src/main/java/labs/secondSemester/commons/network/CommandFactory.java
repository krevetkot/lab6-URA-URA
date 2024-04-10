//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package labs.secondSemester.commons.network;

import labs.secondSemester.commons.commands.Command;
import labs.secondSemester.commons.exceptions.IllegalValueException;
import labs.secondSemester.commons.managers.CommandManager;
import labs.secondSemester.commons.managers.Validator;

import java.util.Arrays;

public class CommandFactory {
    private CommandManager commandManager = new CommandManager();

    public CommandFactory() {
    }

    public Command buildCommand(String request, boolean filemode) throws IllegalValueException, ArrayIndexOutOfBoundsException, NumberFormatException {
        Validator validator = new Validator();
        request = request.trim();
        String commandName = request.split(" ")[0];
        String[] arguments = null;
        if (request.split(" ").length > 1) {
            arguments = Arrays.copyOfRange(request.split(" "), 1, request.split(" ").length);
        } else {
            arguments = new String[]{""};
        }

        Command command = this.commandManager.getCommandMap().get(commandName);

        try {
            validator.commandValidation(command, arguments);
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException | IllegalValueException e) {
            throw e;
        }

        command.setStringArgument("");
        if (request.trim().split(" ").length > 1) {
            command.setStringArgument(request.trim().split(" ")[1]);
        }

        return command;
    }
}
