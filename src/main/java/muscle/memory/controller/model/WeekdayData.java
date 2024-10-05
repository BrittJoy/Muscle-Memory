package muscle.memory.controller.model;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;
import muscle.memory.entity.Exercise;
import muscle.memory.entity.Routine;
import muscle.memory.entity.Weekday;

@Data
@NoArgsConstructor
public class WeekdayData {
	private Long weekdayId;
	private String weekdays;
	private Set<RoutineData> routines = new HashSet<>();

	public WeekdayData(Weekday weekday) {
		weekdayId = weekday.getWeekdayId();
		weekdays = weekday.getWeekdays();

		for(Routine routine : weekday.getRoutines()) {
			routines.add(new RoutineData(routine));
		}
	}

	@Data
	@NoArgsConstructor
	public static class AllWeekdayData {
		private Long weekdayId;
		private String weekdays;
		private Set<RoutineData> routines = new HashSet<>();
		private Set<ExerciseData> exercises = new HashSet<>();

		public AllWeekdayData(Weekday weekday) {
			weekdayId = weekday.getWeekdayId();
			weekdays = weekday.getWeekdays();

			for (Routine routine : weekday.getRoutines()) {
				routines.add(new RoutineData(routine));
			}
			
			for(Exercise exercise : ((Routine) routines).getExercise()) {
				exercises.add(new ExerciseData(exercise));
			}
		}
	}

	@Data
	@NoArgsConstructor
	public static class RoutineData {
		private Long routineId;
		private String routineName;
		private Set<ExerciseData> exercises = new HashSet<>();
		
		public RoutineData(Routine routine) {
			routineId = routine.getRoutineId();
			routineName = routine.getRoutineName();
		
			for(Exercise exercise : routine.getExercise()) {
				exercises.add(new ExerciseData(exercise));
				}
	
		}

	}

	@Data
	@NoArgsConstructor
	public static class ExerciseData {
		private Long exerciseId;
		private String exerciseName;

		public ExerciseData(Exercise exercise) {
			exerciseId = exercise.getExerciseId();
			exerciseName = exercise.getExerciseName();
		}
	}

}
