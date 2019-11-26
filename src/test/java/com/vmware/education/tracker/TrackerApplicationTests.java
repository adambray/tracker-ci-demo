package com.vmware.education.tracker;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TrackerApplicationTests {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testCreateTimesheet() {
        Timesheet timesheetToCreate =
                new Timesheet(2L,
                        3L,
                        LocalDate.of(2019,11,28),
                        6);

        ResponseEntity<Timesheet> timesheetResponseEntity =
                restTemplate.postForEntity("/timesheets", timesheetToCreate, Timesheet.class);

        assertThat(timesheetResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(timesheetResponseEntity.getBody()).isNotNull();

        Timesheet timesheetSaved = timesheetResponseEntity.getBody();

        assertThat(timesheetSaved.getId()).isGreaterThan(0L);
        assertThat(timesheetSaved.getProjectId()).isEqualTo(timesheetToCreate.getProjectId());
        assertThat(timesheetSaved.getUserId()).isEqualTo(timesheetToCreate.getUserId());
        assertThat(timesheetSaved.getDate()).isEqualTo(timesheetToCreate.getDate());
        assertThat(timesheetSaved.getHours()).isEqualTo(timesheetToCreate.getHours());
    }

    @Test
    public void testFindTimesheet() {
        Timesheet timesheetCreated = createTimesheet(
                new Timesheet(
                        22L,
                        33L,
                        LocalDate.of(2019,11,28),
                        6
                )
        );

        ResponseEntity<Timesheet> timesheetResponseEntity =
                restTemplate.getForEntity("/timesheets/" + timesheetCreated.getId(),
                        Timesheet.class);

        assertThat(timesheetResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(timesheetResponseEntity.getBody()).isEqualTo(timesheetCreated);
    }

    @Test
    public void testFindTimesheet_notFound() {
        ResponseEntity<Timesheet> timesheetResponseEntity =
                restTemplate.getForEntity("/timesheets/0",
                        Timesheet.class);

        assertThat(timesheetResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void testUpdateTimesheet() {
        Timesheet timesheetCreated = createTimesheet(
                new Timesheet(
                        22L,
                        33L,
                        LocalDate.of(2019,11,28),
                        6
                )
        );

        Timesheet timesheetToUpdate =
                new Timesheet(
                        22L,
                        33L,
                        LocalDate.of(2019,11,28),
                        10
                );

        RequestEntity<Timesheet> requestEntity = RequestEntity
                .put(URI.create("/timesheets/" + timesheetCreated.getId()))
                .body(timesheetToUpdate);

        ResponseEntity<Void> timesheetResponseEntity =
                restTemplate.exchange(requestEntity,Void.class);

        assertThat(timesheetResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        ResponseEntity<Timesheet> timesheetResponseEntityFound =
                restTemplate.getForEntity("/timesheets/" + timesheetCreated.getId(),
                        Timesheet.class);


        assertThat(timesheetResponseEntityFound.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(timesheetResponseEntityFound.getBody()).isNotNull();

        Timesheet timesheetFound = timesheetResponseEntityFound.getBody();

        assertThat(timesheetFound.getId()).isEqualTo(timesheetCreated.getId());
        assertThat(timesheetFound.getProjectId()).isEqualTo(timesheetToUpdate.getProjectId());
        assertThat(timesheetFound.getUserId()).isEqualTo(timesheetToUpdate.getUserId());
        assertThat(timesheetFound.getDate()).isEqualTo(timesheetToUpdate.getDate());
        assertThat(timesheetFound.getHours()).isEqualTo(timesheetToUpdate.getHours());
    }

    @Test
    public void testUpdateTimesheet_notFound() {
        Timesheet timesheetToUpdate =
                new Timesheet(
                        22L,
                        33L,
                        LocalDate.of(2019,11,28),
                        10
                );

        RequestEntity<Timesheet> requestEntity = RequestEntity
                .put(URI.create("/timesheets/0"))
                .body(timesheetToUpdate);

        ResponseEntity<Void> timesheetResponseEntity =
                restTemplate.exchange(requestEntity,Void.class);

        assertThat(timesheetResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void testDeleteTimesheet() {
        Timesheet timesheetCreated = createTimesheet(
                new Timesheet(
                        22L,
                        33L,
                        LocalDate.of(2019,11,28),
                        6
                )
        );

        ResponseEntity<Void> responseEntity =
                restTemplate.exchange(RequestEntity
                    .delete(URI.create("/timesheets/" + timesheetCreated.getId()))
                    .build(),Void.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        ResponseEntity<Timesheet> timesheetResponseEntity =
                restTemplate.getForEntity("/timesheets/" + timesheetCreated.getId(),
                        Timesheet.class);

        assertThat(timesheetResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    private Timesheet createTimesheet(Timesheet timesheetToCreate) {
        return restTemplate
                .postForEntity("/timesheets", timesheetToCreate, Timesheet.class)
                .getBody();
    }
}
