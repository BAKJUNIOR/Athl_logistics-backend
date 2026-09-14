package athl.logistics.athl_logistics.service.mappers;

public interface EntityMapper<D, E> {
    D fromEntity(E entity);
    E toEntity(D dto);
}
