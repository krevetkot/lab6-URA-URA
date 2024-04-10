package labs.secondSemester.commons.objects;

import lombok.Getter;

import java.io.Serializable;

/**
 * Перечисление типов дракона.
 *
 * @author Kseniya
 */

@Getter
public enum DragonType implements Serializable {
    WATER("WATER"),
    UNDERGROUND("UNDERGROUND"),
    AIR("AIR"),
    FIRE("FIRE");

    private final String name;

    DragonType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
