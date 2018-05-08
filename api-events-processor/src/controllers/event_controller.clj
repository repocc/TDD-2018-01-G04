(ns controllers.event-controller)
(require '[clojure.string :as str])

(use 'db.rule-model)
(use 'db.counter-model)
(use 'core.rule)
(use 'core.data-processor)


(defn get-subcounters [rule id-rules] (
	let [
		name-rule (:name rule)
		subcounters (:subcounters rule)
		id (first (filter #(= name-rule (% :name-counter)) id-rules))
	]
	{:name name-rule, :subcounters subcounters, :id (id :id-rule), :name-rule (id :name-rule)}
))


(defn merge-counter [counter subcounters] (
	let [
		subcounter (into {} (filter #(= (% :name) (:name counter)) subcounters))
		subcounter-merged (if (= subcounter {}) subcounter (:subcounters subcounter))
	]
	(merge counter {:subcounters subcounter-merged})
))

(defn get-counters [counters](

	let [
		subcounters (:subcounter (first (db-find-all-subcounters)))
		current-counters (map #(merge-counter % subcounters) counters)
	]
	current-counters
))


(defn find-all-counters [] (
	let [
		subcounters (db-find-all-subcounters)
	]
	(if (nil? subcounters) [] subcounters)))


(defn get-id-rules [rule] (
	let[
		id (:id rule)
		name-rule (read-string (:name rule))
		name-counter (second (read-string (:query rule)))
	]
	{:id-rule id, :name-rule name-rule, :name-counter name-counter}
))

(defn process-events [request] (
	let [
			event (get-in request [:params :event])
			data (into {} (for [[k v] event] [(name k) v]))
			db-rules (db-find-all-rules)
			rules (into '() (map #(read-string (:query %)) db-rules))
			id-rules (map get-id-rules db-rules)
			state (initialize-processor rules)

			counters (get-counters (:counters state))
			current-state (merge state {:counters counters})

			new-state (process-data current-state data)

			subcounters (map #(get-subcounters % id-rules) (:counters (first new-state)))

	]
	(db-drop-subcounters)
	(db-store-subcounter {:removable true, :subcounter subcounters})

	{:status 200}
))
