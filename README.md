# Backend Engineer Recruitment Task

## Features

List of features from task description with their status.

1. Store functions for customers

- [X] browse games
- [X] search in game catalog
- [ ] add games to a shopping cart
- [ ] order finalization

2. Functions for backoffice employees

- [X] create game entry with first draft
- [X] modify draft details
- [X] list all drafts
- [X] check draft details
- [X] publish draft
- [X] unpublish draft
- [ ] delete draft

## Requirements

- Mongo - provided docker-compose file im Mongo catalog

## Final thoughts

Application is divided into three domains: cart, catalog and users. Catalog module is implemented using CQRS
architecture. As a part ob building process docker container is created so application can be deployed in orchestration
tool like Kubernetes. There is also provided [Insomnia](https://insomnia.rest/ "Insomnia") catalog for manual testing of
REST API. 
