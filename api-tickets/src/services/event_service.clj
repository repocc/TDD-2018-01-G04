(ns services.event-service)
(require '[clj-http.client :as client])

(def api-events-processor-base-url "http://localhost:3000/api")
(def source "api-tickets")

(defn log-create-project [] (
	let [
		url (str api-events-processor-base-url "/event")
		body {:event {:source source :type "create-project"}}
	]
	(client/post url {:form-params body :content-type :json})))

(defn log-ticket [ticket] (
	let [
		url (str api-events-processor-base-url "/event")
		body {:event {:source source :type "ticket" :state (:state ticket)}}
	]
	(client/post url {:form-params body :content-type :json})))
