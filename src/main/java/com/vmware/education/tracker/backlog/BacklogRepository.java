package com.vmware.education.tracker.backlog;

import org.springframework.data.repository.CrudRepository;

interface BacklogRepository extends CrudRepository<Story, Long> {}
