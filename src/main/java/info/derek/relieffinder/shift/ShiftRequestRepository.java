package info.derek.relieffinder.shift;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ShiftRequestRepository extends PagingAndSortingRepository<ShiftRequest, String> {
}
