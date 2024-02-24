package sem2_client_server.server;

import java.beans.PropertyChangeListener;

public interface ServerUserInterface {

    String printToConsole(String msg);

    void setServerWork(boolean serverWork);

    void setUiWork(boolean uiWork);

    void addPropertyChangeListener(PropertyChangeListener pcl);

}
