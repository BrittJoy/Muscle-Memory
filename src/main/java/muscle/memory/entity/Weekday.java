package muscle.memory.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Weekday {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long weekdayId;
	
	private String weekdays; // consider updating to Day of Week
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "weekday", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Routine> routines = new HashSet<>();

}
