package javagrinko.eldorado.gui;

import com.vaadin.annotations.Theme;
import com.vaadin.server.ErrorHandler;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import javagrinko.eldorado.model.Customers;
import javagrinko.eldorado.model.Statistics;
import javagrinko.eldorado.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.DecimalFormat;
import java.text.Format;

@SpringUI
@Theme("valo")
public class MainUI extends UI implements ErrorHandler, Upload.SucceededListener {

    private Format formatter = new DecimalFormat("#.#");

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomersUploader customersUploader;

    private Upload uploader;
    private Table resultsTable;

    @Override
    protected void init(VaadinRequest request) {
        VerticalLayout components = new VerticalLayout();
        components.setSizeFull();
        initUploader();
        initResultsTable();
        components.addComponent(uploader);
        components.addComponent(resultsTable);
        components.setComponentAlignment(uploader, Alignment.MIDDLE_CENTER);
        components.setComponentAlignment(resultsTable, Alignment.MIDDLE_CENTER);
        setContent(components);


    }

    private void initResultsTable() {
        resultsTable = new Table("Таблица результатов");
        resultsTable.setVisible(false);
        resultsTable.setSelectable(true);
        resultsTable.setEditable(false);
        resultsTable.setImmediate(false);

        resultsTable.addContainerProperty("Наименование", String.class, null);
        resultsTable.addContainerProperty("Значение", String.class, null);
        resultsTable.setColumnAlignment("Значение", Table.Align.CENTER);
    }

    private void initUploader() {
        uploader = new Upload(null, customersUploader);
        uploader.setImmediate(false);
        uploader.setButtonCaption("Upload File");
        uploader.addSucceededListener(customersUploader);
        uploader.addSucceededListener(this);
        uploader.addFailedListener(customersUploader);
        uploader.setErrorHandler(this);
    }

    @Override
    public void error(com.vaadin.server.ErrorEvent event) {
        new Notification("Error",
                "System message: " + event.getThrowable().getMessage(),
                Notification.Type.TRAY_NOTIFICATION)
                .show(Page.getCurrent());
    }

    @Override
    public void uploadSucceeded(Upload.SucceededEvent event) {
        uploader.setVisible(false);
        Statistics statistics = customerService.getStatistics();
        resultsTable.addItem(new Object[]{"Сумма всех заказов" , formatter.format(statistics.getTotalSummary())}, 0);
        resultsTable.addItem(new Object[]{"Клиент с максимальной суммой заказов", "Клиент №"+statistics.getBestCustomerId()}, 1);
        resultsTable.addItem(new Object[]{"Сумма максимального заказа", formatter.format(statistics.getMaxOrderValue())}, 2);
        resultsTable.addItem(new Object[]{"Сумма минимального заказа", formatter.format(statistics.getMinOrderValue())}, 3);
        resultsTable.addItem(new Object[]{"Количество заказов", formatter.format(statistics.getOrdersCount())}, 4);
        resultsTable.addItem(new Object[]{"Средняя сумма заказа", formatter.format(statistics.getExpectedMeanValue())}, 5);
        resultsTable.setPageLength(resultsTable.size());
        resultsTable.setVisible(true);
    }
}
