package ua.epam.drugs;

import ua.epam.drugs.entity.Medicine;
import ua.epam.drugs.parser.DOMParser;

import java.io.File;
import java.util.List;

/**
 * Created by Dennis
 *
 * on 12/6/2015.
 */
public class Runner {
    public static void main(String[] args) {
        File file = new File("files/drugs.xml");
        DOMParser parser = new DOMParser();

        List<Medicine> drugs = parser.parse(file);

        for (Medicine medicine : drugs) {
            System.out.println(medicine);
        }
    }
}
