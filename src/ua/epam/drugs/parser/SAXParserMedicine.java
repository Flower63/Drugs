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

        validate(file);

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
                case MEDICINE:
                    medicine = new Medicine();
                    medicine.setId(attributes.getValue(ID));
                    break;
                case CERTIFICATE:
                    certificate = new Certificate();
                    break;
                case ANALOGS:
                    analogs = new ArrayList<>();
                    break;
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            switch (qName) {
                case MEDICINE:
                    result.add(medicine);
                    medicine = null;
                    break;
                case CERTIFICATE:
                    medicine.setCertificate(certificate);
                    certificate = null;
                    break;
                case NAME:
                    medicine.setName(content);
                    break;
                case PHARM:
                    medicine.setPharm(content);
                    break;
                case DRUG_GROUP:
                    medicine.setGroup(DrugGroup.valueOf(content));
                    break;
                case DRUG_VERSION:
                    medicine.setVersion(DrugVersion.valueOf(content));
                    break;
                case ANALOGS:
                    medicine.setAnalogNames(analogs.toArray(new String[0]));
                    analogs = null;
                    break;
                case ANALOG:
                    analogs.add(content);
                    break;
                case NUMBER:
                    certificate.setCertNumber(content);
                    break;
                case ISSUE_DATE:
                    certificate.setIssueDate(content);
                    break;
                case EXPIRATION_DATE:
                    certificate.setExpirationDate(content);
                    break;
                case ORGANISATION_NAME:
                    certificate.setOrganisationName(content);
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            content = new String(ch, start, length);
        }
    }
}
