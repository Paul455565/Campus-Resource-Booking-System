package com.campus.resourcebooking.repositories.inmemory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public abstract class InMemoryRepository<T, ID> {
    protected final Map<ID, T> storage = new HashMap<>();
    private final Function<T, ID> idExtractor;

    protected InMemoryRepository(Function<T, ID> idExtractor) {
        this.idExtractor = idExtractor;
    }

    public T save(T entity) {
        storage.put(idExtractor.apply(entity), entity);
        return entity;
    }

    public Optional<T> findById(ID id) {
        return Optional.ofNullable(storage.get(id));
    }

    public List<T> findAll() {
        return new ArrayList<>(storage.values());
    }

    public void deleteById(ID id) {
        storage.remove(id);
    }

    public boolean existsById(ID id) {
        return storage.containsKey(id);
    }
}
