package info.derek.relieffinder.contact;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
interface ContactRepository extends PagingAndSortingRepository<Contact, String> {
}