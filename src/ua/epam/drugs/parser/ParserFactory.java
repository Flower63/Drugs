package ua.epam.drugs.parser;

import ua.epam.drugs.util.ParserType;

/**
 * Factory for parsers
 *
 * Created by Dennis
 *
 * on 12/7/2015.
 */
public class ParserFactory {

    /**
     * Getting parser
     * Types of parsers
     * @see ua.epam.drugs.util.ParserType
     *
     * @param type Type of required parser.
     * @return New instance of ParserMedicine
     */
    public ParserMedicine getParser(ParserType type) {
        switch (type) {
            case DOM:
                return new DOMParserMedicine();
            case SAX:
                return new SAXParserMedicine();
            case STAX:
                return new STAXParserMedicine();
            default:
                return null;
        }
    }
}
