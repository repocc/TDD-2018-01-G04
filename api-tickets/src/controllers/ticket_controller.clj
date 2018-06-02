(ns controllers.ticket-controller)
(use 'db.project-model)
(use 'db.ticket-model)
(use 'utils.string-util)
(use 'services.event-service)

(defn store-ticket [request] (
	let [
			title (get-in request [:params :title])
			description (get-in request [:params :description])
			type (get-in request [:params :type])
			assigned (get-in request [:params :assigned])
			project (get-in request [:params :project])
			project-data (first (db-find-project-by-id project))
			initial-state (:state (first (:states project-data)))
			ticket {:id (uuid) :title title :description description :type type :assigned assigned :project project :state initial-state} 
  ]
  (db-store-ticket ticket)
 	;(log-create-ticket ticket)
 	{:status 200 :body ticket}
))

(defn update-ticket-by-id [request] ( 
	let [
			id (get-in request [:params :id])
			title (get-in request [:params :title])
			description (get-in request [:params :description])
			type (get-in request [:params :type])
			assigned (get-in request [:params :assigned])
			state (get-in request [:params :state])
			project (get-in request [:params :project])
			ticket {:id (uuid) :title title :description description :type type :assigned assigned :project project :state state}
  ]
	(db-drop-ticket-by-id id)
	;(log-update-ticket ticket)
	(db-store-ticket ticket)
	{:status 200 :body ticket} ))
