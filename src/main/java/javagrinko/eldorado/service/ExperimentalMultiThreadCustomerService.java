package javagrinko.eldorado.service;

import javafx.util.Pair;
import javagrinko.eldorado.model.Statistics;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class ExperimentalMultiThreadCustomerService implements CustomerService {

    private List<Element> elements = new CopyOnWriteArrayList<>();

    @Override
    public void parse(File file) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            parse(doc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void parse(Node node) {
        NodeList nodeList = node.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node child = nodeList.item(i);
            new Thread(() -> {
                addElement(child);
            }).start();
            parse(child);
        }
    }

    public void addElement(Node node) {
        if (node instanceof Element) {
            Element e = (Element) node;
            elements.add(e);
        }
    }

    public List<Element> getElementsByName(String name){
        return elements.stream().filter(it -> name.equals(it.getNodeName())).collect(Collectors.toList());
    }

    @Override
    public Statistics getStatistics() {
        return null;
    }

    @Override
    public List<Pair<Integer, Double>> getCustomers(double minTotalValue) {
        return null;
    }
}
