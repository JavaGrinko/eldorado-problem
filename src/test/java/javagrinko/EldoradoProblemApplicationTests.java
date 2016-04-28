package javagrinko;

import javagrinko.eldorado.main.EldoradoProblemApplication;
import javagrinko.eldorado.model.Statistics;
import javagrinko.eldorado.service.CustomerService;
import javagrinko.eldorado.service.ExperimentalMultiThreadCustomerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.w3c.dom.Element;

import java.io.File;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = EldoradoProblemApplication.class)
@WebAppConfiguration
public class EldoradoProblemApplicationTests {

	@Autowired
	private CustomerService customerService;

	@Test
	public void customerServiceTest() {
		customerService.parse(new File("src/test/resources/test.xml"));
		Statistics statistics = customerService.getStatistics();
		assert statistics.getBestCustomerId() == 1;
		assert statistics.getExpectedMeanValue() < 33;
		assert statistics.getMaxOrderValue() == 100;
		assert statistics.getMinOrderValue() == 1;
		assert statistics.getTotalSummary() == 230;
		assert statistics.getOrdersCount() == 7;
	}

	@Test
	public void experimentalMultiThreadCustomerServiceTest() {
		ExperimentalMultiThreadCustomerService experimentalMultiThreadCustomerService = new ExperimentalMultiThreadCustomerService();
		experimentalMultiThreadCustomerService.parse(new File("src/test/resources/test.xml"));
		List<Element> orders = experimentalMultiThreadCustomerService.getElementsByName("order");
		assert orders.size() == 7;
		assert "orders".equals(orders.get(0).getParentNode().getNodeName());
	}

}
