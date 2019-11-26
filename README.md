# README

## Tracker Spring Boot Project

Welcome to the *Tracker* Spring Boot project.

It was built with an accelerator that:

-   Uses the [Spring Initializr](https://start.spring.io) to
    boot strap a Spring Boot project with the following dependencies:
    - Spring WebMVC
    - Spring Data JPA
    - H2 embedded database
    - Actuator
    - Spring Boot testing

-   A continuous integration pipeline using Github Actions.
    By pushing this code to the default main branch on a Github remote
    will automatically run a Continuous Integration build and test.

## Version 1: Timesheets Functionality

The *Tracker* application provides a REST API for basic timesheet
tracking.

The original story (#TRACKER-1) included the following specifications:

### REST API

The API has basic CRUD operations that manipulate state of
*Timesheet* entities with the following fields:

-   *id* is the resource identifier of the *Timesheet* entity.
-   *projectId* is the resource identifier of the related project.
-   *userId* is the resource identifier of the related user.
-   *date* is the timestamp when the *Timesheet* entity was created
    or updated.
-   *hours* are the number of hours logged,
    rounded to the nearest hour.

The associated CRUD REST API will be implemented as follows:

-   *URI path*:
    `/backlog`

-   *Create*
    -   The request is executed via a `POST` method.
    -   The request includes a body of type `application/json`.
    -   The body includes all fields except for the resource id.
    -   Successful creation results in returned HTTP status CREATED
        (201).

-   *Read*
    -   The request is executed via a `GET` method.
    -   The request includes a single path variable of a *Timesheet*
        resource id.
    -   Successful read (found) results in returned HTTP status OK
        (200).
    -   If resource does not exist,
        return HTTP status NOT FOUND (404).

-   *Update*
    -   The request is executed via a `PUT` method.
    -   Take a single path variable of a *Timesheet* resource id.
    -   The body includes all fields except for the resource id.
    -   If the *Timesheet* associated with the resource id is persisted,
        update it and return the HTTP status NO CONTENT (204).
    -   If resource does not exist,
        return HTTP status NOT FOUND (404).

-   *Delete*
    -   The request is executed via a `DELETE` method.
    -   The request includes a single path variable of a *Timesheet*
        resource id.
    -   Successful deletion results in returned HTTP status NO CONTENT
        (204).

No special handling is required for client or server exceptions,
the default 400 or 500 series exceptions sufficient.

### Persistence

Persistence of the Timesheet entities will be in a relational
database.

## Version 2: Backlog Functionality

The *Tracker* application provides a REST API for basic backlog
tracking.

The original story (#TRACKER-2) included the following specifications:

### REST API

The API has basic CRUD operations that manipulate state of
*Story* entities with the following fields:

-   *id* is the resource identifier of the *Timesheet* entity.
-   *projectId* is the resource identifier of the related project.
-   *createDate* is the timestamp when the *Story* entity was created
    or updated.
-   *title* is the title of the story.

The associated CRUD REST API will be implemented as follows:

-   *URI path*:
    `/backlog`

-   *Create*
    -   The request is executed via a `POST` method.
    -   The request includes a body of type `application/json`.
    -   The body includes all fields except for the resource id.
    -   Successful creation results in returned HTTP status CREATED
        (201).

-   *Read*
    -   The request is executed via a `GET` method.
    -   The request includes a single path variable of a *Story*
        resource id.
    -   Successful read (found) results in returned HTTP status OK
        (200).
    -   If resource does not exist,
        return HTTP status NOT FOUND (404).

-   *Update*
    -   The request is executed via a `PUT` method.
    -   Take a single path variable of a *Story* resource id.
    -   The body includes all fields except for the resource id.
    -   If the *Story* associated with the resource id is persisted,
        update it and return the HTTP status NO CONTENT (204).
    -   If resource does not exist,
        return HTTP status NOT FOUND (404).

-   *Delete*
    -   The request is executed via a `DELETE` method.
    -   The request includes a single path variable of a *Story*
        resource id.
    -   Successful deletion results in returned HTTP status NO CONTENT
        (204).

No special handling is required for client or server exceptions,
the default 400 or 500 series exceptions sufficient.

### Persistence

Persistence of the backlog *Story* entities will be in a relational
database.

## Local development flow to implement V2 Backlog functionality

The following sections outline the necessary steps to implement the
backlog feature using *Red/Green/Refactor* method supporting the
*Test Driven Design* practices.

### Refactor

Upcoming v2 functionality will include backlog functionality.

The Tracker application domain is not well known at this point.
It makes sense to keep new functionality in this codebase and
the deployed application,
but *Backlog* functionality is separate functionality from
*Timesheets*,
and could possibly split to a different application later on.

The engineering code design decision it so keep a single
Spring Boot Application,
but use package separation to keep *Backlog* separate from
*Timesheets*.
The goal is to reduce coupling and chance of integration
conflicts.

There are two refactorings done to achieve the goal:

-   Refactor-move the *Timesheets* related test and production
    classes to new `timesheets` packages in the `main` and `test`
    directories.

-   Given the number the related *Timesheets* classes are small and
    fit in the same package,
    use package or private level visibility for classes,
    interfaces and methods.

The refactorings are guided by the *Single Responsibility*
and *Open-Closed* principles:

-   The *Tracker* application will be used to run two different
    domain concerns,
    but it is necesary to isolate the *Timesheets* functionality
    separate from the *Backlog* functionality that will be
    introduced after the refactoring.

-   Making the *Timesheet* code package-private will hide
    implementation from other packages,
    making less likely to introduce circular dependencies or
    development integration conflicts.

### Implement the Backlog REST API

Iterate on the following CRUD operation specifications using TDD and
private branch (named `wip-backlog-api`) to keep track of each operation:

1. Add create story CRUD operation
1. Add read story CRUD operation.
1. Modify read story CRUD operation story resource does not exist.
1. Add update story CRUD operation.
1. Modify update story CRUD operation story resource does not exist.
1. Add delete story CRUD operation.

You will author a unit test for each CRUD operation,
as well as a vertical integration test
(using `SpringBootTest` and `TestRestTemplate`).

If you make mistakes or need to backtrack any work,
feel free to revise your local history.
You can do so at your convenience without worrying about impact to the
shared repository.

If you cannot complete your work in a day,
or you need to hand off to another developer,
you can push the `wip-backlog-api` branch to the remote and collaborate
on it independently from the mainline.
*You should not let this wip branch last for more than a couple of days,*
*or you risk integration pain.*

### Curate your commit

After you have completed implementation of all the Backlog CRUD
operations and verified that all unit and integration tests pass,
squash your local commits into a single commit with a concise commit
message with the story ID in it.
That will make easy for maintainers to track the outcome of your work
and trace back to the story.

Run `git rebase` as follows:

1.  Make sure your workspace is clean:

    `git status`

1.  Make sure that state of your workspace is at last commit in the
    `wip-backlog-api` local working branch:

    `git log --oneline --all`

1.  Start an interactive rebase from the original `v1` timesheets commit:

    `git rebase -i v1`

    You will but put into your workstation default interactive editor.

1.  At the first line,
    replace the `pick` command with `reword`.
    This tells the git rebase operation that you will rename the
    resulting single commit at the end of the operation.

1.  For the remaining lines,
    replace the `pick` command with `fixup`.
    The tells the git rebase operation to *squash* the remaining commits
    into the resulting single commit,
    but without retaining the individual commit history.

1.  Exit the editor.
    If the git rebase runs into any merge conflicts,
    it will stop and prompt you to fix them.
    After you fix,
    make sure to stage the change via `git add <changes>`,
    and run `git rebase --continue`.

    If you have doubts during the rebase operation and want to start
    over,
    run `git rebase --abort`,
    and git will revert to the starting point before you initated the
    rebase operation.

1.  If you do not have any merge conflicts,
    or after you complete resolving all conflicts through all the
    squashed commits,
    the git rebase operation will prompt for the commit message.

    Make your message concise and intuitive,
    with the originating story number:

    `[TRACKER-2] implement backlog REST api`

1.  Exit the editor and verify your work:

    `git log --oneline --all`

    You should see your work compressed to a single commit in the
    `wip-backlog-api` branch.

### Integrate your work

You will merge your work to the trunk (the `main` branch in git).
But before you do that,
you need to pull any change from the trunk to your local development
environment:

1.  Checkout to the main branch:

    `git checkout main`

1.  Pull the latest changes from the remote git repo and rebase them
    to your local repository:

    `git pull origin main -r`

    Given that you did your work on a local private branch,
    you should have no merge conflicts.

1.  Merge your work from your private branch via a rebase:

    `git rebase wip-backlog-api`

    If you run into merge conflicts,
    resolve them,
    stage the changes,
    and complete the rebase with `git rebase --continue`.

1.  Run a full build locally, including the unit and integration tests:

    `./gradlew clean build`

    If you have any failures,
    debug,
    resolve.

    If the failures are too complex to fix,
    repeat the entire process by creating a new private branch,
    and treat like a bugfix.

1.  After getting a successful local build and test,
    push your changes to remote trunk:

    `git push origin main`

1.  Watch the pipeline,
    it should succeed.
    If it does not,
    debug and resolve.
    If you cannot debug and resolve,
    roll back your changes.
