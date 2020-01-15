package common.solutions.utils;

import static java.nio.file.StandardCopyOption.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public final class FileUtils {

  private FileUtils(){

  }

  public static File createFileFromResource(String fileNamePrefix ,String fileNameSuffix, String resourcePath) throws IOException {
    try(InputStream inputStream = FileUtils.class.getResourceAsStream(resourcePath)) {
      Path tempFile = Files.createTempFile(fileNamePrefix, fileNameSuffix);
      Files.copy(inputStream, tempFile, REPLACE_EXISTING);
      return tempFile.toFile();
    }
  }

}
