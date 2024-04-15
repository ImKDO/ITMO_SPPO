package project.lab5.managers;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import project.lab5.console.Console;
import project.lab5.models.CollectionWrapper;
import project.lab5.models.HumanBeing;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;


public class DumpManager {
    private static String path;
    private final Console console;


    public DumpManager(String path, Console console) {
        this.path = path;
        this.console = console;
    }


    /**
     * Записать коллекцию в XML
     *
     * @param collection
     * @throws IOException
     */
    public static void writeCollection(List<HumanBeing> collection) throws IOException {
        ObjectMapper xmlMapper = new XmlMapper();

        FileOutputStream outputFile = new FileOutputStream(path);
        xmlMapper.writeValue(outputFile, collection);
    }

    /**
     * Записать данные из XML в JAVA
     * @param collection
     */

    public void readColletion(List<HumanBeing> collection) {
        // Создаем ObjectMapper для работы с XML
        try {
            CollectionWrapper collectionHumanBeing = new CollectionWrapper(collection);
            XmlMapper xmlMapper = new XmlMapper();

            File inputFile = new File(path);
            Scanner scanner = new Scanner(inputFile);
            StringBuilder xmlBuilder = new StringBuilder();

            while (scanner.hasNextLine()) {
                xmlBuilder.append(scanner.nextLine());
            }
            String xml = xmlBuilder.toString();
            // Читаем данные из XML в коллекцию объектов Person
            List<HumanBeing> personList = xmlMapper.readValue(xml, new TypeReference<List<HumanBeing>>() {
            });
            for (HumanBeing person : personList) {
                person.setCreationDate(LocalDateTime.now());
                if (person.validate()) {
                    collection.add(person);
                } else {
                    System.out.println("У некоторых объектов невалидные поля, они не будут загружены в программу");
                }
            }
        } catch (IOException | NullPointerException e) {
            console.println("Введите корректный формат данных:\n\n" +
                    "<item>\n" +
                    "        <id>serial int</id>\n" +
                    "        <name>String!=null и не пустую</name>\n" +
                    "        <coordinates>\n" +
                    "            <x>long</x>\n" +
                    "            <y>double</y>\n" +
                    "        </coordinates>\n" +
                    "        <realHero>boolean</realHero>\n" +
                    "        <hasToothpick>boolean or null</hasToothpick>\n" +
                    "        <impactSpeed> double < 118 </impactSpeed>\n" +
                    "        <soundtrackName>String!=null</soundtrackName>\n" +
                    "        <weaponType>weaponType</weaponType>\n" +
                    "        <mood>mood</mood>\n" +
                    "        <car>\n" +
                    "            <name>String!=null</name>\n" +
                    "            <cool>boolean</cool>\n" +
                    "        </car>\n" +
                    "</item>");
        }

    }
}