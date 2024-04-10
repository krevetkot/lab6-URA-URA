package labs.secondSemester.commons.commands;


import labs.secondSemester.commons.exceptions.IllegalValueException;
import labs.secondSemester.commons.network.Response;
import labs.secondSemester.commons.objects.Dragon;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Scanner;

/**
 * Абстрактный класс команды.
 *
 * @author Kseniya
 */
@Getter
@Setter
public abstract class Command implements Serializable {
    /**
     * Название команды
     */
    protected String name;
    /**
     * Описание команды
     */
    protected String description;
    /**
     * Есть ли аргументы
     */
    protected boolean args;

    private String stringArgument;
    private Dragon objectArgument;

    /**
     * Конструктор со всеми параметрами.
     *
     * @param name        - название
     * @param description - описание
     * @param args        - есть ли аргументы
     */
    public Command(String name, String description, boolean args) {
        this.name = name;
        this.description = description;
        this.args = args;
    }

    /**
     * Выполнение команды.
     *
     * @return response
     * @throws IllegalValueException - при недопустимом аргументе
     */
    public abstract Response execute(String argument, boolean fileMode, Scanner scanner) throws IllegalValueException;
}
