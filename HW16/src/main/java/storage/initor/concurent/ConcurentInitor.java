package storage.initor.concurent;

import cargo.domain.Cargo;
import carrier.domain.Carrier;
import common.business.exception.checked.InitStorageException;
import common.solutions.utils.FileUtils;
import common.solutions.utils.xml.dom.XmlDomUtils;
import org.w3c.dom.Document;
import storage.initor.fileinitor.xml.dom.XmlDomFileDataInitor;
import transportation.domain.Transportation;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ConcurentInitor extends XmlDomFileDataInitor {

    private static final String FILE = "/ru/epam/javacore/lesson_13_sax_parser_recursion/initdata/xmldata.xml";

    private Thread cargoThread;
    private Thread carrierThread;
    private Thread transportationsThread;

    private Map<String, Cargo> cargoMap;
    private Map<String, Carrier> carrierMap;
    private List<ParsedTransportation> transportations;

    @Override
    protected File getFileWithInitData() throws IOException {
        return FileUtils
                .createFileFromResource(
                        XmlDomFileDataInitor.class, "init-data", "lesson12", FILE);
    }

    public void initThreads() {
        cargoThread = new Thread(() ->  {
            try {
                File file = getFileWithInitData();
                Document document = XmlDomUtils.getDocument(file);
                cargoMap = parseCargos(document);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        carrierThread = new Thread(() -> {
            try {
                File file = getFileWithInitData();
                Document document = XmlDomUtils.getDocument(file);
                carrierMap = parseCarriers(document);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        transportationsThread = new Thread(() -> {
            try {
                File file = getFileWithInitData();
                Document document = XmlDomUtils.getDocument(file);
                transportations = parseTransportationsData(document);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    @Override
    public void initStorage() throws InitStorageException {
        initThreads();
        cargoThread.start();
        carrierThread.start();
        transportationsThread.start();

        try {
            cargoThread.join();
            carrierThread.join();
            transportationsThread.join();

            setReferencesBetweenEntities(cargoMap, carrierMap, transportations);

            persistCargos(cargoMap.values());
            persistCarriers(carrierMap.values());
            List<Transportation> transportationList = getTransportationsFromParsedObject(transportations);
            persistTransportations(transportationList);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
