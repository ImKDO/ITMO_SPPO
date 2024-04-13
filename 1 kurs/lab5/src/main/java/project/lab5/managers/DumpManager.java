package project.lab5.managers;


import org.xml.sax.SAXException;
import project.lab5.console.Console;
import project.lab5.console.StandardConsole;
import project.lab5.models.CollectionHumanBeing;
import project.lab5.models.HumanBeing;

import javax.xml.bind.JAXBContext;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class DumpManager {
    private static String path;
    private final Console console;
    private static String xmlFile;


    public DumpManager(String path, StandardConsole console) throws FileNotFoundException {
        this.path = path;
        this.console = console;
    }


    // Parser XML -> JAVA

    public void readColletion(List<HumanBeing> collection) throws ParserConfigurationException, IOException, SAXException, Exception {
        JAXBContext jaxbContext = JAXBContext.newInstance(CollectionHumanBeing.class);
        File file = new File(path);
        CollectionHumanBeing collectionHumanBeing = (CollectionHumanBeing) jaxbContext.createUnmarshaller().unmarshal(file);
        System.out.println(collectionHumanBeing.getCollectionHumanBeing());
    }


    // Java -> XML

    public static void writeCollection(ArrayList<HumanBeing> collection) throws IOException {
        StringBuilder xmlFile = new StringBuilder();

        xmlFile.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        xmlFile.append("<collection>\n");
        for (HumanBeing element : collection) {
            xmlFile.append("\t<HumanBeing>\n");
            xmlFile.append("\t\t<id>" + element.getId() + "</id>\n");
            xmlFile.append("\t\t<name>" + element.getName() + "</name>\n");
            xmlFile.append("\t\t<coordinates>" + element.getCoordinates().toString() + "</coordinates>\n");
//            xmlFile.append("\t\t<creationDate>" + element.getCreationDate() + "</creationDate>\n");
            xmlFile.append("\t\t<realHero>" + element.getRealHero() + "</realHero>\n");
            xmlFile.append("\t\t<hasToothpick>" + element.getHasToothpick() + "</hasToothpick>\n");
            xmlFile.append("\t\t<impactSpeed>" + element.getImpactSpeed() + "</impactSpeed>\n");
            xmlFile.append("\t\t<soundtrackName>" + element.getSoundtrackName() + "</soundtrackName>\n");
            xmlFile.append("\t\t<weaponType>" + element.getWeaponType() + "</weaponType>\n");
            xmlFile.append("\t\t<mood>" + element.getMood() + "</mood>\n");
            xmlFile.append("\t\t<car>" + element.getCar() + "</car>\n");
            xmlFile.append("\t</HumanBeing>\n");
        }

        xmlFile.append("</collection>\n");
        System.out.println(12313131);
        String str;
        System.out.println();
        str = String.valueOf(xmlFile);
        FileOutputStream fileOutputStream = new FileOutputStream(path);
        fileOutputStream.write(str.getBytes());
    }

    public void readCollection(ArrayList<HumanBeing> collection) {
        StandardConsole console = new StandardConsole();
        if (path != null && !path.isEmpty()) {
            try (var fileReader = new Scanner(new File(path))) {
                var s = new StringBuilder("");
                while (fileReader.hasNextLine()) {
                    s.append(fileReader.nextLine());
                    s.append("\n");
                }
                collection.clear();
                if (collection != null) {
                    String str;
                    str = String.valueOf(xmlFile);
                    FileOutputStream fileOutputStream = new FileOutputStream(path);
                    fileOutputStream.write(str.getBytes());
                    console.println("Коллекция успешна загружена!");
                    return;
                } else
                    console.printError("В загрузочном файле не обнаружена необходимая коллекция!");
            } catch (FileNotFoundException exception) {
                console.printError("Загрузочный файл не найден!");
            } catch (IllegalStateException exception) {
                console.printError("Непредвиденная ошибка!");
                System.exit(0);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            console.printError("Аргумент командной строки с загрузочным файлом не найден!");
        }
        collection = new ArrayList<HumanBeing>();
    }
}

