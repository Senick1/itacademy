package by.autodiagnostic.parser.impl;

import by.autodiagnostic.parser.ParserException;
import by.autodiagnostic.transport.Category;
import by.autodiagnostic.transport.Transport;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JSONParserTest {

    @Test
    void testParse_successfull() throws ParserException {

        final String initialValue = """
                [
                  {
                    "type": "мотоцикл",
                    "model": "Ninja ZX-14"
                  },
                  {
                    "type": "микроавтобус",
                    "model": "Sprinter264"
                  },
                  {
                    "type": "автомобиль",
                    "model": "Mazda CX7"
                  },
                ]""";

        final var parser = new JSONParser();
        final List<Transport> expected = parser.parse(initialValue);

        final Transport transport1 = new Transport(Category.MOTORBIKE, "Ninja ZX-14");
        final Transport transport2 = new Transport(Category.MINIBUS, "Sprinter264");
        final Transport transport3 = new Transport(Category.AUTOMOBILE, "Mazda CX7");

        final List<Transport> actual = new ArrayList<>();
        actual.add(transport1);
        actual.add(transport2);
        actual.add(transport3);

        assertNotNull(expected, "List is null");
        assertEquals(expected, actual);
    }

    @Test
    void testParse_throwsParserException() {
        final String initialValue = """
                [
                  {
                    "type": "мотоцикл",
                    "model": "Ninja ZX-14"
                  },
                  {
                    "part": "микроавтобус",
                    "model": "Sprinter264"
                  },
                  {
                    "type": "комбайн",
                    "model": "Mazda CX7"
                  },
                ]""";

        final var parser = new JSONParser();
        final var exception = assertThrows(ParserException.class, () -> parser.parse(initialValue));

        assertEquals("Can't parse file", exception.getMessage());
    }
}