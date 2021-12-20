package com.backend_app.filewriter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

//@Component
public class FileWriter {
    /*
        Must decide on correct storage for the file,
        ../resources/data.txt ?
     */
    //@Value("${path}")     // Issues taking this value from application.properties
    private String path = "/data.txt";
    File directory = new File("../resources");

    /*
    Currently: new lines will be appended. This required that the file is wiped when application closes down,
        and that only 1 repo can be checked during the application's lifetime.
        Depends on implementation of other parts of the project, may need changed.

     */
    public void write(String msg){
        // Saving file in main folder, temp solution.
        BufferedWriter writer;

        // Creating resource directory if required.
        if(!directory.exists())
            directory.mkdir();

        try {
            writer = new BufferedWriter(new java.io.FileWriter(directory + path, true));
            writer.append(msg);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
