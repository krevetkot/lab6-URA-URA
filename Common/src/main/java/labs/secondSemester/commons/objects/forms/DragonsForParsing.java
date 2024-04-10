package labs.secondSemester.commons.objects.forms;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import labs.secondSemester.commons.objects.Dragon;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

/**
 * Класс для обертки объектов {@link Dragon} в массив при парсинге из файла.
 *
 * @author Kseniya
 */
@Getter
@Setter
@XmlRootElement(name = "dragons")
@XmlAccessorType(XmlAccessType.FIELD)
public class DragonsForParsing {
    /**
     * Массив драконов
     */
    @XmlElement(name = "dragon", type = Dragon.class)
    private ArrayList<Dragon> collectionOfDragons;

}
