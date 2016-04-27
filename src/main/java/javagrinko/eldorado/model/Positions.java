package javagrinko.eldorado.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class Positions {
    @XmlElement(name = "position")
    List<Position> positions;

    public List<Position> getPositions() {
        return positions;
    }
}
