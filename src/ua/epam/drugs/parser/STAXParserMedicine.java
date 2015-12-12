package ua.epam.drugs.parser;

import ua.epam.drugs.entity.Certificate;
import ua.epam.drugs.entity.Medicine;
import ua.epam.drugs.util.DrugGroup;
import ua.epam.drugs.util.DrugVersion;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * StAX parser implementation
 *
 * @author Dennis
 *
 * on 12/8/2015.
 */
public class STAXParserMedicine extends ParserMedicine {

    @Override
    public List<Medicine> parse(File file) {

        /*
         * Validation
         */
        validate(file);

        /*
         * List of results
         */
        List<Medicine> result = new ArrayList<>();

        /*
         * List of analogs
         */
        List<String> analogs = new ArrayList<>();

        /*
         * Tags content
         */
        String content = null;

        /*
         * Actually medicine object
         */
        Medicine medicine = new Medicine();

        /*
         * Drug certificate
         */
        Certificate certificate = new Certificate();

        /*
         * Streams factory
         */
        XMLInputFactory factory = XMLInputFactory.newInstance();

        try {
            /*
             * Creating input stream from factory
             */
            XMLStreamReader reader = factory.createXMLStreamReader(new FileInputStream(file));

            /*
             * Iterating over input stream
             */
            while (reader.hasNext()) {
                int event = reader.next();

                /*
                 * Handling events
                 */
                switch (event) {
                    case XMLStreamConstants.START_ELEMENT:
                        if (MEDICINE.equals(reader.getLocalName())) {
                            medicine = new Medicine();
                            medicine.setId(reader.getAttributeValue(0));
                        } else if (CERTIFICATE.equals(reader.getLocalName())) {
                            certificate = new Certificate();
                        }
                        break;
                    case XMLStreamConstants.CHARACTERS:
                        content = reader.getText().trim();
                        break;
                    case XMLStreamConstants.END_ELEMENT:
                        switch (reader.getLocalName()) {
                            case MEDICINE:
                                result.add(medicine);
                                medicine = new Medicine();
                                break;
                            case CERTIFICATE:
                                medicine.setCertificate(certificate);
                                certificate = new Certificate();
                                break;
                            case ANALOGS:
                                medicine.setAnalogNames(analogs.toArray(new String[0]));
                                analogs = new ArrayList<>();
                                break;
                            case ANALOG:
                                analogs.add(content);
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
                                break;
                        }
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
