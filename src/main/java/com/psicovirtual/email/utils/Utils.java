package com.psicovirtual.email.utils;

import com.psicovirtual.email.exception.EmailException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.psicovirtual.email.utils.Constants.*;

@Slf4j
public class Utils {

    public static String getExtension(String filename) throws EmailException {
        var lastDotIndex = filename.lastIndexOf(DOT);

        if (lastDotIndex <= 0){
            throw new EmailException("Invalid file extension");
        }
        return filename.substring(lastDotIndex + 1);
    }

    public static Set<String> extractImgIdentifiers(String input) {
        log.debug("Extracting img identifiers from input: " + input);
        Set<String> identifiers = new HashSet<>();
        Pattern pattern = Pattern.compile(IMG_REGEX);
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            identifiers.add(matcher.group(1));
        }
        log.info("Extracted img identifiers: " + identifiers);

        return identifiers;
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

}
