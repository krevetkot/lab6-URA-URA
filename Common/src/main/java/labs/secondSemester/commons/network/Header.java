package labs.secondSemester.commons.network;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class Header implements Serializable {
    private int count; //количество всех блоков
    private int number; //порядковый номер этого блока

    public Header(int count, int number){
        this.count = count;
        this.number = number;
    }
}
