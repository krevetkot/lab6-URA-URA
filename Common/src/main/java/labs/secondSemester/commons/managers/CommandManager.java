package labs.secondSemester.commons.managers;

import labs.secondSemester.commons.commands.*;
import lombok.Getter;

import java.io.Serializable;
import java.util.LinkedHashMap;

/**
 * Класс, управляющий командами {@link Command}.
 *
 * @author Kseniya
 */
@Getter
public class CommandManager implements Serializable {
    /**
     * Коллекция команд, где ключ - название команды, значение - ссылка на экземпляр класса
     */
    private final LinkedHashMap<String, Command> commandMap;

    /**
     * Конструктор, в котором в коллекцию менеджера добавляются все доступные команды.
     */
    public CommandManager() {
        commandMap = new LinkedHashMap<>();

        commandMap.put("help", new Help(this));
        commandMap.put("info", new Info());
        commandMap.put("add", new Add());
        commandMap.put("show", new Show());
        commandMap.put("save", new Save());
        commandMap.put("update", new Update());
        commandMap.put("execute_file", new ExecuteFile());
        commandMap.put("remove_by_id", new RemoveByID());
        commandMap.put("clear", new Clear());
        commandMap.put("exit", new Exit());
        commandMap.put("remove_first", new RemoveFirst());
        commandMap.put("print_field_descending_age", new PrintFieldDescendingAge());
        commandMap.put("insert_at", new InsertAt());
        commandMap.put("reorder", new Reorder());
        commandMap.put("max_by_killer", new MaxByKiller());
        commandMap.put("filter_less_than_killer", new FilterLessThanKiller());
    }
}
