package org.communication.retry.repositories;

import org.communication.retry.entities.Message;
import org.springframework.data.repository.CrudRepository;

public interface CommunicationMessageRepository extends CrudRepository<Message, Long> {
}
