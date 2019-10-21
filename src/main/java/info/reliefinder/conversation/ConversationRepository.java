package info.reliefinder.conversation;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConversationRepository extends JpaRepository<Conversation, String> {

    Optional<Conversation> findTopByUserIdAndCompletedAtIsNullOrderByCreatedDateDesc(String userId);

}
