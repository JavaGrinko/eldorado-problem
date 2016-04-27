package javagrinko.eldorado.service;

import javafx.util.Pair;
import javagrinko.eldorado.model.Statistics;

import java.io.File;
import java.util.List;

public interface CustomerService {
    void parse(File file);
    Statistics getStatistics();
    List<Pair<Integer, Double>> getCustomers(double minTotalValue);
}
