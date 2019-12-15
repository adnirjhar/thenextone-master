package com.thenextone.config.services;

import com.thenextone.config.entities.Config;
import com.thenextone.config.models.ConfigDTO;
import com.thenextone.config.repositories.ConfigRepository;
import com.thenextone.core.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ConfigService {

    @Autowired
    private ConfigRepository configRepository;

    public List<Config> findAll() {
        return this.configRepository.findAll();
    }

    public Config findConfigByCode(String code) {
        return this.configRepository.findFirstByCode(code);
    }

    public List<Config> findAllConfigByCode(String code) {
        return this.configRepository.findAllByCode(code);
    }

    public Config add(ConfigDTO newConfig) {
        Config toBeSaved = null;

        Optional<Config> parent = Optional.empty();
        if (newConfig.getParent() != null && newConfig.getParent().getId() != null) {
            parent = this.configRepository.findById(newConfig.getParent().getId());
            parent.orElseThrow(() -> {
                return new BadRequestException("Config parent not found");
            });
        }

        Config saveMe = new Config(
                newConfig.getName(),
                newConfig.getCode(),
                newConfig.getType(),
                parent.orElse(null),
                new ArrayList<Config>()
        );
        List<Config> children = new ArrayList<>();
        toBeSaved = this.configRepository.save(saveMe);

        if (newConfig.getChildren() != null) {
            Config finalParent = toBeSaved;
            newConfig.getChildren().forEach(child -> {
                Optional<Config> newChild = this.findConfigByIdOrSaveNew(child);
                newChild.get().setParent(finalParent);
                this.configRepository.save(newChild.get());
                children.add(newChild.get());
            });
        }
        toBeSaved.setChild(children);
        this.configRepository.save(toBeSaved);

        return toBeSaved;
    }

    public Optional<Config> findConfigByIdOrSaveNew(ConfigDTO configDTO) {
        if (configDTO.getId() == null) {
            return Optional.ofNullable(
                this.configRepository.save(new Config(
                    configDTO.getName(),
                    configDTO.getCode(),
                    configDTO.getType())
                )
            );
        }
        else {
            return this.configRepository.findById(configDTO.getId());
        }
    }
}
