(ns utils.info_dashboard_util)
(use 'db.counter-model)
(use 'db.rule-model)
(use 'controllers.snapshot-controller)
(use 'core.rule)

(defn get-name-rule [rule-id] (
	let [
		rule (first (filter #(= (:id %) rule-id) (db-find-all-rules)))
		name (:name (evaluate-function (read-string(:query rule))))
	]
	name
))

(defn get-snapshot-info [rule-id snapshot](
	let[
		date (:date snapshot)
		subcounters-record (first (filter #(= (:id %) rule-id) (first (:subcounters snapshot))))
		value (if (= (:subcounters subcounters-record) {}) {[] 0} (:subcounters subcounters-record))
	]
	{:date date :value value}
))

(defn get-counters-info [rule-id subcounters snapshot](
	let[
		subcounters-value (first (filter #(= (:id %) rule-id) subcounters))
		snapshot-value (map #(get-snapshot-info rule-id %) snapshot)
		value (if (nil? subcounters-value) {[] 0} (:subcounters subcounters-value))
		value (if (= value {}) {[] 0} value)
		snapshots ( if (= [] snapshot-value) [] snapshot-value)
		name (get-name-rule rule-id)
	]
	{:id rule-id, :name name, :value value, :snapshots snapshots}

))

(defn get-dashboard-info [dashboard subcounters snapshot] (
	let [
		id (dashboard :id) 
		name (dashboard :name) 
		rule-ids (dashboard :rule_ids)
		enabled (dashboard :enabled)
		counters (map #(get-counters-info % subcounters snapshot) rule-ids)
	]
	{:id id :name name :enabled enabled :rule_ids rule-ids :counters counters}
))

(defn get-info [dashboard](
	let [
		subcounters (:subcounter (first (db-find-all-subcounters)))
		snapshot (find-snapshot)
		dashboard-info (get-dashboard-info dashboard subcounters snapshot)
	]
	dashboard-info
))