(ns data-processor (:use clojure.pprint))
(use 'rule)
(use 'counter)

(defn initialize-processor [rules]
  (	let [ 
  		rules (map evaluate-function rules)
  		counters (filter is-counter rules)
  		signals (filter is-signal rules)
  		data [{}]
  		new-data []
  		state (zipmap [:counters :signals :data :new-data] [counters signals data new-data])
		]
  [state]))
         
(defn process-data [state new-data]
  ( let [
  		state (map #(evaluate-rules % new-data) state)
  	]
  state))
         
(defn query-counter [state counter-name counter-args]
  (get-counter-value (:counters (first state)) counter-name counter-args))
