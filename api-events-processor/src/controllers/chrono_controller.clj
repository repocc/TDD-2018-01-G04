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

(defn parser-date [date](
	let [
		day (t/day date)
		month (t/month date)
		year (t/year date)
		hour (- (t/hour date) 3)
		minute (t/minute date)
		seconds (t/second date)
	]
	{:day day, :month month, :year year, :hour hour, :minute minute, :seconds seconds}
))

(defn find-snapshot [] (
	let [
		subcounters (db-find-all-snapshot)
	]
	(if (nil? subcounters) [] subcounters)))

(defn store-counters [date] (
	let [
		subcounters (map #(:subcounter %) (db-find-all-subcounters))
		parsed-date (parser-date date)
		snapshot {:date parsed-date, :subcounters subcounters}
	]
	(if (= subcounters []) nil (db-store-snapshot snapshot))
))

(defn run-chrono [frequency]
	(chime-at (periodic-seq (t/now)
		(-> frequency t/seconds))
			(fn [date]
			(store-counters date)))
)
