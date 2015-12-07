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
 * Created by Dennis
 *
 * on 12/6/2015.
 */
public class DOMParser extends Parser {

    @Override
    public List<Medicine> parse(File file) {
        List<Medicine> drugs = new ArrayList<>();

        if (!valid(file)) {
            throw new IllegalArgumentException("Not valid file");
        }

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);

            doc.getDocumentElement().normalize();

            NodeList nodes = doc.getElementsByTagName("Medicine");

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

    private Medicine getMedicine(Element element) {
        Medicine medicine = new Medicine();

        medicine.setId(element.getAttribute("id"));
        medicine.setName(element.getElementsByTagName("Name").item(0).getTextContent());
        medicine.setPharm(element.getElementsByTagName("Pharm").item(0).getTextContent());
        medicine.setGroup(DrugGroup.valueOf(element.getElementsByTagName("DrugGroup").item(0).getTextContent()));
        medicine.setAnalogNames(getAnalogs(element));
        medicine.setVersion(DrugVersion.valueOf(element.getElementsByTagName("DrugVersion").item(0).getTextContent()));

        Certificate certificate = getCertificate((Element) element.getElementsByTagName("Certificate").item(0));

        medicine.setCertificate(certificate);

        return medicine;
    }

    private Certificate getCertificate(Element element) {
        Certificate certificate = new Certificate();

        certificate.setCertNumber(element.getElementsByTagName("Number").item(0).getTextContent());
        certificate.setIssueDate(element.getElementsByTagName("IssueDate").item(0).getTextContent());
        certificate.setExpirationDate(element.getElementsByTagName("ExpirationDate").item(0).getTextContent());
        certificate.setOrganisationName(element.getElementsByTagName("OrganisationName").item(0).getTextContent());

        return certificate;
    }

    private String[] getAnalogs(Element element) {
        NodeList nodes = element.getElementsByTagName("Analogs");
        String[] analogs = new String[nodes.getLength()];

        for (int i = 0; i < nodes.getLength(); i++) {
            Node current = nodes.item(i);
            analogs[i] = current.getTextContent();
        }

        return analogs;
    }
}
