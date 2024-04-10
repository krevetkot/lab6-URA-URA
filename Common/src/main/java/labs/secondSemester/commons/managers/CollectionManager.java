package labs.secondSemester.commons.managers;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import labs.secondSemester.commons.exceptions.FailedBuildingException;
import labs.secondSemester.commons.objects.Dragon;
import labs.secondSemester.commons.objects.forms.DragonsForParsing;
import lombok.Setter;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * Класс, управляющий коллекцией элементов типа {@link Dragon}.
 *
 * @author Kseniya
 */
public class CollectionManager {
    /**
     * Коллекция
     */
    private static ArrayList<Dragon> collectionOfDragons;
    /**
     * Название файла типа xml, используемого при загрузке и сохранении коллекции
     */
    @Setter
    private static String fileName;

    /**
     * Загружает коллекцию из файла. Вызывается при открытии приложения.
     *
     * @param filename - название файла типа xml, из которого происходит загрузка
     * @throws IOException             - ошибка открытия файла/доступа к файлу/отсутствие файла/невалидный путь
     * @throws JAXBException           - ошибка парсинга
     * @throws FailedBuildingException - ошибка сборки объектов
     */
    public static void loadCollection(String filename) throws IOException, JAXBException, FailedBuildingException {
        if (!(new File(filename).isFile())){
            throw new IOException("Невозможно прочесть файл.");
        }

        setFileName(filename);

        BufferedReader br = new BufferedReader(new FileReader(CollectionManager.fileName));
        String body = br.lines().collect(Collectors.joining());
        StringReader reader = new StringReader(body);
        JAXBContext context = JAXBContext.newInstance(DragonsForParsing.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        DragonsForParsing dragons = (DragonsForParsing) unmarshaller.unmarshal(reader);

        boolean flag = true;
        for (Dragon dragon : dragons.getCollectionOfDragons()) {
            if (!Validator.dragonValidation(dragon) || !IDManager.dragonIDisUnique(dragon.getId(), dragons.getCollectionOfDragons())) {
                flag = false;
                break;
            }
        }
        if (flag) {
            collectionOfDragons = dragons.getCollectionOfDragons();
            Collections.sort(collectionOfDragons);
            System.out.println("Коллекция загружена.");
        } else {
            throw new FailedBuildingException("Данные в коллекции не валидны", Dragon.class);
        }

        br.close();

    }

    /**
     * Сохраняет коллекцию в файл.
     *
     * @throws IOException   - ошибка открытия файла/доступа к файлу/отсутствие файла/невалидный путь
     * @throws JAXBException - ошибка парсинга
     */
    public static void saveCollection() throws JAXBException, IOException {
        DragonsForParsing dragons = new DragonsForParsing();
        dragons.setCollectionOfDragons(collectionOfDragons);

        JAXBContext jaxbContext = JAXBContext.newInstance(DragonsForParsing.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        File file = new File(fileName);

        marshaller.marshal(dragons, new OutputStreamWriter(Files.newOutputStream(file.toPath())));

        System.out.println("Коллекция сохранена.");

    }

    /**
     * Возвращает экземпляр коллекции. Если коллекция еще не инициализирована - инициализирует.
     *
     * @return экземпляр коллекции
     */
    public static ArrayList<Dragon> getCollection() {
        if (collectionOfDragons == null) {
            collectionOfDragons = new ArrayList<>();
        }
        return collectionOfDragons;
    }

    /**
     * Возвращает элемент коллекции по его идентификатору.
     *
     * @param id - идентификатор
     */
    public static Dragon getById(long id) {
        return collectionOfDragons.stream().filter(x -> x.getId() == id).findAny().orElse(null);
    }

}
