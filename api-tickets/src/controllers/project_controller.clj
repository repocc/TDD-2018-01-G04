(ns controllers.project-controller)
(use 'db.project-model)
(use 'utils.string-util)
(use 'services.event-service)

(defn store-project [request] (
	let [
			name (get-in request [:params :name])
			owner (get-in request [:params :owner])
			ticket-types (get-in request [:params :ticket-types])
			states (get-in request [:params :states])
			users (get-in request [:params :users])
			;parsed-query (try (evaluate-function (read-string query)) (catch Exception e nil))
			project {:id (uuid) :name name :owner owner :ticket-types ticket-types :states states :users users} 
  ]
  (db-store-project project)
 	(log-create-project)
 	{:status 200 :body project}
))

(defn find-all-projects [request] (
	let [
		projects (db-find-all-projects)
	]
	(if (nil? projects) [] projects)))