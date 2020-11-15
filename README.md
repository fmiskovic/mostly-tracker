# Project Time Tracking

### Build project
`./mvnw clean install`

### Run project
`docker-compose up --build`

### API

##### projects

GET - /api/v1/projects/{entryId} - Find project by its id

DELETE - /api/v1/projects/{entryId} - Delete project by its id

GET - /api/v1/projects/page - Browse page of projects

GET - /api/v1/projects/search - Search for projects and filter it with a query params

PUT - /api/v1/projects/save - Save new project

POST - /api/v1/projects/{projectId}/update - Update existing project

##### entries

GET - /api/v1/entries/{entryId} - Find project entry by its id

DELETE - /api/v1/entries/{entryId} - Delete project entry by its id

GET - /api/v1/entries/project/{projectId} - Find all project entries by project id

GET - /api/v1/entries/search - Search for project entries and filter it with a query params

PUT - /api/v1/entries/save - Save new project entry

##### summary

GET - /api/v1/summary/project/{projectId} - Retrieves project summary 