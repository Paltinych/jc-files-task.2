import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static java.util.Map.entry;

public class Main {
    public static void main(String[] args) {
        GameProgress player1 = new GameProgress(35, 20, 2, 25.36);
        GameProgress player2 = new GameProgress(58, 6, 5, 256.12);
        GameProgress player3 = new GameProgress(10, 45, 10, 23.08);
        String[] saves = new String[] {
                "D:/Games/savegames/save1.dat",
                "D:/Games/savegames/save2.dat",
                "D:/Games/savegames/save3.dat"
        };
        saveGame(saves[0], player1);
        saveGame(saves[1], player2);
        saveGame(saves[2], player3);

        zipFiles("D:/Games/savegames/save.zip", saves);


        for (String save : saves) {
            File file = new File(save);
            if (file.delete()) {
                System.out.println("Файл " + save + " удален");
            } else {
                System.out.println("Ошибка в удалении файла " + save);
            }
        }

    }

    public static void saveGame(String filePath, GameProgress gameProgress) {
        try (FileOutputStream fos = new FileOutputStream(filePath);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void zipFiles(String zipFilePath, String[] saves) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(zipFilePath))) {
            for (String save : saves) {
                try {
                    FileInputStream fis = new FileInputStream(save);
                    String[] s = save.split("/");   //  оставляем только имя архива,
                    ZipEntry entry = new ZipEntry(s[3]);  //  без полного пути с папками
                    zout.putNextEntry(entry);
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zout.write(buffer);
                    fis.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
            zout.closeEntry();
        } catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
}
