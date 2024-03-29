(ns controllers.dashboard-controller)
(require '[clojure.string :as str])
(use 'db.dashboard-model)
(use 'utils.string-util)
(use 'utils.info_dashboard_util)

(defn convert-rule-ids [rule_ids] (
  let [
		converted-rule-ids (if (nil? rule_ids) nil (if (vector? rule_ids) rule_ids [rule_ids]))
	]
	converted-rule-ids ))

(defn convert-enabled [enabled] (
  let [
		converted-enabled (if (nil? enabled) false (if (= "" enabled) false enabled))
	]
	converted-enabled ))

(defn store-dashboard [request] (
	let [
			name (get-in request [:params :name])
			enabled (convert-enabled (get-in request [:params :enabled]))
			rule_ids (convert-rule-ids (get-in request [:params :rule_ids]))
			dashboard {:id (uuid) :name name :enabled enabled :rule_ids rule_ids} 
  ]
  (db-store-dashboard dashboard)
 	{:status 200 :body dashboard}
))

(defn find-all-dashboards [params] (
	let [
		enabled (get params "enabled")
		dashboards (db-find-all-dashboards)
	]
	(if (nil? dashboards) [] 
		(if (nil? enabled) 
			dashboards 
			(filter #(= (:enabled %) (read-string enabled)) dashboards)))))


(defn get-dashboard-by-id [id] (
	let [
		dashboard (first (db-get-dashboard-by-id id))
		dashboard-info (get-info dashboard)
	]
	{:status 200 :body dashboard-info}
))

(defn drop-dashboard-by-id [id] (
	db-drop-dashboard-by-id id))

(defn update-dashboard-by-id [request] ( 
	let [
			id (get-in request [:params :id])
			name (get-in request [:params :name])
			enabled (convert-enabled (get-in request [:params :enabled]))
			rule_ids (convert-rule-ids (get-in request [:params :rule_ids]))
      dashboard {:id id :name name :enabled enabled :rule_ids rule_ids} 
  ]
	(drop-dashboard-by-id id)
	(db-store-dashboard dashboard)
	{:status 200 :body dashboard} ))

(defn count-all-dashboards [params] (
	let [
		enabled (get params "enabled")
		dashboards (db-find-all-dashboards)
		count (if (nil? dashboards) 0 
			(if (nil? enabled) 
				(count dashboards) 
				(count (filter #(= (:enabled %) (read-string enabled)) dashboards))))
	]
	{:count count}))