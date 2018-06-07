(ns services.event-service)
(require '[clj-http.client :as client])

(def api-events-processor-base-url "http://localhost:3000/api")
(def source "api-tickets")

(defn log-create-project [] (
	let [
		url (str api-events-processor-base-url "/event")
		body {:event {:source source :type "create-project"}}
	]
	(try
     (client/post url {:form-params body :content-type :json})
     (catch Exception e (str "caught exception: " (.getMessage e))))
	))

(defn log-ticket [ticket] (
	let [
		url (str api-events-processor-base-url "/event")
		body {:event {:source source :type "ticket" :state (:state ticket)}}
	]
	(try
     (client/post url {:form-params body :content-type :json})
     (catch Exception e (str "caught exception: " (.getMessage e))))
	))
