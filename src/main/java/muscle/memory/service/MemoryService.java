package muscle.memory.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import muscle.memory.controller.model.WeekdayData;
import muscle.memory.controller.model.WeekdayData.ExerciseData;
import muscle.memory.controller.model.WeekdayData.RoutineData;
import muscle.memory.dao.ExerciseDao;
import muscle.memory.dao.RoutineDao;
import muscle.memory.dao.WeekdayDao;
import muscle.memory.entity.Exercise;
import muscle.memory.entity.Routine;
import muscle.memory.entity.Weekday;

@Service
public class MemoryService {

	@Autowired
	private WeekdayDao weekdayDao;

	@Autowired
	private RoutineDao routineDao;

	@Autowired
	private ExerciseDao exerciseDao;

	@Transactional(readOnly = false)
	public WeekdayData saveWeekday(WeekdayData weekdayData) {
		Long weekdayId = weekdayData.getWeekdayId();
		Weekday weekday = findOrCreateWeekday(weekdayId);

		copyWeekdayFields(weekday, weekdayData);
		return new WeekdayData(weekdayDao.save(weekday));

	}

	private void copyWeekdayFields(Weekday weekday, WeekdayData weekdayData) {
		weekday.setWeekdayId(weekdayData.getWeekdayId());
		weekday.setWeekdays(weekdayData.getWeekdays());
	}

	private Weekday findOrCreateWeekday(Long weekdayId) {
		if (Objects.isNull(weekdayId)) {
			return new Weekday();
		} else {
			return findWeekdayById(weekdayId);
		}
	}

	private Weekday findWeekdayById(Long weekdayId) {
		return weekdayDao.findById(weekdayId)
				.orElseThrow(() -> new NoSuchElementException("Weekday with ID=" + weekdayId + " was not found."));
	}

	@Transactional(readOnly = false)
	public RoutineData saveRoutine(Long weekdayId, RoutineData routineData) {
		Weekday weekday = findWeekdayById(weekdayId);
		Long routineId = routineData.getRoutineId();
		Routine routine = findOrCreateRoutine(weekdayId, routineId);

		copyRoutineFields(routine, routineData);

		routine.setWeekday(weekday);
		weekday.getRoutines().add(routine);

		Routine dbRoutine = routineDao.save(routine);

		return new RoutineData(dbRoutine);
	}

	private void copyRoutineFields(Routine routine, RoutineData routineData) {
		routine.setRoutineId(routineData.getRoutineId());
		routine.setRoutineName(routineData.getRoutineName());
	}

	private void copyExerciseFields(Exercise exercise, ExerciseData exerciseData) {
		exercise.setExerciseId(exerciseData.getExerciseId());
		exercise.setExerciseName(exerciseData.getExerciseName());
	}

	private Routine findOrCreateRoutine(Long weekdayId, Long routineId) {
		if (Objects.isNull(routineId)) {
			return new Routine();
		}

		return findRoutineById(weekdayId, routineId);

	}

	private Routine findRoutineById(Long weekdayId, Long routineId) {
		Routine routine = routineDao.findById(routineId)
				.orElseThrow(() -> new NoSuchElementException("Routine with ID=" + routineId + " was not found."));

		if (routine.getWeekday().getWeekdayId() != weekdayId) {
			throw new IllegalArgumentException(
					"The routine with ID=" + routineId + " is not assigned to the weekday with ID=" + weekdayId + ".");
		}
		return routine;
	}

	private Exercise findOrCreateExercise(Long routineId, Long exerciseId) {
		if (Objects.isNull(exerciseId)) {
			return new Exercise();
		}

		return findExerciseById(routineId, exerciseId);
	}

	private Exercise findExerciseById(Long routineId, Long exerciseId) {
		Exercise exercise = exerciseDao.findById(exerciseId)
				.orElseThrow(() -> new NoSuchElementException("Exercise with ID=" + exerciseId + " was not found."));

		boolean found = false;

		for (Routine routine : exercise.getRoutines()) {
			if (routine.getRoutineId() == routineId) {
				found = true;
				break;
			}
		}

		if (!found) {
			throw new IllegalArgumentException(
					"The exercise with ID=" + exerciseId + " is not assigned to the routine with ID=" + routineId);
		}

		return exercise;
	}

	@Transactional(readOnly = true)
	public List<WeekdayData> retrieveAllWeekdays() {
		List<Weekday> weekdays = weekdayDao.findAll();

		List<WeekdayData> result = new LinkedList<>();

		for (Weekday weekday : weekdays) {
			WeekdayData wdd = new WeekdayData(weekday);

			result.add(wdd);
		}

		return result;
	}

	@Transactional(readOnly = true)
	public List<RoutineData> retrieveAllRoutines() {
		List<Routine> routines = routineDao.findAll();
		List<RoutineData> routineResult = new LinkedList<>();

		for (Routine routine : routines) {
			RoutineData rtd = new RoutineData(routine);

			routineResult.add(rtd);
		}
		return routineResult;
	}

	@Transactional(readOnly = true)
	public WeekdayData retrieveWeekdayById(Long weekdayId) {
		return new WeekdayData(findWeekdayById(weekdayId));
	}

	@Transactional(readOnly = false)
	public void deleteRoutineFromWeekday(Long weekdayId, Long routineId) {
		Weekday weekday = weekdayDao.findById(weekdayId)
				.orElseThrow(() -> new NoSuchElementException("Weekday with ID=" + weekdayId + " was not found."));

		Routine routine = routineDao.findById(routineId)
				.orElseThrow(() -> new NoSuchElementException("Routine with ID=" + routineId + " was not found"));

		weekday.getRoutines().remove(routine);
		routineDao.delete(routine);
		weekdayDao.save(weekday);

	}

	@Transactional
	public ExerciseData saveExercise(Long routineId, ExerciseData exerciseData) {
		Routine routine = routineDao.findById(routineId)
				.orElseThrow(() -> new NoSuchElementException("Routine with ID=" + routineId + " was not found."));
		Long exerciseId = exerciseData.getExerciseId();
		Exercise exercise = findOrCreateExercise(routineId, exerciseId);

		copyExerciseFields(exercise, exerciseData);

		exercise.getRoutines().add(routine);
		routine.getExercise().add(exercise);

		Exercise dbExercise = exerciseDao.save(exercise);

		return new ExerciseData(dbExercise);
	}

	@Transactional(readOnly = false)
	public void deleteExerciseById(Long routineId, Long exerciseId) {
		Routine routine = routineDao.findById(routineId)
				.orElseThrow(() -> new NoSuchElementException("Routine with ID=" + routineId + " was not found."));

		Exercise exercise = exerciseDao.findById(exerciseId)
				.orElseThrow(() -> new NoSuchElementException("Exercise with ID=" + exerciseId + " was not found"));

		routine.getExercise().remove(exercise);
		exerciseDao.delete(exercise);
		routineDao.save(routine);
	}

	@Transactional(readOnly = true)
	public ExerciseData retrieveExerciseById(Long routineId, Long exerciseId) {
		findRoutineById(routineId, exerciseId);
		Exercise exercise = findExerciseById(routineId, exerciseId);

		if (((RoutineData) exercise.getRoutines()).getRoutineId() != routineId) {
			throw new IllegalStateException("Exercise with ID=" + exerciseId + " is not tied to routine.");
		}

		return new ExerciseData(exercise);
	}

	@Transactional(readOnly = true)
	public RoutineData retrieveRoutineById(Long weekdayId, Long routineId) {
		return new RoutineData(findRoutineById(weekdayId, routineId));

	}

} // End of Class
