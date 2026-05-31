package com.museum.app.core.service;

import com.museum.app.common.NotFoundException;
import com.museum.app.core.domain.MuseumObject;
import com.museum.app.core.dto.MuseumObjectDtos.MuseumObjectRequest;
import com.museum.app.core.dto.MuseumObjectDtos.MuseumObjectResponse;
import com.museum.app.core.repository.MuseumObjectRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class MuseumObjectService {

    private final MuseumObjectRepository repository;

    public MuseumObjectService(MuseumObjectRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public Page<MuseumObjectResponse> list(Pageable pageable) {
        return repository.findByDeletedFalse(pageable).map(this::toResponse);
    }

    @Transactional(readOnly = true)
    public MuseumObjectResponse getById(UUID id) {
        return toResponse(findActive(id));
    }

    @Transactional(readOnly = true)
    public List<MuseumObjectResponse> findByInventarNumber(String inventarNumber) {
        return repository.findByInventarNumberAndDeletedFalse(inventarNumber).stream()
                .map(this::toResponse)
                .toList();
    }

    public MuseumObjectResponse create(MuseumObjectRequest request) {
        MuseumObject entity = new MuseumObject();
        applyRequest(entity, request);
        if (Boolean.TRUE.equals(request.assignSystemNumber()) && entity.getSystemNumber() == null) {
            entity.setSystemNumber(repository.nextSystemNumber());
        }
        return toResponse(repository.save(entity));
    }

    public MuseumObjectResponse update(UUID id, MuseumObjectRequest request) {
        MuseumObject entity = findActive(id);
        applyRequest(entity, request);
        return toResponse(repository.save(entity));
    }

    public void delete(UUID id) {
        MuseumObject entity = findActive(id);
        entity.setDeleted(true);
        repository.save(entity);
    }

    @Transactional(readOnly = true)
    public void requireActiveObject(UUID objectId) {
        if (!repository.existsByIdAndDeletedFalse(objectId)) {
            throw new NotFoundException("Museum object not found: " + objectId);
        }
    }

    private MuseumObject findActive(UUID id) {
        return repository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException("Museum object not found: " + id));
    }

    private void applyRequest(MuseumObject entity, MuseumObjectRequest request) {
        entity.setSystemNumber(request.systemNumber());
        entity.setInventarNumber(request.inventarNumber());
        entity.setPartNumber(request.partNumber());
        entity.setParentObjectId(request.parentObjectId());
        entity.setTitle(request.title());
        entity.setObjectDateText(request.objectDateText());
        entity.setObjectDateFrom(request.objectDateFrom());
        entity.setObjectDateTo(request.objectDateTo());
        entity.setTechnique(request.technique());
        entity.setHeightCm(request.heightCm());
        entity.setWidthCm(request.widthCm());
        entity.setDepthCm(request.depthCm());
        entity.setOwnerOrganizationId(request.ownerOrganizationId());
        entity.setStorageLocationId(request.storageLocationId());
        entity.setContainerId(request.containerId());
        entity.setCanBeExhibited(request.canBeExhibited());
        entity.setCanBeTransported(request.canBeTransported());
        entity.setNotes(request.notes());
        entity.setMetadata(request.metadata());
    }

    private MuseumObjectResponse toResponse(MuseumObject entity) {
        return new MuseumObjectResponse(
                entity.getId(),
                entity.getSystemNumber(),
                entity.getInventarNumber(),
                entity.getPartNumber(),
                entity.getParentObjectId(),
                entity.getTitle(),
                entity.getObjectDateText(),
                entity.getObjectDateFrom(),
                entity.getObjectDateTo(),
                entity.getTechnique(),
                entity.getHeightCm(),
                entity.getWidthCm(),
                entity.getDepthCm(),
                entity.getOwnerOrganizationId(),
                entity.getStorageLocationId(),
                entity.getContainerId(),
                entity.getCanBeExhibited(),
                entity.getCanBeTransported(),
                entity.getNotes(),
                entity.getMetadata(),
                entity.getVersion(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
