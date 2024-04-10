package labs.secondSemester.commons.managers;


import lombok.Getter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayDeque;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Менеджер скриптов.
 *
 * @author Kseniya
 */
public class ScriptManager {
    /**
     * Очередь выполняемых файлов
     */
    @Getter
    private static final ArrayDeque<String> pathQueue = new ArrayDeque<>();
    /**
     * Очередь считанных буферизированных потоков из выполняемых файлов
     */
    @Getter
    private static final ArrayDeque<BufferedReader> bufferedReaders = new ArrayDeque<>();

    /**
     * Добавляет файл и буфер в очереди.
     *
     * @param fileName - имя файла
     * @throws FileNotFoundException - ошибка отсутствия файла/доступа к файлу
     */
    public static void addFile(String fileName) throws FileNotFoundException {
        pathQueue.add(new File(fileName).getAbsolutePath());
        bufferedReaders.add(new BufferedReader(new FileReader(fileName)));
    }

    /**
     * Выявляет рекурсию в файле.
     *
     * @param path - путь файла
     */
    public static boolean isRecursive(String path) {
        return pathQueue.contains(new File(path).getAbsolutePath());
    }

    /**
     * Считывает следующую строку.
     *
     * @param scanner - используемый сканер
     */
    public static String nextLine(Scanner scanner) {
        try {
            return scanner.nextLine();
        } catch (NoSuchElementException e) {
            return "";
        }
    }


}
