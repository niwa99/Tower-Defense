# Tower Defense - Testplan

## Table of Contents

-   [1. Introduction](#1-introduction)

    -   [1.1 Purpose](#11-purpose)
    -   [1.2 Scope](#12-scope)
    -   [1.3 Intended Audience](#13-intended-audience)
    -   [1.4 Document Terminology and Acronyms](#14-document-terminology-and-acronyms)
    -   [1.5 References](#15-references)

-   [2. Evaluation Mission and Test Motivation](#2-evaluation-mission-and-test-motivation)

    -   [2.1 Background](#21-background)
    -   [2.2 Evaluation Mission](#22-evaluation-mission)
    -   [2.3 Test Motivators](#23-test-motivators)

-   [3. Target Test Items](#3-target-test-items)

-   [4. Outline of Planned Tests](#4-outline-of-planned-tests)
    
    -   [4.1 Outline of Test Inclusions](#41-outline-of-test-inclusions)
    -   [4.2 Outline of other candidates for potential inclusion](#42-outline-of-other-candidates-for-potential-inclusion)
    -   [4.3 Outline of Test Exclusions](#43-outline-of-test-exclusions)
    
-   [5. Test Approach](#5-test-approach)
    
    -   [5.1 Initial Test-Idea Catalogs and other reference sources](#51-initial-test-idea-catalogs-and-other-reference-sources)
    -   [5.2 Testing Techniques and Types](#52-testing-techniques-and-types)
    
        -   [5.2.1 Data and Database Integrity Testing](#521-data-and-database-integrity-testing)
        -   [5.2.2 Function Testing](#522-function-testing)
        -   [5.2.3 Business Cycle Testing](#523-business-cycle-testing)
        -   [5.2.4 User Interface Testing](#524-user-interface-testing)
        -   [5.2.5 Performance Profiling](#525-performance-profiling)
        -   [5.2.6 Load Testing](#526-load-testing)
        -   [5.2.7 Stress Testing](#527-stress-testing)
        -   [5.2.8 Volume Testing](#528-volume-testing)
        -   [5.2.9 Security and Access Control Testing](#529-security-and-access-control-testing)
        -   [5.2.10 Failover and Recovery Testing](#5210-failover-and-recovery-testing)
        -   [5.2.11 Configuration Testing](#5211-configuration-testing)
        -   [5.2.12 Installation Testing](#5212-installation-testing)
        -   [5.2.13 Usability Testing](#5213-usability-testing)
        
-   [6. Entry and Exit Criteria](#6-entry-and-exit-criteria)
    
    -   [6.1 Test Plan](#61-test-plan)
    
        -   [6.1.1 Test Plan Entry Criteria](#611-test-plan-entry-criteria)
        -   [6.1.2 Test Plan Exit Criteria](#612-test-plan-exit-criteria)
        -   [6.1.3 Suspension and resumption criteria](#613-suspension-and-resumption-criteria)
    
    -   [6.2 Test Cycles](#62-test-cycles)
    
        -   [6.2.1 Test Cycle Entry Criteria](#621-test-cycle-entry-criteria)
        -   [6.2.2 Test Cycle Exit Criteria](#622-test-cycle-exit-criteria)
        -   [6.2.3 Test Cycle abnormal termination](#623-test-cycle-abnormal-termination)

-   [7. Deliverables](#7-deliverables)

    -   [7.1 Test Evaluation Summaries](#71-test-evaluation-summaries)
    -   [7.2 Reporting on Test Coverage](#72-reporting-on-test-coverage)
    -   [7.3 Perceived Quality Reports](#73-perceived-quality-reports)
    -   [7.4 Incident Logs and Change Requests](#74-incident-logs-and-change-requests)
    -   [7.5 Smoke Test Suite and supporting Test Scripts](#75-smoke-test-suite-and-supporting-test-scripts)
    -   [7.6 Additional work products](#76-additional-work-products)
    
        -   [7.6.1 Detailed Test Results](#761-detailed-test-results)
        -   [7.6.2 Additional automated functional Test Scripts](#762-additional-automated-functional-test-scripts)
        -   [7.6.3 Test Guidelines](#763-test-guidelines)
        -   [7.6.4 Traceability Matrices](#764-traceability-matrices)

-   [8. Testing Workflow](#8-testing-workflow)

-   [9. Environmental Needs](#9-environmental-needs)

    -   [9.1 Base System Hardware](#91-base-system-hardware)
    -   [9.2 Base Software Elements in the Test Environment](#92-base-software-elements-in-the-test-environment)
    -   [9.3 Productivity and Support Tools](#93-productivity-and-support-tools)
    -   [9.4 Test Environment Configurations](#94-test-environment-configurations)
    
-   [10. Responsibilities, Staffing and Training Needs](#10-responsibilities)

    -   [10.1 People and Roles](#101-people-and-roles)
    -   [10.2 Staffing and Training Needs](#102-staffing-and-training-needs)
    
-   [11. Iteration Milestones](#11-iteration-milestones)

-   [12. Risks, Dependencies, Assumptions and Constraints](#12-risks-dependencies-assumptions-and-constraints)

-   [13. Management Process and Procedures](#13-management-process-and-procedures)

    -   [13.1 Measuring and Assessing the Extent of Testing](#131-measuring-and-assessing-the-extent-of-testing)
    -   [13.2 Assessing the deliverables of this Test Plan](#132-assessing-the-deliverables-of-this-test-plan)
    -   [13.3 Problem Reporting, Escalation and Issue Resolution](#133-problem-reporting-escalation-and-issue-resolution)
    -   [13.4 Managing Test Cycles](#134-managing-test-cycles)
    -   [13.5 Traceability Strategies](#135-traceability-strategies)
    -   [13.6 Approval and Signoff](#136-approval-and-signoff)


## 1. Introduction

### 1.1 Purpose

The purpose of the Iteration Test Plan is to gather all of the information necessary to plan and control the test effort for a given iteration. It describes the approach to testing the software, and is the top-level plan generated and used by managers to direct the test effort.
This Test Plan for the Project "Tower Defense" supports the following objectives:

-   definition of items to be tested
-   definition of the motivation for extensive testing
-   strategy for detailed testing
-   definition of neccessary resources

### 1.2 Scope

The testplan describes the use of several tests. In particular, the document will deal with Unit-, UI- and UX-tests.

### 1.3 Intended Audience

Mainly, an internal use is intended.

### 1.4 Document Terminology and Acronyms

-   **UI**  User Interface
-   **UX**  User Experience
-   **SRS** Software Requirements Specification
-   **SAD** Software Architecture Document
-   **n/a** not applicable
-   **tbd** to be determined
-   **UC**  Use Case
-   **CI**  Continuous Integration

### 1.5 References

| Reference |
| --------- |
| [Blog](https://dh-towerdefense.de) |
| [GitHub](https://github.com/niwa99/Tower-Defense) |
| [SRS](https://github.com/niwa99/Tower-Defense/blob/master/documents/SRS.md) |
| [SAD](https://github.com/niwa99/Tower-Defense/blob/master/documents/SAD.md) |
| [Use Case Diagram](https://github.com/niwa99/Tower-Defense/blob/master/documents/UCS/UseCaseDiagram-2020-04-25.png) |
| [UC 1: Start the game from the menu](https://github.com/niwa99/Tower-Defense/blob/master/documents/UCS/UCS-Start_The_Game_From_The_Menu/UCS-Start_The_Game_From_The_Menu.md) |
| [UC 2: Start waves](https://github.com/niwa99/Tower-Defense/blob/master/documents/UCS/UCS-Start_Waves/UCS-Start_Waves.md) |
| [UC 3: Build towers on selected area](https://github.com/niwa99/Tower-Defense/blob/master/documents/UCS/UCS-Build_Towers_On_Selected_Area/UCS-Build_Towers_On_Selected_Area.md) |
| [UC 4: Return to main menu](https://github.com/niwa99/Tower-Defense/blob/master/documents/UCS/UCS-Return_To_Main_Menu/UCS-Return_To_Main_Menu.md) |
| [UC 5: Upgrade towers](https://github.com/niwa99/Tower-Defense/blob/master/documents/UCS/UCS-Upgrade_Towers/UCS-Upgrade_Towers.md) |
| [UC 6: Sell towers](https://github.com/niwa99/Tower-Defense/blob/master/documents/UCS/UCS-Sell_Towers/UCS-Sell_Towers.md) |
| [UC 7: Select between different towers](https://github.com/niwa99/Tower-Defense/blob/master/documents/UCS/UCS-Select_Between_Different_Towers/UCS-Select_Between_Different_Towers.md) |
| [UC 8: Edit Settings](https://github.com/niwa99/Tower-Defense/blob/master/documents/UCS/UCS-Edit_Settings/UCS-Edit_Settings.md) |
| [UC 9: Toggle Sound and Animations](https://github.com/niwa99/Tower-Defense/blob/master/documents/UCS/UCS-Toggle_Sound_And_Animations/UCS-Toggle_Sound_And_Animations.md) |
| [UC 10: Choose difficulties](https://github.com/niwa99/Tower-Defense/blob/master/documents/UCS/UCS-Choose_Difficulties/UCS-Choose_Difficulties.md) |

## 2. Evaluation Mission and Test Motivation

### 2.1 Background

If a project becomes tested continuously, one can almost ensure that every additional commit does not break the existing functionalities without being noticed.
Due to our game engine, we necessarily have to insure that new features cannot break the game engine.
Those are the reason for why a testplan is important for this project.

### 2.2 Evaluation Mission

The consideration of using tests came up when the game engine was able to run in a very basic way and the first features have been implemented.
With the ongoing progress, tests become more important in order to keep our previous progress alive.

### 2.3 Test Motivators

-   Use Cases
-   Functional Necessities
-   Risk of crashing previous features

## 3. Target Test Items

The listing below identifies those test itemssoftware, hardware, and supporting product elements that have been identified as targets for testing. This list represents what items will be tested.

-   Android/Java backend
-   User Interface in combination with the Java backend

## 4. Outline of Planned Tests

### 4.1 Outline of Test Inclusions

-   Unit tests
-   Functional tests

### 4.2 Outline of Other Candidates for Potential Inclusion

-   Usability tests
-   Performance tests

### 4.3 Outline of Test Exclusions

-   Stress tests (because the app will run on separate devices, not on a central server)
-   Security tests (because the app does not make use of any personal data and does not need any internet connection)
-   Recovery tests (not important for a "by-the-way"-game)

## 5. Test Approach

### 5.1 Initial Test-Idea Catalogs and Other Reference Sources

n/a

### 5.2 Testing Techniques and Types

#### 5.2.1 Data and Database Integrity Testing

n/a

#### 5.2.2 Function Testing

|                        |                                                                             |
|------------------------|-----------------------------------------------------------------------------|
| Technique Objective    | Background actions of the game engine should be simulated.                  |
| Technique              | Unit testing with JUnit and Mockito.                                        |
| Oracles                | The game engine receives mock-data and should run through expected actions. |
| Required Tools         | JUnit, Mockito                                                              |
| Success Criteria       | All tests pass without errors and expected behaviour can be observed.       |
| Special Considerations | -                                                                           |

#### 5.2.3 Business Cycle Testing

n/a

#### 5.2.4 User Interface Testing

|                        |                                                                            |
|------------------------|----------------------------------------------------------------------------|
| Technique Objective    | UI interactions by a user should be simulated.                             |
| Technique              | UI testing with the tools Cucumber and Espresso.                           |
| Oracles                | Buttons will be clicked and corresponding actions should be runned.        |
| Required Tools         | Cucumber, Espresso                                                         |
| Success Criteria       | All narratives pass without errors and expected behaviour can be observed. |
| Special Considerations | -                                                                          |

#### 5.2.5 Performance Profiling

tbd

#### 5.2.6 Load Testing

n/a

#### 5.2.7 Stress Testing

n/a

#### 5.2.8 Volume Testing

n/a

#### 5.2.9 Security and Access Control Testing

n/a

#### 5.2.10 Failover and Recovery Testing

n/a

#### 5.2.11 Configuration Testing

n/a

#### 5.2.12 Installation Testing

n/a

#### 5.2.13 Usability Testing

|                        |                                                                            |
|------------------------|----------------------------------------------------------------------------|
| Technique Objective    | Real users will receive the app and test if the app is easily useable.     |
| Technique              | Realistic testing with real users.                                         |
| Oracles                | User plays the game several times and makes use of many use cases.         |
| Required Tools         | Android devices, communication tools                                       |
| Success Criteria       | Users are able to make use of all use cases without any help/instructions. |
| Special Considerations | -                                                                          |

## 6. Entry and Exit Criteria

### 6.1 Test Plan

#### 6.1.1 Test Plan Entry Criteria

A new version of our app is developed and pushed onto the GitHub repository.

#### 6.1.2 Test Plan Exit Criteria

All tests have passed successfully.

#### 6.1.3 Suspension and Resumption Criteria

While developing (without publishing), it is allowed to skip the CI-tests in order to save time. The tests can be runned locally nevertheless.

### 6.2 Test Cycles

#### 6.2.1 Test Cycle Entry Criteria

n/a

#### 6.2.2 Test Cycle Exit Criteria

n/a

#### 6.2.3 Test Cycle Abnormal Termination

n/a

## 7. Deliverables

### 7.1 Test Evaluation Summaries

Every time a test becomes runned (when a commit becomes pushed), the result of this test is saved in the CI-summaries on GitHub.

### 7.2 Reporting on Test Coverage

tbd

### 7.3 Perceived Quality Reports

tbd

### 7.4 Incident Logs and Change Requests

In the GitHub-CI summaries, a log of all incidents is automatically generated.

### 7.5 Smoke Test Suite and Supporting Test Scripts

tbd

### 7.6 Additional Work Products

#### 7.6.1 Detailed Test Results

tbd

#### 7.6.2 Additional Automated Functional Test Scripts

n/a

#### 7.6.3 Test Guidelines

Because of time-aspects and the connection between the game engine and the user interface, we cannot provide a high test coverage right now.
Nevertheless, every developer should ensure to write as many tests for the self-created features as possible.
Of course, existing tests need to be kept up to date.

#### 7.6.4 Traceability Matrices

n/a

## 8. Testing Workflow

While developing new features, every developer has to ensure that all given tests still work properly. If they do not, the developer has to find the issue and adjust the code or the test.
After the local testing, an automatic testrun will follow on GitHub.

## 9. Environmental Needs

### 9.1 Base System Hardware

The following table sets forth the system resources for the test effort presented in this Test Plan.

| Resource             | Quantity | Name and Type         |
|----------------------|----------|-----------------------|
| Test Development PCs | 3        | Different Windows PCs |

### 9.2 Base Software Elements in the Test Environment

The following base software elements are required in the test environment for this Test Plan.

| Software Element Name | Version | Type and Other Notes   |
|-----------------------|---------|------------------------|
| Windows               | 10      | Operating System       |
| Android SDK           |  10     | SDK                    |
| Nexus 5X              | 29      | Android Virtual Device |
| Android Studio        | 3.6.3   | IDE                    |

### 9.3 Productivity and Support Tools

The following tools will be employed to support the test process for this Test Plan.

| Tool Category or Type | Tool Brand Name |
|-----------------------|-----------------|
| Code Hoster           | GitHub.com      |
| Project Management    | Jira Software   |
| CI Service            | GitHub CI       |
| Metrics Tool          | tbd             |
| Test Coverage Monitor | tbd             |

### 9.4 Test Environment Configurations

In order to run the tests, only the source code and an adequate test environment (such as Android Studio) is needed.

## 10. Responsibilities

### 10.1 People and Roles

This table shows the staffing assumptions for the test effort.

| Role | Minimum Resources Recommended (number of full-time roles allocated) | Specific Responsibilities or Comments |
|---|---|---|
| Test Manager | 1 | Provides management oversight. <br> Responsibilities include: <br> planning and logistics <br> agree mission <br> identify motivators<br> acquire appropriate resources<br> present management reporting<br> advocate the interests of test<br>evaluate effectiveness of test effort |
| Test Designer | 3 | Defines the technical approach to the implementation of the test effort. <br> Responsibilities include:<br> define test approach<br> define test automation architecture<br> verify test techniques<br> define testability elements<br> structure test implementation|
| Test System Administrator | 1 | Ensures test environment and assets are managed and maintained.<br> Responsibilities include:<br> 	administer test management system<br> install and support access to, and recovery of, test environment configurations and test labs | 
| Tester | 3 |	Implements and executes the tests.<br> Responsibilities include:<br> implement tests and test suites<br> execute test suites<br> log results<br> analyze and recover from test failures<br> document incidents|
| Implementer | 3 | Implements and unit tests the test classes and test packages.<br> Responsibilities include:<br> creates the test components required to support testability requirements as defined by the designer |

In fact, every project-developer is responsible for the self-developed features and hence, every developer has to test almost daily.

### 10.2 Staffing and Training Needs

n/a

## 11. Iteration Milestones

| Milestone                | Planned Start Date | Actual Start Date | Planned End Date | Actual End Date |
|--------------------------|--------------------|-------------------|------------------|-----------------|
| Develop first Unit Tests | 14.11.2019         | 14.11.2019        | 21.11.2019       | 14.11.2019      |
| Tests integrated in CI   | 07.05.2020         | 10.05.2020        | 14.05.2020       | 10.05.2020      |

## 12. Risks, Dependencies, Assumptions, and Constraints

| Risk                               | Mitigation Strategy                       | Contingency (Risk is realized)        |
|------------------------------------|-------------------------------------------|---------------------------------------|
| Features which cause test-failures | Developer tests feature before publishing | Awareness of publishing-risk          |
| Technical Issue of a Tester | other Tester runs tests; tester helps tester     | Multiple testings on multiple devices | 

## 13. Management Process and Procedures

### 13.1 Measuring and Assessing the Extent of Testing

n/a

### 13.2 Assessing the Deliverables of this Test Plan

n/a

### 13.3 Problem Reporting, Escalation, and Issue Resolution

n/a

### 13.4 Managing Test Cycles

n/a

### 13.5 Traceability Strategies

n/a

### 13.6 Approval and Signoff

n/a