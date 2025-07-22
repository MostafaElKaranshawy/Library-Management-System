package Services;

import Interfaces.Identifiable;
import Interfaces.Nameable;

public class SearchService<T> {

    public T searchById(int id, T[] items) {
        for (T item : items) {
            if (item instanceof Identifiable && ((Identifiable) item).getId() == id) {
                return item;
            }
        }
        return null;
    }

    public T[] searchByName(String name, T[] items) {
        for (T item : items) {
            if(item instanceof Nameable && ((Nameable) item).getName().equalsIgnoreCase(name)) {
                return (T[]) new Object[]{item}; // Return as an array with one element
            }
        }
        return null;
    }
}
