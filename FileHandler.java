import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileHandler {
    private final String PATH = "./data/";
    private final String EXTENSION = ".sav";


    public boolean CreateFile(String fileName, String fileContent) {
        File myDir = new File(PATH);
        File myFile = new File(PATH + fileName + EXTENSION);
        if (myDir.mkdir()) {
            System.out.println(PATH + " Directory created");
        }
        else
            System.out.println("Directory already exists");

        if (myFile.exists()) {
            System.out.println("File already exists. Do you want to overwrite it? (y/n)");
            Scanner scan = new Scanner(System.in);
            String answer = scan.nextLine();
            if (answer.equalsIgnoreCase("y")) {
                DeleteFile(fileName);
                if (CreateFile(fileName, fileContent))
                    return true;
            }
            else
                return false;
        }
        else {
            try {
                if (myFile.createNewFile()) {
                    System.out.println(fileName + EXTENSION + " file has been created");
                    return AddToFile(fileName, fileContent);
                }
                else
                    return false;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }


    public boolean DeleteFile(String fileName) {
        File myDir = new File(PATH);
        File myFile = new File(PATH + fileName + EXTENSION);
        if (myDir.exists()) {
            if (myFile.delete()) {
                System.out.println(fileName + EXTENSION + " file has been deleted");
                return true;
            }
            else {
                return false;
            }
        }
        return false;
    }

    public boolean AddToFile (String filename, String s) {
        File myFile = new File(PATH + filename + EXTENSION);
        if (myFile.exists()) {
            FileWriter fw = null;
            try {
                fw = new FileWriter(myFile.getPath(), true);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                //fw.write("\n");
                fw.write(s);
                fw.flush();
                fw.close();
                return true;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }
}
