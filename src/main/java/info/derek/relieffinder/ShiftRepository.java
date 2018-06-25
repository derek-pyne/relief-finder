package info.derek.relieffinder;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
interface ShiftRepository extends PagingAndSortingRepository<Shift, String> {
}
