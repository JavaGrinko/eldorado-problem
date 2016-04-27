package javagrinko.eldorado.model;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

public class Orders {
    @XmlElement(name = "order")
    List<Order> orders;

    public List<Order> getOrders() {
        return orders;
    }
}
