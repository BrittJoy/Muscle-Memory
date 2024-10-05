package muscle.memory.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import muscle.memory.entity.Weekday;

public interface WeekdayDao extends JpaRepository<Weekday, Long> {

}
