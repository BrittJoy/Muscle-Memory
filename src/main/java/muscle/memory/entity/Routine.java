package muscle.memory.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Routine {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long routineId;

	private String routineName;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "weekday_id")
	private Weekday weekday;

	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "routine_exercise", 
	joinColumns = @JoinColumn(name = "routine_id"), 
	inverseJoinColumns = @JoinColumn(name = "exercise_id"))
	private Set<Exercise> exercise = new HashSet<>();

}
