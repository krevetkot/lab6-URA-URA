package labs.secondSemester.server;

import jakarta.xml.bind.JAXBException;
import labs.secondSemester.commons.exceptions.FailedBuildingException;
import labs.secondSemester.commons.managers.CollectionManager;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println("Вы не указали имя файла. Запуск невозможен.");
            System.exit(1);
        }
        String filename = args[0];


        try {
            CollectionManager.loadCollection(filename);
            Server server = new Server();
            server.start();
        }
        catch (IOException | FailedBuildingException e) {
            System.out.println(e.getMessage());
            System.out.println("Не удалось загрузить коллекцию.");
            System.exit(1);
        } catch (JAXBException e) {
            System.out.println("Файл поврежден. Не удалось загрузить коллекцию.");
            System.exit(1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }
}