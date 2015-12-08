package ua.epam.drugs.parser;

import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;
import ua.epam.drugs.entity.Certificate;
import ua.epam.drugs.entity.Medicine;
import ua.epam.drugs.util.DrugGroup;
import ua.epam.drugs.util.DrugVersion;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * SAX parser implementation
 *
 * @author Dennis
 *
 * on 12/7/2015.
 */
public class SAXParserMedicine extends ParserMedicine {

    /**
     * Result list of drugs
     */
    private List<Medicine> result;

    @Override
    public List<Medicine> parse(File file) {
        result = new ArrayList<>();

        if (!valid(file)) {
            throw new IllegalArgumentException("Not valid file");
        }

        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            parser.parse(file, new DrugParser());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Private class that extends DefaultHandler.
     *
     * Needed to parse file
     */
    private class DrugParser extends DefaultHandler {

        private List<String> analogs;
        private Medicine medicine;
        private Certificate certificate;
        private String content;

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            switch (qName) {
                case "Medicine":
                    medicine = new Medicine();
                    medicine.setId(attributes.getValue("id"));
                    break;
                case "Certificate":
                    certificate = new Certificate();
                    break;
                case "Analogs":
                    analogs = new ArrayList<>();
                    break;
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            switch (qName) {
                case "Medicine":
                    result.add(medicine);
                    medicine = null;
                    break;
                case "Certificate":
                    medicine.setCertificate(certificate);
                    certificate = null;
                    break;
                case "Name":
                    medicine.setName(content);
                    break;
                case "Pharm":
                    medicine.setPharm(content);
                    break;
                case "DrugGroup":
                    medicine.setGroup(DrugGroup.valueOf(content));
                    break;
                case "DrugVersion":
                    medicine.setVersion(DrugVersion.valueOf(content));
                    break;
                case "Analogs":
                    medicine.setAnalogNames(analogs.toArray(new String[0]));
                    analogs = null;
                    break;
                case "Analog":
                    analogs.add(content);
                    break;
                case "Number":
                    certificate.setCertNumber(content);
                    break;
                case "IssueDate":
                    certificate.setIssueDate(content);
                    break;
                case "ExpirationDate":
                    certificate.setExpirationDate(content);
                    break;
                case "OrganisationName":
                    certificate.setOrganisationName(content);
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            content = new String(ch, start, length);
        }
    }
}
