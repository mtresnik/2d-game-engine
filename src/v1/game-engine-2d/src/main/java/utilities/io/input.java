package main.java.utilities.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public abstract class input {

    // TODO : Put in IOutilities.
    public static void checkFileExists(String... locations) throws IllegalArgumentException {
        for (String fileLocation : locations) {
            File file = new File(fileLocation);
            if (file.exists() == false) {
                throw new IllegalArgumentException("File does not exist.");
            }
        }
    }


    // TODO : put in IOutilites
    public static ArrayList<HashMap<String, String>> getValuesFromFile(String fileLocation, String[] keys) {
        Scanner fileScan = null;
        try {
            File file = new File(fileLocation);
            fileScan = new Scanner(file);
        } catch (FileNotFoundException fnfe) {
            System.out.println(fnfe.getMessage());
            fnfe.printStackTrace();
        }
        String currentLine = "";
        ArrayList<HashMap<String, String>> retList = new ArrayList();
        while (fileScan.hasNext() == true) {
            currentLine = fileScan.nextLine();
            boolean doContinue = false;
            for (String key : keys) {
                if (currentLine.contains(key) == false) {
                    doContinue = true;
                    break;
                }
            }
            if (doContinue == true) {
                continue;
            }
            HashMap<String, String> lineMap = new HashMap();
            for (int index = 0; index < keys.length; index++) {
                int currentKeyIndex = currentLine.indexOf(keys[index]) + keys[index].length();
                int nextKeyIndex = currentKeyIndex + currentLine.substring(currentKeyIndex).indexOf(" ");
                if (index + 1 < keys.length) {
                    nextKeyIndex = currentLine.indexOf(keys[index + 1]);
                }
                if (nextKeyIndex < currentKeyIndex) {
                    nextKeyIndex = currentLine.length();
                }
                String value = currentLine.substring(currentKeyIndex, nextKeyIndex);
                lineMap.put(keys[index], value);
            }
            retList.add(lineMap);
        }
        return retList;
    }

    private input() {

    }


}
