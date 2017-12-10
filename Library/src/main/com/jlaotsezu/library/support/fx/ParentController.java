package main.com.jlaotsezu.library.support.fx;

import java.net.URL;
import java.util.Map;

public interface ParentController extends FXController {
    Map<String, ChildrenController> getChilds();
    ChildrenController getChild(String name);
}
