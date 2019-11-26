package com.vmware.education.tracker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class TimesheetControllerTests {
    private TimesheetRepository repository;
    private TimesheetController controller;

    @BeforeEach
    public void setUp() {
        repository = mock(TimesheetRepository.class);
        controller = new TimesheetController(repository);
    }

    @Test
    public void testCreateTimesheet() {
        Timesheet timesheetToCreate =
                new Timesheet(2L,
                        3L,
                        LocalDate.of(2019,11,28),
                        6);

        Timesheet timesheetSaved =
                new Timesheet(1L,
                        2L,
                        3L,
                        LocalDate.of(2019,11,28),
                        6);

        doReturn(timesheetSaved)
                .when(repository)
                .save(timesheetToCreate);

        ResponseEntity<Timesheet> timesheetResponseEntity =
                controller.create(timesheetToCreate);

        verify(repository)
                .save(timesheetToCreate);

        assertThat(timesheetResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(timesheetResponseEntity.getBody()).isEqualTo(timesheetSaved);
    }

    @Test
    public void testFindTimesheet() {
        Timesheet timesheetFound =
                new Timesheet(1L,
                        2L,
                        3L,
                        LocalDate.of(2019,11,28),
                        6);

        doReturn(Optional.of(timesheetFound))
                .when(repository)
                .findById(1L);

        ResponseEntity<Timesheet> timesheetResponseEntity =
                controller.findById(1L);

        verify(repository)
                .findById(1L);

        assertThat(timesheetResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(timesheetResponseEntity.getBody()).isEqualTo(timesheetFound);
    }

    @Test
    public void testFindTimesheet_notFound() {
        doReturn(Optional.empty())
                .when(repository)
                .findById(1L);

        ResponseEntity<Timesheet> timesheetResponseEntity =
                controller.findById(1L);

        verify(repository)
                .findById(1L);

        assertThat(timesheetResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void testUpdateTimesheet() {
        Timesheet timesheetToUpdate =
                new Timesheet(2L,
                        3L,
                        LocalDate.of(2019,11,28),
                        6);

        Timesheet timesheetFound =
                new Timesheet(1L,
                        2L,
                        3L,
                        LocalDate.of(2019,11,28),
                        5);

        Timesheet timesheetSaved =
                new Timesheet(1L,
                        2L,
                        3L,
                        LocalDate.of(2019,11,28),
                        6);

        doReturn(Optional.of(timesheetFound))
                .when(repository)
                .findById(1L);

        doReturn(timesheetSaved)
                .when(repository)
                .save(new Timesheet(1L,
                        timesheetToUpdate.getProjectId(),
                        timesheetToUpdate.getUserId(),
                        timesheetToUpdate.getDate(),
                        timesheetToUpdate.getHours()));

        ResponseEntity<Void> timesheetResponseEntity =
                controller.update(1L, timesheetToUpdate);

        verify(repository)
                .findById(1L);

        verify(repository)
                .save(new Timesheet(1L,
                        timesheetToUpdate.getProjectId(),
                        timesheetToUpdate.getUserId(),
                        timesheetToUpdate.getDate(),
                        timesheetToUpdate.getHours()));

        assertThat(timesheetResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    public void testUpdateTimesheet_notFound() {
        Timesheet timesheetToSave =
                new Timesheet(
                        2L,
                        3L,
                        LocalDate.of(2019,11,28),
                        6
                );

        doReturn(Optional.empty())
                .when(repository)
                .findById(0L);

        ResponseEntity<Void> timesheetResponseEntity =
                controller.update(0L,timesheetToSave);

        verify(repository)
                .findById(0L);

        assertThat(timesheetResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }



    @Test
    public void testDeleteTimesheet() {
         ResponseEntity<Void> timesheetResponseEntity =
                controller.delete(1L);

        verify(repository)
                .deleteById(1L);

        assertThat(timesheetResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}
