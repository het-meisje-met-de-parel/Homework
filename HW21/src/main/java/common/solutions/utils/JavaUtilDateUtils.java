package common.solutions.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;

public final class JavaUtilDateUtils {

  private static final String PATTERN = "dd.MM.yyyy";

  private JavaUtilDateUtils(){

  }

  public static LocalDate valueOf(String dateStr, String pattern) throws ParseException {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
    return LocalDate.parse(dateStr, formatter);
  }

  public static LocalDate valueOf(String dateStr) throws ParseException {
    return valueOf(dateStr, PATTERN);
  }

}
