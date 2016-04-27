package javagrinko.eldorado.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class Customers {

    @XmlElement(name="customer")
    private List<Customer> customers;

    public List<Customer> getCustomers() {
        return customers;
    }
}
