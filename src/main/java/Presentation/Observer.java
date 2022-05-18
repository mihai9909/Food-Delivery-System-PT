package Presentation;

import java.io.Serializable;

public interface Observer extends Serializable {
    void notifyEmployee(String info);
}
