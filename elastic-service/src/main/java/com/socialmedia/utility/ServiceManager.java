package com.socialmedia.utility;

import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.Optional;
@RequiredArgsConstructor
public class ServiceManager<T,ID> implements IService<T,ID>{
    private final ElasticsearchRepository<T,ID> elasticsearchRepository;
    @Override
    public T save(T t) {

        return elasticsearchRepository.save(t);
    }

    @Override
    public Iterable<T> saveAll(Iterable<T> t) {

        return elasticsearchRepository.saveAll(t);
    }

    @Override
    public T update(T t) {
        return elasticsearchRepository.save(t);
    }

    @Override
    public void delete(T t) {
        elasticsearchRepository.delete(t);
    }

    @Override
    public void deleteById(ID id) {
        elasticsearchRepository.deleteById(id);
    }

    @Override
    public Optional<T> findById(ID id) {
        return elasticsearchRepository.findById(id);
    }

    @Override
    public Iterable<T> findAll() {
        return elasticsearchRepository.findAll();
    }
}
