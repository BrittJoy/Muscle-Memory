package muscle.memory.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import muscle.memory.entity.Exercise;

public interface ExerciseDao extends JpaRepository<Exercise, Long> {

}
