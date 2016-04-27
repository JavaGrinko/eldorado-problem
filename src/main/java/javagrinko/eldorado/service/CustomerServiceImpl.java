package javagrinko.eldorado.service;

import javafx.util.Pair;
import javagrinko.eldorado.model.Customers;
import javagrinko.eldorado.model.Order;
import javagrinko.eldorado.model.Statistics;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Scope("session")
public class CustomerServiceImpl implements CustomerService {

    private Customers customers;
    private Statistics statistics;
    private Map<Order, Integer> orderCustomerMap;
    private Map<Integer, Double> totalValues;

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
        orderCustomerMap = new HashMap<>();
        totalValues = new HashMap<>();
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
                .findFirst().get().getKey());


        return statistics;
    }

    @Override
    public List<Pair<Integer, Double>> getCustomers(double minTotalValue) {
        List<Pair<Integer, Double>> pairs = new ArrayList<>();
        totalValues.keySet().stream()
                .filter(k -> totalValues.get(k) > minTotalValue)
                .forEach(k -> pairs.add(new Pair<>(k, totalValues.get(k))));
        return pairs;
    }
}