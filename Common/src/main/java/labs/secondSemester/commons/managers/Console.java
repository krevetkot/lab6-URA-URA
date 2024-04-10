package labs.secondSemester.commons.managers;

/**
 * Класс для вывода в консоль с учетом режима: файлового или интерактивного.
 *
 * @author Kseniya
 */
public class Console {

    /**
     * Выводит в консоль.
     *
     * @param output   - что нужно вывести
     * @param fileMode - режим для вывода: если true - вывод не производится,
     *                 если false - вывод производится
     */
    public static void print(String output, boolean fileMode) {
        if (!fileMode) {
            System.out.println(output);
        }
    }
}
