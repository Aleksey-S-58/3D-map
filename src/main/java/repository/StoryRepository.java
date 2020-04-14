package repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import data.Story;

@Repository
@Transactional
public interface StoryRepository extends CrudRepository<Story, String> {

}
