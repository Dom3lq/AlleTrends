package components;

import java.util.List;

public interface ItemsProvider<T> {

	List<T> getItems();
}
