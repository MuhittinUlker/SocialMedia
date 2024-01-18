package com.socialmedia.utility;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;
@RequiredArgsConstructor
public class ServiceManager<T,ID> implements IService<T,ID>{
    private final MongoRepository<T,ID> mongoRepository;
    @Override
    public T save(T t) {

        return mongoRepository.save(t);
    }

    @Override
    public Iterable<T> saveAll(Iterable<T> t) {

        return mongoRepository.saveAll(t);
    }

    @Override
    public T update(T t) {
        return mongoRepository.save(t);
    }

    @Override
    public void delete(T t) {
        mongoRepository.delete(t);
    }

    @Override
    public void deleteById(ID id) {
        mongoRepository.deleteById(id);
    }

    @Override
    public Optional<T> findById(ID id) {
        return mongoRepository.findById(id);
    }

    @Override
    public List<T> findAll() {
        return mongoRepository.findAll();
    }
}
