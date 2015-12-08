package ua.epam.drugs.entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Certificate for drugs
 *
 * @author Dennis
 *
 * on 12/5/2015.
 */
public class Certificate {
    private String certNumber;
    private LocalDate issueDate;
    private LocalDate expirationDate;
    private String organisationName;

    /*
     * Instance of DataTimeFormatter.
     *
     * Needed to parse and format dates to String
     */
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public String getCertNumber() {
        return certNumber;
    }

    public void setCertNumber(String certNumber) {
        this.certNumber = certNumber;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = LocalDate.parse(issueDate, formatter);
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = LocalDate.parse(expirationDate, formatter);
    }

    public String getOrganisationName() {
        return organisationName;
    }

    public void setOrganisationName(String organisationName) {
        this.organisationName = organisationName;
    }

    @Override
    public String toString() {
        return  "certNumber " + certNumber + "\n" +
                "issueDate " + issueDate + "\n" +
                "expirationDate " + expirationDate + "\n" +
                "organisationName " + organisationName + "\n";
    }
}
