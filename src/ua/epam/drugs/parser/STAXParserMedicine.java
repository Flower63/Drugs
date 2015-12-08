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
        if (!valid(file)) {
            throw new IllegalArgumentException("Not valid file");
        }

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
                        if ("Medicine".equals(reader.getLocalName())) {
                            medicine = new Medicine();
                            medicine.setId(reader.getAttributeValue(0));
                        } else if ("Certificate".equals(reader.getLocalName())) {
                            certificate = new Certificate();
                        }
                        break;
                    case XMLStreamConstants.CHARACTERS:
                        content = reader.getText().trim();
                        break;
                    case XMLStreamConstants.END_ELEMENT:
                        switch (reader.getLocalName()) {
                            case "Medicine":
                                result.add(medicine);
                                medicine = new Medicine();
                                break;
                            case "Certificate":
                                medicine.setCertificate(certificate);
                                certificate = new Certificate();
                                break;
                            case "Analogs":
                                medicine.setAnalogNames(analogs.toArray(new String[0]));
                                analogs = new ArrayList<>();
                                break;
                            case "Analog":
                                analogs.add(content);
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
