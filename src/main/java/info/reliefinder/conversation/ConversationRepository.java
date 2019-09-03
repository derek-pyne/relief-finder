package info.reliefinder.conversation;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface ConversationRepository extends PagingAndSortingRepository<Conversation, String> {

    Optional<Conversation> findTopByUserIdOrderByCreatedDateDesc(String userId);

}
