(ns data-processor (:use clojure.pprint))
(use 'rules)

(defn initialize-processor [rules]
  (	let [ 
  		counters (map evaluate-function rules)
  		data []
		]
  (zipmap [:counters :data] [counters data])))
         
(defn process-data [state new-data]
  [nil []])
         
(defn query-counter [state counter-name counter-args]
  0)