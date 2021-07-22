package com.vmware.education.tracker.timesheets;

import org.springframework.data.repository.CrudRepository;

interface TimesheetRepository extends CrudRepository<Timesheet, Long> {}
