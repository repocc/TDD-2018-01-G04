(ns controllers.project-controller)
(use 'db.project-model)
(use 'db.ticket-model)
(use 'utils.string-util)
(use 'services.event-service)

(defn store-project [request] (
	let [
			name (get-in request [:params :name])
			owner (get-in request [:params :owner])
			ticket-types (get-in request [:params :ticket-types])
			states (get-in request [:params :states])
			users (get-in request [:params :users])
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

(defn find-project-by-id [id] (
	let [
		project (first (db-find-project-by-id id))
		tickets (db-get-tickets-by-project id)
		result (merge project {:tickets tickets})
	]
	{:status 200 :body result}
))

(defn in? 
  "true if coll contains elm"
  [coll elm]  
  (some #(= elm (:id %)) coll))

(defn find-projects-by-username [params] (
	let [
		username (get params "username")
		users (db-find-all-projects)
	]
	(if (nil? users) [] 
		(if (nil? username) 
			users 
			(filter #(or (= (:owner %) username) (in? (:users %) username)) users)))
))
