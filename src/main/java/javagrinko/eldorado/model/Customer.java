package javagrinko.eldorado.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class Customer {
    private int id;
    private String name;
    Orders orders;

    public Orders getOrders() {
        return orders;
    }
}
