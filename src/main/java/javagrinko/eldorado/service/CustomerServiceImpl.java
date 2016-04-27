package javagrinko.eldorado.service;

import javagrinko.eldorado.model.Customers;
import javagrinko.eldorado.model.Order;
import javagrinko.eldorado.model.Statistics;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.DoubleSummaryStatistics;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Scope("session")
public class CustomerServiceImpl implements CustomerService {

    private Customers customers;
    private Statistics statistics;
    private Map<Order, Integer> orderCustomerMap = new HashMap<>();
    private Map<Integer, Double> totalValues = new HashMap<>();

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
        customers.getCustomers().forEach(
                a -> a.getOrders().getOrders().forEach(
                        b -> orderCustomerMap.put(b, a.getId())));

        orderCustomerMap.values().stream().distinct().forEach(it -> totalValues.put(it, 0.));

        DoubleSummaryStatistics summaryStatistics =
                orderCustomerMap.keySet().stream().collect(
                        Collectors.summarizingDouble(p -> {
                                    Double orderTotal = p.getPositions().getPositions().stream().collect(
                                            Collectors.summingDouble(v -> v.getPrice() * v.getCount()));
                                    Integer customerId = orderCustomerMap.get(p);
                                    totalValues.put(customerId, totalValues.get(customerId) + orderTotal);
                                    return orderTotal;
                                }
                        ));

        statistics.setTotalSummary(summaryStatistics.getSum());
        statistics.setOrdersCount(summaryStatistics.getCount());
        statistics.setMaxOrderValue(summaryStatistics.getMax());
        statistics.setMinOrderValue(summaryStatistics.getMin());
        statistics.setExpectedMeanValue(summaryStatistics.getAverage());
        statistics.setBestCustomerId(totalValues.entrySet().stream()
                .sorted(Map.Entry.<Integer, Double>comparingByValue().reversed())
                .limit(1)
                .findFirst().get().getKey());


        return statistics;
    }
}