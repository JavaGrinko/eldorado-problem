package javagrinko.eldorado.gui;

import com.vaadin.server.Page;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Upload;
import javagrinko.eldorado.service.CustomerService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

@Component
@Scope("session")
public class CustomersUploader implements Upload.Receiver, Upload.SucceededListener , Upload.FailedListener {

    @Autowired
    private CustomerService customerService;

    private Logger logger = Logger.getLogger(CustomersUploader.class);
    private File file;

    public OutputStream receiveUpload(String filename,
                                      String mimeType) {
        FileOutputStream fos;
        try {
            String folderName = VaadinService.getCurrent()
                    .getBaseDirectory().getAbsolutePath() + "/uploads/";
            file = new File(folderName + filename);
            fos = new FileOutputStream(file);
        } catch (Exception e) {
            return null;
        }
        return fos;
    }

    public void uploadFailed(Upload.FailedEvent event) {
        new Notification("Error",
                "Upload failed!",
                Notification.Type.TRAY_NOTIFICATION)
                .show(Page.getCurrent());
    }

    @Override
    public void uploadSucceeded(Upload.SucceededEvent event) {
        new Notification("Thank you!",
                "Upload succeeded!",
                Notification.Type.TRAY_NOTIFICATION)
                .show(Page.getCurrent());
        customerService.parse(file);
    }
}