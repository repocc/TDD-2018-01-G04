(ns data-processor (:use clojure.pprint))
(use 'rule)
(use 'counter)
(use 'filter-data)
(use 'rules-evaluator)

(defn initialize-processor [rules]
  (	let [ 
  		rules (map evaluate-function rules)
  		counters (filter is-counter rules)
  		signals (filter is-signal rules)
      data-filter (find-data counters signals)
  		data [{}]
		](zipmap [:counters :signals :data-filter :data ] [counters signals data-filter data ])
  ))
         
(defn process-data [state new-data]
  ( let [
      [state results] (evaluate-rules state new-data)
      new-state (merge state {:data (conj (state :data) new-data)})
  	]
  [new-state results]))
         
(defn query-counter [state counter-name counter-args]
  (get-counter-value (:counters  state) counter-name counter-args))
