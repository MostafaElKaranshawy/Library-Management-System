package Services;

import Interfaces.Identifiable;
import Interfaces.Nameable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SearchService<T> implements Serializable {

    public T searchById(String id, List<T> items) {
        for (T item : items) {
            if (item instanceof Identifiable && ((Identifiable) item).getId().equals(id)) {
                return item;
            }
        }
        return null;
    }

    public T searchByName(String name, List<T> items) {
        for (T item : items) {
            if(item instanceof Nameable && ((Nameable) item).getName().equalsIgnoreCase(name)) {
                return  item;
            }
        }
        return null;
    }
}
