import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.jupiter.api.Test;
import project.lab5.models.HumanBeing;

import java.io.File;
import java.io.IOException;

class BeamTest {
    @Test
    public void testBeam() throws IOException {
        Beam beam = new Beam("hello",1);

        XmlMapper serizXmlFile = new XmlMapper();
        File file = new File(System.getenv("LAB5_FILE"));
        HumanBeing humanBeing = serizXmlFile.readValue(file, HumanBeing.class);
        System.out.println(humanBeing);
    }
}