package common.solutions.utils.xml.dom;

import cargo.domain.Cargo;
import cargo.domain.CargoType;
import cargo.domain.ClothersCargo;
import cargo.domain.FoodCargo;
import carrier.domain.Carrier;
import carrier.domain.CarrierType;
import common.solutions.utils.JavaUtilDateUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.text.ParseException;
import java.util.AbstractMap;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public final class XmlDomUtils {
    private XmlDomUtils() {

    }

    public static Document getDocument(File file) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = factory.newDocumentBuilder();
        return documentBuilder.parse(file);
    }

    public static Element getOnlyElement(Document document, String tagName) {
        return (Element) document.getElementsByTagName(tagName).item(0);
    }

    public static String getOnlyElementTextContentOrNull(Element elementSource, String tagName) {
        NodeList elementsByTagName = elementSource.getElementsByTagName(tagName);

        if (elementsByTagName.getLength() > 0) {
            return elementsByTagName.item(0).getTextContent();
        } else {
            return null;
        }
    }

    public static String getOnlyElementTextContent(Element elementSource, String tagName) {
        NodeList elementsByTagName = elementSource.getElementsByTagName(tagName);

        return elementsByTagName.item(0).getTextContent();
    }

    public static Element getOnlyElement(Element elementSource, String tagName) {
        return (Element) elementSource.getElementsByTagName(tagName).item(0);
    }
}
