package storage.initor.fileinitor.xml.sax;

import cargo.domain.Cargo;
import carrier.domain.Carrier;
import common.business.exception.checked.InitStorageException;
import common.solutions.utils.FileUtils;
import common.solutions.utils.xml.sax.XmlSaxUtils;
import storage.initor.fileinitor.BaseFileInitor;
import transportation.domain.Transportation;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.SAXParser;

public class XmlSaxFileDataInitor extends BaseFileInitor {

  private static final String FILE = "/ru/epam/javacore/lesson_16_concurrency/initdata/xmldata.xml";

  @Override
  public void initStorage() throws InitStorageException {
    File file = null;
    try {
      file = getFileWithInitData();
      SAXParser parser = XmlSaxUtils.getParser();
      SaxHandler saxHandler = new SaxHandler();
      parser.parse(file, saxHandler);
      Map<String, Cargo> cargoMap = saxHandler.getCargoMap();
      Map<String, Carrier> carrierMap = saxHandler.getCarrierMap();
      List<ParsedTransportation> transportations = saxHandler.getTransportations();
      setReferencesBetweenEntities(cargoMap, carrierMap, transportations);

      persistCargos(cargoMap.values());
      persistCarriers(carrierMap.values());
      List<Transportation> transportationList = getTransportationsFromParsedObject(transportations);
      persistTransportations(transportationList);
    } catch (Exception e) {
      e.printStackTrace();
      throw new InitStorageException(e.getMessage());
    } finally {
      if (file != null) {
        file.delete();
      }
    }
  }

  private File getFileWithInitData() throws IOException {
    return FileUtils
        .createFileFromResource(
            XmlSaxFileDataInitor.class, "init-data", "lesson12", FILE);
  }
}

