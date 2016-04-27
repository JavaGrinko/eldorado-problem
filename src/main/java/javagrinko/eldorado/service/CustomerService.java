package javagrinko.eldorado.service;

import javagrinko.eldorado.model.Statistics;

import java.io.File;

public interface CustomerService {
    void parse(File file);

    Statistics getStatistics();
}
