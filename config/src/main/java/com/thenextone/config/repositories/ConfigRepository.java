package com.thenextone.config.repositories;

import com.thenextone.config.entities.Config;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ConfigRepository extends CrudRepository<Config, Integer> {

    @Override
    public List<Config> findAll();

    @Override
    public Optional<Config> findById(Integer id);

    public Config findFirstByCode(String code);

    public List<Config> findAllByCode(String code);

    public Config findAllByType(String type);

    @Override
    public Config save(Config config);

}
