package javagrinko.eldorado.gui;

import com.vaadin.annotations.Theme;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.ErrorHandler;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import javafx.util.Pair;
import javagrinko.eldorado.model.Statistics;
import javagrinko.eldorado.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.DecimalFormat;
import java.text.Format;
import java.util.List;

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
    private TextField filterTextField;
    private Button filterButton;
    private Table filterTable;

    @Override
    protected void init(VaadinRequest request) {
        VerticalLayout root = new VerticalLayout();
        root.setSizeFull();
        VerticalLayout components = new VerticalLayout();
        components.setWidthUndefined();

        initUploader();
        initResultsTable();
        initFilterComponents();

        components.addComponent(uploader);
        components.addComponent(resultsTable);
        components.addComponent(filterTable);
        components.addComponent(filterTextField);
        components.addComponent(filterButton);
        components.setComponentAlignment(filterButton, Alignment.MIDDLE_CENTER);
        components.setComponentAlignment(filterTextField, Alignment.MIDDLE_CENTER);

        root.addComponent(components);
        root.setComponentAlignment(components, Alignment.MIDDLE_CENTER);
        setContent(root);
    }

    private void initFilterComponents() {
        filterTable = new Table("Клиенты с суммой по заказам больше N");
        filterTable.setVisible(false);
        filterTable.setSelectable(true);
        filterTable.setEditable(false);
        filterTable.setImmediate(false);

        filterTable.addContainerProperty("ID клиента", Integer.class, null);
        filterTable.addContainerProperty("Сумма по заказам", String.class, null);

        filterTextField = new TextField("Минимальная сумма");
        filterTextField.setInputPrompt("Введите N");
        filterTextField.setVisible(false);

        filterButton = new Button("Показать клиентов");
        filterButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        filterButton.setVisible(false);
        filterButton.addClickListener(c -> {
            String value = filterTextField.getValue().trim();
            try {
                double minValue = Double.parseDouble(value);
                resultsTable.setVisible(false);
                showFilterTable(minValue);
            } catch (NumberFormatException e) {
                new Notification("Ошибка",
                        "Введенное значение с ошибкой",
                        Notification.Type.TRAY_NOTIFICATION)
                        .show(Page.getCurrent());
            }
        });
    }

    private void showFilterTable(double minValue) {
        filterTable.removeAllItems();
        List<Pair<Integer, Double>> customers = customerService.getCustomers(minValue);
        customers.forEach(it -> filterTable.addItem(
                new Object[]{it.getKey(), formatter.format(it.getValue())}, customers.indexOf(it) + 1));
        filterTable.setPageLength(8);
        filterTable.setVisible(true);
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
        uploader.setButtonCaption("Загрузить файл");
        uploader.addSucceededListener(customersUploader);
        uploader.addSucceededListener(this);
        uploader.addFailedListener(customersUploader);
        uploader.setErrorHandler(this);
    }

    @Override
    public void error(com.vaadin.server.ErrorEvent event) {
        new Notification("Ошибка",
                "Системная ошибка: " + event.getThrowable().getMessage(),
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
        filterTextField.setVisible(true);
        filterButton.setVisible(true);
    }
}
