package muscle.memory.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import muscle.memory.controller.model.WeekdayData;
import muscle.memory.controller.model.WeekdayData.ExerciseData;
import muscle.memory.controller.model.WeekdayData.RoutineData;
import muscle.memory.service.MemoryService;

@RestController
@RequestMapping("/muscle-memory")
@Slf4j
public class MemoryController {
	@Autowired
	private MemoryService memoryService;

	
	@GetMapping("/weekday") // Displays weekdays with their assigned ID number, routine, and exercises of the associated routine.
	public List<WeekdayData> retrieveAllWeekdays() {
		log.info("Retrieve all weekdays called.");
		return memoryService.retrieveAllWeekdays();
	}

	
	@GetMapping("/weekday/{weekdayId}") // Pull up a specific weekday by ID, along with assigned routine and exercises.
	public WeekdayData retrieveWeekdayById(@PathVariable Long weekdayId) {
		log.info("Retrieving weekday with ID={}", weekdayId);
		return memoryService.retrieveWeekdayById(weekdayId);
	}

	
	@GetMapping("/routine") // Retrieves all routines and their exercises.
	public List<RoutineData> retrieveAllRoutines() {
		log.info("Retrieving all routines");
		return memoryService.retrieveAllRoutines();
	}
	
	
	@GetMapping("weekday/{weekdayId}/routine/{routineId}")
	public RoutineData retrieveRoutineById(@PathVariable Long weekdayId, @PathVariable Long routineId) {
		log.info("Retrieving routine with ID={}", routineId);
		return memoryService.retrieveRoutineById(weekdayId, routineId);
	}

	//**WORKING**//
	@PutMapping("weekday/{weekdayId}/routine/{routineId}") // Update a routine name.
	public RoutineData updateRoutine(@PathVariable Long weekdayId, @PathVariable Long routineId, @RequestBody RoutineData routineData) {
		routineData.setRoutineId(routineId);
		log.info("Updating routine {}", routineData);
		return memoryService.saveRoutine(weekdayId, routineData);
	}
		
	
	@PutMapping("/weekday/{weekdayId}") // Update the name of a day of the week.
	public WeekdayData updateWeekday(@PathVariable Long weekdayId, @RequestBody WeekdayData weekdayData) {
		weekdayData.setWeekdayId(weekdayId);
		log.info("Updating weekday {}", weekdayData);
		return memoryService.saveWeekday(weekdayData);
	}

	
	@PutMapping("/routine/{routineId}/exercise/{exerciseId}") // Update an exercise name.
	public ExerciseData updateExercise(@PathVariable Long routineId, @PathVariable Long exerciseId,
			@RequestBody ExerciseData exerciseData) {
		exerciseData.setExerciseId(exerciseId);
		log.info("Updating/assigning exercise {}", exerciseData);
		return memoryService.saveExercise(routineId, exerciseData);
	}

	
	@PostMapping("/weekday") // Creates a new weekday
	@ResponseStatus(code = HttpStatus.CREATED)
	public WeekdayData insertWeekday(@RequestBody WeekdayData weekdayData) {
		log.info("Creating weekday {}", weekdayData);
		return memoryService.saveWeekday(weekdayData);
	}

	
	@PostMapping("/weekday/{weekdayId}/routine") // Name and add a routine to a day of the week in the Weekday table.
	@ResponseStatus(code = HttpStatus.CREATED)
	public RoutineData addRoutineToWeekday(@PathVariable Long weekdayId, @RequestBody RoutineData routineData) {
		log.info("Adding routine {} to Weekday with ID={}", routineData, weekdayId);

		return memoryService.saveRoutine(weekdayId, routineData);
	}

	
	@PostMapping("/routine/{routineId}/exercise") // Creates and adds an exercise to a specific routine based on routineId.
	@ResponseStatus(code = HttpStatus.CREATED)
	public ExerciseData addExerciseToRoutine(@PathVariable Long routineId, @RequestBody ExerciseData exerciseData) {
		log.info("Adding exercise {} to routine with ID={}", exerciseData, routineId);

		return memoryService.saveExercise(routineId, exerciseData);
	}

	
	@DeleteMapping("/weekday/{weekdayId}/routine/{routineId}")
	public Map<String, String> deleteRoutineFromWeekday(@PathVariable Long weekdayId, @PathVariable Long routineId) {
		log.info("Deleting routine with ID=" + routineId + " from weekday with ID=" + weekdayId + ".");
		memoryService.deleteRoutineFromWeekday(weekdayId, routineId);
		
		return Map.of("message", "Routine was successfully deleted.");
	}
	
	
	@DeleteMapping("/routine/{routineId}/exercise/{exerciseId}") 
	public Map<String, String> deleteExerciseById(@PathVariable Long routineId, @PathVariable Long exerciseId) {
	  log.info("Deleting exercise with ID={}", exerciseId);
	  
	  memoryService.deleteExerciseById(routineId, exerciseId);
	  
	  return Map.of("message", "Exercise with ID=" + exerciseId + " deleted successfully."); 
	  
	  }	
	
	
}// End of Class
