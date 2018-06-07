# Frontend Dashboard

This project was bootstrapped with [Create React App](https://github.com/facebookincubator/create-react-app).

# Running

* npm install
* npm start

# Port

* 3001

# Login

* admin
* guest

## To disable CORS in Chrome

* On Linux: chromium-browser --disable-web-security --user-data-dir
* On Windows: chrome.exe --disable-web-security --user-data-dir


# Example

## Created projects by source

(define-counter "projects-by-source" [] ( = (current "source") "api-tickets"))

## Created projects

(define-counter "projects-create" [] ( = (current "type") "create-project"))

## Created tickets

(define-counter "tickets-created" [] ( = (current "type") "ticket"))

## Tickets by "state"

(define-counter "tickets-in-state" [] (and ( = (current "state") "estado") ( = (current "type") "ticket")))

## Tickets fraction by state

(define-signal {"ticket-fraction" (/ (counter-value "tickets-in-state" []) (counter-value "tickets-created" []))} true)')
