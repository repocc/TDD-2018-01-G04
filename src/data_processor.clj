(ns data-processor (:use clojure.pprint))
(use 'rules)

(defn initialize-processor [rules]
  (	let [ 
  		rules (map evaluate-function rules)
  		counters (filter is-counter rules)
  		signals (filter is-signal rules)
  		data []
		]
  (zipmap [:counters :signals :data] [counters signals data])))
         
(defn process-data [state new-data]
  [nil []])
         
(defn query-counter [state counter-name counter-args]
  0)