# Introduction

This is a simple clojure REST example that denmonstrates how a REST api can be built using clojure, ring and composure.

# Port

* 3000

# Routes

## Get all rules
GET http://localhost:3000/api/rule

## Get all rules count
GET http://localhost:3000/api/rule/count

## Save a dashboard given in the body
POST http://localhost:3000/api/dashboard

## Get all enabled dashboards
GET http://localhost:3000/api/dashboard

## Get enabled dashboards count
GET http://localhost:3000/api/dashboard/count

## Get all counters
GET http://localhost:3000/api/counter

## Get a particular dashboard
GET http://localhost:3000/api/dashboard/:id

## Update a particular dashboard
PUT http://localhost:3000/api/dashboard/:id

## Get all snapshots
GET http://localhost:3000/api/snapshot

## Delete a particular dashboard
DELETE http://localhost:3000/api/dashboard/:id

## Save a rule given in the body
POST http://localhost:3000/api/rule

## Authenticate a user given in the body
POST http://localhost:3000/api/auth

## Save an event given in the body
POST http://localhost:3000/api/event
