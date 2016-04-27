package javagrinko;

import javagrinko.eldorado.main.EldoradoProblemApplication;
import javagrinko.eldorado.model.Statistics;
import javagrinko.eldorado.service.CustomerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.File;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = EldoradoProblemApplication.class)
@WebAppConfiguration
public class EldoradoProblemApplicationTests {

	@Autowired
	private CustomerService customerService;

	@Test
	public void contextLoads() {
		customerService.parse(new File("src/test/resources/test.xml"));
		Statistics statistics = customerService.getStatistics();
		assert statistics.getBestCustomerId() == 1;
		assert statistics.getExpectedMeanValue() < 33;
		assert statistics.getMaxOrderValue() == 100;
		assert statistics.getMinOrderValue() == 1;
		assert statistics.getTotalSummary() == 230;
		assert statistics.getOrdersCount() == 7;
	}

}
