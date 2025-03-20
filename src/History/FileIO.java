package History;

import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class FileIO {
    public static ArrayList<String> readfile() {
        ArrayList<String> fileContent = new ArrayList<String>();

        try (BufferedReader br = new BufferedReader(new FileReader("src/History/History.txt"))) {
            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                fileContent.add(currentLine);
            }
        } catch (IOException e) {
			fileContent.add("No any record yet");
        }

        return fileContent;
    }

    public static void writeToFile(String win) {
        File file = new File("src/History/History.txt");
        String Winner;
        if (!file.exists()) {
            try {
                file.createNewFile(); 
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ArrayList <String> content = readfile();
        while (content.size() > 10) {
            content.remove(0);

        }
        Date date = new Date();
        Winner = win + " is the winner (" + date + ")";
        content.add(Winner);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (String line : content) {
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
