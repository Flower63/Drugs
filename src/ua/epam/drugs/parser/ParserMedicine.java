package ua.epam.drugs.parser;

import ua.epam.drugs.entity.Medicine;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.util.List;

/**
 * Common abstract class for my xml parsers
 *
 * @author Dennis
 *
 * on 12/6/2015.
 */
public abstract class ParserMedicine {

    private final String xsdFilePath = "files/drugs.xsd";

    /**
     * Parsing input file
     *
     * @param file File to parse
     * @return List of found entities
     */
    public abstract List<Medicine> parse(File file);

    /**
     * Validate input file against representative xsd schema
     *
     * @param file File to validate
     * @return Validation result
     */
    protected boolean valid(File file) {
        try {
            SchemaFactory factory =
                    SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(xsdFilePath));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(file));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
