# Introduction

This is a simple clojure REST example that denmonstrates how a REST api can be built using clojure, ring and composure.

# Port

* 3001

# Routes

## Authenticate a user given in the body
POST http://localhost:3000/api/auth

## Get all roles
GET http://localhost:3000/api/role

## Get all users
GET http://localhost:3000/api/user

## Get all ticket types
GET http://localhost:3000/api/tickettype
