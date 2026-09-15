package athl.logistics.athl_logistics.service.Impl;

import athl.logistics.athl_logistics.models.Project;
import athl.logistics.athl_logistics.models.enums.ProjectStatus;
import athl.logistics.athl_logistics.repositories.ProjectRepository;
import athl.logistics.athl_logistics.service.ProjectService;
import athl.logistics.athl_logistics.service.dto.ProjectDTO;
import athl.logistics.athl_logistics.service.dto.ProjectUpsertDTO;
import athl.logistics.athl_logistics.web.errors.AccountResourceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<ProjectDTO> list() {
        List<Project> projects = isAdminRequest() ? repository.findAll() : repository.findByStatus(ProjectStatus.PUBLISHED);
        return projects.stream().map(ProjectDTO::new).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProjectDTO create(ProjectUpsertDTO dto) {
        Project entity = new Project();
        applyFields(entity, dto);
        Project saved = repository.save(entity);
        log.info("Projet créé : id={}", saved.getId());
        return new ProjectDTO(saved);
    }

    @Override
    @Transactional
    public ProjectDTO update(Long id, ProjectUpsertDTO dto) {
        Project entity = findEntityOrThrow(id);
        applyFields(entity, dto);
        Project saved = repository.save(entity);
        log.info("Projet mis à jour : id={}", saved.getId());
        return new ProjectDTO(saved);
    }

    @Override
    @Transactional
    public ProjectDTO publish(Long id) {
        Project entity = findEntityOrThrow(id);
        entity.setStatus(ProjectStatus.PUBLISHED);
        return new ProjectDTO(repository.save(entity));
    }

    @Override
    @Transactional
    public ProjectDTO unpublish(Long id) {
        Project entity = findEntityOrThrow(id);
        entity.setStatus(ProjectStatus.DRAFT);
        return new ProjectDTO(repository.save(entity));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Project entity = findEntityOrThrow(id);
        repository.delete(entity);
        log.info("Projet supprimé : id={}", id);
    }

    private Project findEntityOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new AccountResourceException("Projet introuvable avec l'ID : " + id, HttpStatus.NOT_FOUND));
    }

    private void applyFields(Project entity, ProjectUpsertDTO dto) {
        entity.setTitleFr(dto.getTitleFr());
        entity.setTitleEn(dto.getTitleEn());
        entity.setCaptionFr(dto.getCaptionFr());
        entity.setCaptionEn(dto.getCaptionEn());
        entity.setImage(dto.getImage());
        entity.setFeatured(dto.isFeatured());
        entity.setWide(dto.isWide());
        entity.setStatus(dto.getStatus() != null ? dto.getStatus() : ProjectStatus.DRAFT);
    }

    private boolean isAdminRequest() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) return false;
        return auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(a -> a.equals("ROLE_SUPER_ADMIN") || a.equals("ROLE_ADMIN"));
    }
}
