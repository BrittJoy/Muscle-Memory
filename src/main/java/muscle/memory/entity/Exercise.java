package muscle.memory.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Exercise {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long exerciseId;

	private String exerciseName;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany(mappedBy = "exercise", cascade = CascadeType.PERSIST)
	private Set<Routine> routines = new HashSet<>();

}
