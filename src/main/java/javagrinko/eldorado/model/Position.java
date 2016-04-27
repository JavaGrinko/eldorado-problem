package javagrinko.eldorado.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class Position {
    private int id;
    private double price;
    private int count;

    public double getPrice() {
        return price;
    }

    public int getCount() {
        return count;
    }
}
