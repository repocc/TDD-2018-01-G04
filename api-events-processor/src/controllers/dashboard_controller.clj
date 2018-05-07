(ns controllers.dashboard-controller)
(require '[clojure.string :as str])
(use 'db.dashboard-model)
(use 'utils.string-util)

(defn store-dashboard [request] (
	let [
			name (get-in request [:params :name])
			rule_ids (get-in request [:params :rule_ids])
      dashboard {:id (uuid) :name name :rule_ids rule_ids} 
  ]
  (db-store-dashboard dashboard)
 	{:status 200 :body dashboard}
))

(defn find-all-dashboards [] (
	let [
		dashboards (db-find-all-dashboards)
	]
	(if (nil? dashboards) [] dashboards)))

(defn get-dashboard-by-id [id] 
	{:status 200 :body (db-get-dashboard-by-id id)}
)

(defn drop-dashboard-by-id [id] 
	(db-drop-dashboard-by-id id)
 	{:status 200}
)

(defn count-all-dashboards [] (
	let [
		count (db-count-all-dashboards)
	]
	{:count count}))