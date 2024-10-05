package muscle.memory.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import muscle.memory.entity.Routine;

public interface RoutineDao extends JpaRepository<Routine, Long> {

}
