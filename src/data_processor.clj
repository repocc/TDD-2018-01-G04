(ns data-processor (:use clojure.pprint))
(use 'rule)
(use 'counter)

(defn initialize-processor [rules]
  (	let [ 
  		rules (map evaluate-function rules)
  		counters (filter is-counter rules)
  		signals (filter is-signal rules)
  		data []
		]
  (zipmap [:counters :signals :data] [counters signals data])))
         
(defn process-data [state new-data]
  ( let [
  		all-data (concat (:data state) new-data)
  	]
  [nil []]))
         
(defn query-counter [state counter-name counter-args]
  (let [
  	counter (get-counter-by-name (:counters state) counter-name)
  ]
  (if(nil? counter) 0 (:count counter))))