package com.vmware.education.tracker.backlog;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Story {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private long projectId;
    private LocalDate createDate;
    private String title;

    public Story() {}

    public Story(long projectId, LocalDate createDate, String title) {
        id = projectId;
        this.createDate = createDate;
        this.title = title;
    }

    public Story(long id, long projectId, LocalDate createDate, String title) {
        this.id = id;
        this.projectId = projectId;
        this.createDate = createDate;
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public long getProjectId() {
        return projectId;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Story story = (Story) o;
        return projectId == story.projectId && id.equals(story.id) && createDate.equals(story.createDate) && title.equals(story.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, projectId, createDate, title);
    }

    @Override
    public String toString() {
        return "Story{" +
                "id=" + id +
                ", projectId=" + projectId +
                ", createDate=" + createDate +
                ", title='" + title + '\'' +
                '}';
    }
}
