package labs.secondSemester.commons.managers;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Класс, адаптирующий строку в дату при парсинге.
 *
 * @author Kseniya
 */
public class DateAdapter extends XmlAdapter<String, LocalDate> {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public String marshal(LocalDate v) {
        return v.format(formatter);
    }

    @Override
    public LocalDate unmarshal(String v) {
        return LocalDate.parse(v, formatter);
    }

}