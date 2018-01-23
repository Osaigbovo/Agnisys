/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agnisys.idsnextgen.global;

import java.io.File;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author Sumeet
 */
public class IDSFileUtils {

    public static File compareTimeStamp(File file1, File file2) {
        if (file1 != null && file2 != null) {
            long f1 = file1.lastModified();
            long f2 = file2.lastModified();
            if (f1 > f2) {
                return file1;
            }
            return file2;
        }
        return null;
    }

    public static void saveToIDSNG(File htmlFile, String content) {
        try {
            File idsngFile = new File(htmlFile.getParent() + File.separator + FilenameUtils.removeExtension(htmlFile.getName()) + ".idsng");
            //System.out.println("--idsng org file=" + idsngFile.getAbsolutePath());
            if (idsngFile.exists()) {
                FileUtils.writeStringToFile(idsngFile, content);
            } else {
                System.err.println("Error : IDSNG org file not exist!");
            }
        } catch (Exception e) {
            System.err.println("Error saveToIDSNG : " + e.getMessage());
        }
    }
}
