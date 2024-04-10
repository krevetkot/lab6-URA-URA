package labs.secondSemester.commons.objects;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import labs.secondSemester.commons.managers.DateAdapter;
import labs.secondSemester.commons.managers.IDManager;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Класс дракона.
 *
 * @author Kseniya
 */
@Getter
@Setter
@XmlRootElement(name = "dragon")
@XmlAccessorType(XmlAccessType.FIELD)
public class Dragon implements Comparable<Dragon>, Serializable {
    /**
     * Идентификатор дракона
     */
    @XmlElement(name = "id")
    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    /**
     * Имя
     */
    @XmlElement(name = "name")
    private String name; //Поле не может быть null, Строка не может быть пустой
    /**
     * Координаты местоположения
     */
    @XmlElement(name = "coordinates")
    private Coordinates coordinates; //Поле не может быть null
    /**
     * Дата создания
     */
    @XmlElement(name = "creationDate", required = true)
    @XmlJavaTypeAdapter(DateAdapter.class)
    private LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    /**
     * Возраст
     */
    @XmlElement(name = "age")
    private Long age; //Значение поля должно быть больше 0, Поле не может быть null
    /**
     * Вес
     */
    @XmlElement(name = "weight")
    private Long weight; //Значение поля должно быть больше 0, Поле не может быть null
    /**
     * Умеет ли говорить дракон
     */
    @XmlElement(name = "speaking")
    private boolean speaking;
    /**
     * Тип дракона
     */
    @XmlElement(name = "type", required = true, nillable = true)
    private DragonType type; //Поле может быть null
    /**
     * Убийца дракона
     */
    @XmlElement(name = "killer", required = true, nillable = true)
    private Person killer; //Поле может быть null

    /**
     * Конструктор дракона со всеми параметрами, исключаяя id и дату создания.
     * Используется для создания нового дракона.
     *
     * @param name        - имя
     * @param coordinates - координаты местоположения
     * @param age         - возраст
     * @param weight      - вес
     * @param speaking    - умеет ли говорить
     * @param type        - тип дракона
     * @param killer      - убийца дракона
     **/
    public Dragon(String name, Coordinates coordinates, Long age, Long weight, boolean speaking, DragonType type, Person killer) {
        this.id = IDManager.generateID();
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = LocalDate.now();
        this.age = age;
        this.weight = weight;
        this.speaking = speaking;
        this.type = type;
        this.killer = killer;
    }

    /**
     * Конструктор дракона со всеми параметрами. Используется для считывания данных из файла.
     *
     * @param id           - идентификатор
     * @param name         - имя
     * @param coordinates  - координаты местоположения
     * @param creationDate - дата создания
     * @param age          - возраст
     * @param weight       - вес
     * @param speaking     - умеет ли говорить
     * @param type         - тип дракона
     * @param killer       - убийца дракона
     **/
    public Dragon(int id, String name, Coordinates coordinates, LocalDate creationDate, Long age, Long weight, boolean speaking, DragonType type, Person killer) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.age = age;
        this.weight = weight;
        this.speaking = speaking;
        this.type = type;
        this.killer = killer;
    }

    /**
     * Конструктор дракона без параметров.
     **/
    public Dragon() {
    }

    @Override
    public String toString() {
        if (killer == null) {
            return this.getClass().getName() + '{'
                    + "id=" + this.id
                    + ", name=" + this.name
                    + ", coordinates=" + this.coordinates
                    + ", creation date=" + this.creationDate
                    + ", age=" + this.age
                    + ", weight=" + this.weight
                    + ", speaking=" + this.speaking
                    + ", type=" + this.type
                    + ", killer=null"
                    + '}';
        }
        return this.getClass().getName() + '{'
                + "id= " + this.id
                + ", name=" + this.name
                + ", coordinates=" + this.coordinates
                + ", creation date=" + this.creationDate
                + ", age=" + this.age
                + ", weight=" + this.weight
                + ", speaking=" + this.speaking
                + ", type=" + this.type
                + ", killer=" + this.killer.toString()
                + '}';
    }

    @Override
    public int compareTo(Dragon anotherDragon) {
        return (int) ((getCoordinates().getX() * getCoordinates().getX() + getCoordinates().getY() * getCoordinates().getY())
                - (anotherDragon.getCoordinates().getX() * anotherDragon.getCoordinates().getX() +
                anotherDragon.getCoordinates().getY() * anotherDragon.getCoordinates().getY()));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (this.getClass() != obj.getClass())
            return false;
        Dragon dragon = (Dragon) obj;
        if (this.killer == null && dragon.killer == null) {
            if (this.type == null && dragon.type == null) {
                return this.id == dragon.getId()
                        && this.name.equals(dragon.getName())
                        && this.coordinates.equals(dragon.getCoordinates())
                        && this.creationDate.equals(dragon.getCreationDate())
                        && Objects.equals(this.age, dragon.getAge())
                        && Objects.equals(this.weight, dragon.getWeight())
                        && this.speaking == dragon.getSpeaking();
            }
            if (this.type == null || dragon.type == null) {
                return false;
            }
            return this.id == dragon.getId()
                    && this.name.equals(dragon.getName())
                    && this.coordinates.equals(dragon.getCoordinates())
                    && this.creationDate.equals(dragon.getCreationDate())
                    && Objects.equals(this.age, dragon.getAge())
                    && Objects.equals(this.weight, dragon.getWeight())
                    && this.speaking == dragon.getSpeaking()
                    && this.type == dragon.getType();

        }
        if (this.type == null && dragon.type == null) {
            return this.id == dragon.getId()
                    && this.name.equals(dragon.getName())
                    && this.coordinates.equals(dragon.getCoordinates())
                    && this.creationDate.equals(dragon.getCreationDate())
                    && Objects.equals(this.age, dragon.getAge())
                    && Objects.equals(this.weight, dragon.getWeight())
                    && this.speaking == dragon.getSpeaking();
        }
        if (this.type == null || dragon.type == null) {
            return false;
        }

        if (this.killer == null || dragon.killer == null) {
            return false;
        }
        return this.id == dragon.getId()
                && this.name.equals(dragon.getName())
                && this.coordinates.equals(dragon.getCoordinates())
                && this.creationDate.equals(dragon.getCreationDate())
                && Objects.equals(this.age, dragon.getAge())
                && Objects.equals(this.weight, dragon.getWeight())
                && this.speaking == dragon.getSpeaking()
                && this.type == dragon.getType()
                && this.killer.equals(dragon.getKiller());
    }

    public boolean getSpeaking() {
        return this.speaking;
    }

    @Override
    public int hashCode() {
        int hash = 31;
        hash = hash * 17 + name.hashCode();
        hash = hash * 17 + id;
        hash = hash * 17 + coordinates.hashCode();
        hash = hash * 17 + creationDate.hashCode();
        hash = hash * 17 + age.hashCode();
        hash = hash * 17 + weight.hashCode();
        if (speaking) {
            hash = hash * 17 + 1;
        }
        if (type != null) {
            hash = hash * 17 + type.getName().hashCode();
        }
        if (killer != null) {
            hash = hash * 17 + killer.hashCode();
        }
        return hash;
    }
}