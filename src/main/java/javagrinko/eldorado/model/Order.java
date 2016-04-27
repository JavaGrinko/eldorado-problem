package javagrinko.eldorado.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class Order {
    private int id;
    Positions positions;

    public Positions getPositions() {
        return positions;
    }
}
