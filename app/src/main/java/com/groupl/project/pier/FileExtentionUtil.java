package com.groupl.project.pier;

import java.io.File;

/**
 * Created by ollie on 09/03/2018.
 */

public class FileExtentionUtil {

    public static String getExtensionOfFile(File file)
    {
        String fileExtension="";
        // Get file Name first
        String fileName=file.getName();

        // If fileName do not contain "." or starts with "." then it is not a valid file
        if(fileName.contains(".") && fileName.lastIndexOf(".")!= 0)
        {
            fileExtension=fileName.substring(fileName.lastIndexOf(".")+1);
        }

        return fileExtension;
    }
}