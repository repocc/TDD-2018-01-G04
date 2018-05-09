(ns utils.info_dashboard_util)
(use 'db.counter-model)
(use 'controllers.snapshot-controller)


(defn get-snapshot-info [rule-id snapshot](
	let[
		date (:date snapshot)
		subcounters-record (first (filter #(= (:id %) rule-id) (first (:subcounters snapshot))))
	]
	{:date date, :value (:subcounters subcounters-record)}
))

(defn get-counters-info [rule-id subcounters snapshot](
	let[
		subcounters-value (first (filter #(= (:id %) rule-id) subcounters))
		snapshot-value (map #(get-snapshot-info rule-id %) snapshot)
	]
	{:id rule-id, :name (:name-rule subcounters-value), :value (:subcounters subcounters-value),:snapshots snapshot-value }

))

(defn get-dashboard-info [dashboard subcounters snapshot] (
	let [
		name-dashboard (dashboard :name) 
		rule-ids (dashboard :rule_ids)
		counters (map #(get-counters-info % subcounters snapshot) rule-ids)
	]
	{:name name-dashboard, :rule_ids rule-ids, :counters counters}
))

(defn get-info [dashboard](
	let [
		subcounters (:subcounter (first (db-find-all-subcounters)))
		snapshot (find-snapshot)
		dashboard-info (get-dashboard-info dashboard subcounters snapshot)
	]
	dashboard-info
))