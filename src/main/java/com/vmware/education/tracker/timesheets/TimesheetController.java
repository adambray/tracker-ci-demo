package com.vmware.education.tracker.timesheets;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/timesheets")
class TimesheetController {
    private final TimesheetRepository repository;

    TimesheetController(TimesheetRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    ResponseEntity<Timesheet> create(@RequestBody Timesheet timesheetToCreate) {
        return created(null)
                .body(repository.save(timesheetToCreate));
    }

    @GetMapping("/{id}")
    ResponseEntity<Timesheet> findById(@PathVariable long id) {
        return repository.findById(id).map(ResponseEntity::ok)
                .orElseGet(() -> notFound().build());
    }

    @PutMapping("/{id}")
    ResponseEntity<Void> update(@PathVariable long id,
                                            @RequestBody Timesheet timesheetToUpdate) {

        if (repository.findById(id).isPresent()) {
            repository.save(new Timesheet(id,
                    timesheetToUpdate.getProjectId(),
                    timesheetToUpdate.getUserId(),
                    timesheetToUpdate.getDate(),
                    timesheetToUpdate.getHours()));
            return noContent().build();
        } else {
            return notFound().build();
        }

    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable long id) {
        repository.deleteById(id);
        return noContent().build();
    }
}
