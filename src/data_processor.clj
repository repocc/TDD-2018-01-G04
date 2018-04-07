(ns data-processor (:use clojure.pprint))
(use 'rule)
(use 'counter)

(defn initialize-processor [rules]
  (	let [ 
  		rules (map evaluate-function rules)
  		counters (filter is-counter rules)
  		signals (filter is-signal rules)
      data-filter (find-data counters signals)
  		data [{}]
  		new-data []
		](zipmap [:counters :signals :data-filter :data :new-data] [counters signals data-filter data new-data])
  ))
         
(defn process-data [state new-data]
  ( let [
      state (evaluate-rules state new-data)
      new-state (merge state {:data (conj (state :data) new-data)})
  	]
  [new-state {}]))
         
(defn query-counter [state counter-name counter-args]
  (get-counter-value (:counters  state) counter-name counter-args))
