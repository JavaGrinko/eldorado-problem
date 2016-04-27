package javagrinko.eldorado.service;

import javagrinko.eldorado.model.Customers;
import javagrinko.eldorado.model.Statistics;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;

@Component
@Scope("session")
public class CustomerServiceImpl implements CustomerService {

    private Customers customers;
    private Statistics statistics;

    @Override
    public void parse(File file) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Customers.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            customers = (Customers) jaxbUnmarshaller.unmarshal(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Statistics getStatistics() {
        statistics = new Statistics();
        final double[] maxValue = {0};
        customers.getCustomers().forEach(
                a -> a.getOrders().getOrders().forEach(
                        b -> {
                            final double[] sum = {0};
                            b.getPositions().getPositions().forEach(c -> sum[0] += c.getPrice());
                            if (sum[0] > maxValue[0]){
                                maxValue[0] = sum[0];
                            }
                        }
                ));
        return maxValue[0];
    }

}
