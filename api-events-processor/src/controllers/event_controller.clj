(ns controllers.event-controller)
(require '[clojure.string :as str])
(use 'db.rule-model)
(use 'db.counter-model)
(use 'core.rule)
(use 'core.data-processor)



(defn get-subcounters [rule] (
	let [
		name-rule (:name rule)
		subcounters (:subcounters rule)
	]
	{:name name-rule, :subcounters subcounters}
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


(defn process-events [request] (
	let [
			event (get-in request [:params :event])
			data (into {} (for [[k v] event] [(name k) v]))
			rules (into '() (map #(read-string (second (vals %))) (db-find-all-rules)))
			state (initialize-processor rules)

			counters (get-counters (:counters state))
			current-state (merge state {:counters counters})

			new-state (process-data current-state data)

			subcounters (map get-subcounters (:counters (first new-state)))

	]
	(db-drop-subcounters)
	(db-store-subcounter {:removable true, :subcounter subcounters})

	{:status 200}
))
