package pl.ug.edu.planner.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.reflect.Field;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
public abstract class BaseService<T, I> {
    private JpaRepository<T, I> repository;

    public T save(T Entity) {
        return repository.save(Entity);
    }

    public Optional<T> getById(I id) {
        return repository.findById(id);
    }

    public Iterable<T> getAll() {
        return repository.findAll();
    }

    public Optional<T> putById(I id, T entity) {
        Optional<T> entityOptional = repository.findById(id);
        if (entityOptional.isPresent()) {
            T entityToUpdate = entityOptional.get();
            updateEntityFields(entityToUpdate, entity);
            return Optional.of(repository.save(entityToUpdate));
        }
        return entityOptional;
    }

    public Optional<Void> deleteById(I id) {
        Optional<T> entityOptional = getById(id);
        entityOptional.ifPresent(repository::delete);
        return entityOptional.map(e -> null);
    }

    private void updateEntityFields(T targetEntity, T sourceEntity) {
        Class<?> entityClass = targetEntity.getClass();
        Field[] fields = entityClass.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);

            try {
                Object fieldValue = field.get(sourceEntity);
                field.set(targetEntity, fieldValue);
            } catch (IllegalAccessException e) {
                log.error("Illegal Access when updating entity fields");
                e.printStackTrace();
            }
        }
    }
}
