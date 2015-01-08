---
title: AMT Project 1
template: layout.jade
menuIndex: 1
---

This project implements a REST API for our AMT project ([Teaching-HEIGVD-AMT][github]). 

## About
This project is about organizations wich own sensors, and get measures from these sensors.
The users are associated to an organization, and an organization can create facts about the measures of sensors.

See the [specifications][github] for more details.

There are 3 entry points for this REST API.
- Organizations
- Sensors
- Facts

In an organization, we can list, or create users associated to this organization. We can also list and create sensors and facts, also associated to this organization.
With the sensors and facts entry points, we can list all public sensors and all public facts. We can also filter them by type, or organization id. 

For more information, just go to API Reference, then Resources.

[github]: https://github.com/wasadigi/Teaching-HEIGVD-AMT/tree/master/lectures/lecture-07

## Installation
* Clone the git repository [https://github.com/lamesse/AMT-Project1.git](https://github.com/lamesse/AMT-Project1.git) 
* Compile and run it into a glassfish with a working mysql database.
* Generate data by launching [http://localhost:8080/api/generator](http://localhost:8080/api/generator)

## Domain modeling
This is the domain modeling of the project.

![Domain modeling](domainModeling.png)

## Authors
Jonathan Bischof & Antoine Messerli
