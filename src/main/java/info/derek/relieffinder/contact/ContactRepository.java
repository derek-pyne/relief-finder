package info.derek.relieffinder.contact;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource
public interface ContactRepository extends PagingAndSortingRepository<Contact, String> {
    Optional<Contact> findByEmail(String email);
}
