package br.uff.labtempo.osiris.repository;

import br.uff.labtempo.osiris.model.domain.function.FunctionData;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Repository interface for FunctionData data in Osiris Web Application Database
 * Default CRUD methods implemented by Spring Framework.
 * @see CrudRepository
 * @see FunctionData
 * @version 1.0
 * @author andre.ghigo
 * @since 10/06/17.
 */
public interface FunctionDataRepository extends CrudRepository<FunctionData, Long> {
    List<FunctionData> findByName(String Name);
}
