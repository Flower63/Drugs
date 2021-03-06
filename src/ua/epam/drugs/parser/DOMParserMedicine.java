package ua.epam.drugs.parser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import ua.epam.drugs.entity.Certificate;
import ua.epam.drugs.entity.Medicine;
import ua.epam.drugs.util.DrugGroup;
import ua.epam.drugs.util.DrugVersion;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * DOM parser implementation
 *
 * @author Dennis
 *
 * on 12/6/2015.
 */
public class DOMParserMedicine extends ParserMedicine {

    @Override
    public List<Medicine> parse(File file) {
        List<Medicine> drugs = new ArrayList<>();

        /*
         * Validation
         */
        validate(file);

        try {
            /*
             * Getting factory
             */
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            /*
             * Getting builder
             */
            DocumentBuilder builder = factory.newDocumentBuilder();

            /*
             * Parsing file
             */
            Document doc = builder.parse(file);

            /*
             * Normalisation document
             */
            doc.getDocumentElement().normalize();

            /*
             * Getting list of nodes
             */
            NodeList nodes = doc.getElementsByTagName(MEDICINE);

            /*
             * Iterating over nodes
             */
            for (int i = 0; i < nodes.getLength(); i++) {
                Node current = nodes.item(i);

                if (current.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) current;
                    drugs.add(getMedicine(element));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return drugs;
    }

    /**
     * Getting drug from element
     *
     * @param element Element to work upon
     * @return Medicine instance
     */
    private Medicine getMedicine(Element element) {
        Medicine medicine = new Medicine();

        /*
         * Fill object fields
         */
        medicine.setId(element.getAttribute(ID));
        medicine.setName(element.getElementsByTagName(NAME).item(0).getTextContent());
        medicine.setPharm(element.getElementsByTagName(PHARM).item(0).getTextContent());
        medicine.setGroup(DrugGroup.valueOf(element.getElementsByTagName(DRUG_GROUP).item(0).getTextContent()));
        medicine.setAnalogNames(getAnalogs((Element) element.getElementsByTagName(ANALOGS).item(0)));
        medicine.setVersion(DrugVersion.valueOf(element.getElementsByTagName(DRUG_VERSION).item(0).getTextContent()));
        medicine.setCertificate(getCertificate((Element) element.getElementsByTagName(CERTIFICATE).item(0)));

        return medicine;
    }

    /**
     * Getting certificate
     *
     * @param element Element to work upon
     * @return Certificate instance
     */
    private Certificate getCertificate(Element element) {
        Certificate certificate = new Certificate();

        certificate.setCertNumber(element.getElementsByTagName(NUMBER).item(0).getTextContent());
        certificate.setIssueDate(element.getElementsByTagName(ISSUE_DATE).item(0).getTextContent());
        certificate.setExpirationDate(element.getElementsByTagName(EXPIRATION_DATE).item(0).getTextContent());
        certificate.setOrganisationName(element.getElementsByTagName(ORGANISATION_NAME).item(0).getTextContent());

        return certificate;
    }

    /**
     * Getting drug analogs
     *
     * @param element Element to work upon
     * @return Array of analogs names
     */
    private String[] getAnalogs(Element element) {
        NodeList nodes = element.getElementsByTagName(ANALOG);
        String[] analogs = new String[nodes.getLength()];

        for (int i = 0; i < nodes.getLength(); i++) {
            Node current = nodes.item(i);
            analogs[i] = current.getTextContent();
        }

        return analogs;
    }
}
