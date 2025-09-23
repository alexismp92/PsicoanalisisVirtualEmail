package com.psicovirtual.email.utils;

import com.psicovirtual.email.exception.EmailException;
import com.psicovirtual.email.utils.enums.EmailContentEnum;
import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.psicovirtual.email.utils.Constants.*;

@Slf4j
public class Utils {

    public static String getExtension(String filename) throws FileNotFoundException {
        var lastDotIndex = filename.lastIndexOf(DOT);

        if (lastDotIndex <= 0){
            throw new FileNotFoundException( "Invalid file extension");
        }
        return filename.substring(lastDotIndex + 1);
    }

    public static List<Path> findFilesWithPrefix(Path dir, String prefix) throws IOException {
        List<Path> result = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, prefix + STAR)) {
            for (Path entry : stream) {
                result.add(entry);
            }
        }
        return result;
    }


    public static String replacePlaceHolders(String html, HashMap<EmailContentEnum, String> values){
        String htmlReplaced = html;
        for(var entry : values.entrySet()){
            htmlReplaced = htmlReplaced.replaceAll(entry.getKey().getId(), entry.getValue());
        }

        return htmlReplaced;
    }

}
