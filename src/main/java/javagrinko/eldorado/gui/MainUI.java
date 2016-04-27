package javagrinko.eldorado.gui;

import com.vaadin.annotations.Theme;
import com.vaadin.server.ErrorHandler;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import javagrinko.eldorado.model.Customers;
import javagrinko.eldorado.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;

@SpringUI
@Theme("valo")
public class MainUI extends UI implements ErrorHandler, Upload.SucceededListener {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomersUploader customersUploader;

    private Upload uploader;

    @Override
    protected void init(VaadinRequest request) {
        VerticalLayout components = new VerticalLayout();
        components.setSizeFull();
        initUploader();
        components.addComponent(uploader);
        components.setComponentAlignment(uploader, Alignment.MIDDLE_CENTER);
        setContent(components);


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
    }
}
