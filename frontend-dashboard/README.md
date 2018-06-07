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

Project created by rule

(define-counter "projects-by-source" [] ( = (current "source") "api-tickets"))

Project created

(define-counter "projects-create" [] ( = (current "type") "create-project"))

Tickets created

(define-counter "tickets-created" [] ( = (current "type") "ticket"))

Tickets in "state"

(define-counter "tickets-in-state" [] (and ( = (current "state") "estado") ( = (current "type") "ticket")))

Fraction tickets in one state

(define-signal {"ticket-fraction" (/ (counter-value "tickets-in-state" []) (counter-value "tickets-created" []))} true)')
