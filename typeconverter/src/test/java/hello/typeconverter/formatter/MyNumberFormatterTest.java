package hello.typeconverter.formatter;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.cglib.core.Local;

import java.text.ParseException;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class MyNumberFormatterTest {


    MyNumberFormatter formatter = new MyNumberFormatter();

    @Test
    void parse() {
        try {
            Number number = formatter.parse("1,000", Locale.KOREA);
            Assertions.assertThat(number).isEqualTo(1000l); // Long 타입 리턴
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Test
    void print() {
        String print = formatter.print(1000, Locale.KOREA);
        Assertions.assertThat(print).isEqualTo("1,000");
    }
}