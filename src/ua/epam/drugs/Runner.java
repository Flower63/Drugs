package ua.epam.drugs;

import ua.epam.drugs.parser.ParserFactory;
import ua.epam.drugs.parser.ParserMedicine;
import ua.epam.drugs.util.ParserType;

import java.io.File;

/**
 * Class that contains main method
 * to demonstrate parsers work
 *
 * @author Dennis
 *
 * on 12/6/2015.
 */
public class Runner {
    public static void main(String[] args) {
        File file = new File("files/drugs.xml");

        ParserFactory factory = new ParserFactory();

        ParserMedicine parser = factory.getParser(ParserType.DOM);

        System.out.println("DOM:");

        parser.parse(file).stream().forEach(System.out::println);

        parser = factory.getParser(ParserType.SAX);

        System.out.println("SAX:");

        parser.parse(file).stream().forEach(System.out::println);

        parser = factory.getParser(ParserType.STAX);

        System.out.println("StAX:");

        parser.parse(file).stream().forEach(System.out::println);
    }
}
