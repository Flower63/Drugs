package ua.epam.drugs.entity;

import ua.epam.drugs.util.DrugGroup;
import ua.epam.drugs.util.DrugVersion;

import java.util.Arrays;

/**
 * Medicine entity
 *
 * @author Dennis
 *
 * on 12/5/2015.
 */
public class Medicine {
    private String id;
    private String name;
    private String pharm;
    private DrugGroup group;
    private String[] analogNames;
    private DrugVersion version;
    private Certificate certificate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPharm() {
        return pharm;
    }

    public void setPharm(String pharm) {
        this.pharm = pharm;
    }

    public DrugGroup getGroup() {
        return group;
    }

    public void setGroup(DrugGroup group) {
        this.group = group;
    }

    public DrugVersion getVersion() {
        return version;
    }

    public void setVersion(DrugVersion version) {
        this.version = version;
    }

    public String[] getAnalogNames() {
        return analogNames;
    }

    public void setAnalogNames(String[] analogNames) {
        this.analogNames = analogNames;
    }

    public Certificate getCertificate() {
        return certificate;
    }

    public void setCertificate(Certificate certificate) {
        this.certificate = certificate;
    }

    @Override
    public String toString() {
        return  "id " + id + "\n" +
                "name " + name + "\n" +
                "pharm " + pharm + "\n" +
                "group " + group + "\n" +
                "analogs " + Arrays.toString(analogNames) + "\n" +
                "version " + version + "\n" + certificate + "\n";
    }
}
