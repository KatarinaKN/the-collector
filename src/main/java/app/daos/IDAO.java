package app.daos;

import java.util.List;

public interface IDAO <T, I> {

    void create(T type);

    List<T> getAll();

    T getById(I id);

    void update(T type);

    void delete(I id);
}
