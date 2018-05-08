(ns controllers.chrono-controller)
(require '[clojure.string :as str]
			'[chime :refer [chime-ch]]
  			'[chime :refer [chime-at]]
          	'[clj-time.core :as t]
          	'[clojure.core.async :as a :refer [<! go-loop]]
          	'[clj-time.periodic :refer [periodic-seq]])

(use 'db.rule-model)
(use 'db.counter-model)
(use 'core.rule)
(use 'core.data-processor)
(use 'db.chrono-model)

(defn find-snapshot [] (
	let [
		subcounters (db-find-all-snapshot)
	]
	(if (nil? subcounters) [] subcounters)))

(defn store-counters [date] (
	let [
		subcounters (map #(:subcounter %) (db-find-all-subcounters))
		snapshot {:date (str date), :subcounters subcounters}
	]
	(if (= subcounters []) nil (db-store-snapshot snapshot))
	;(db-store-snapshot {:date (str date), :subcounters subcounters})
))

(defn run-chrono [frequency]
	(chime-at (periodic-seq (t/now)
		(-> frequency t/seconds))
			(fn [date]
			(store-counters date)))
)
