(ns controllers.project-controller)
(use 'db.project-model)
(use 'utils.string-util)

(defn store-project [request] (
	let [
			name (get-in request [:params :name])
			;parsed-query (try (evaluate-function (read-string query)) (catch Exception e nil))
			project {:id (uuid) :name name} 
  ]
  (db-store-project project)
 	{:status 200 :body project}
))

(defn find-all-projects [request] (
	let [
		projects (db-find-all-projects)
	]
	(if (nil? projects) [] projects)))