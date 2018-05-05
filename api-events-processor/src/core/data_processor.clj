(ns data-processor (:use clojure.pprint))
(use 'rule)
(use 'counter)
(use 'rule-evaluator)
(use 'field)

(defn initialize-processor [rules]
  (	let [ 
  		rules (map evaluate-function rules)
  		counters (concat (filter is-counter rules) (filter is-step-counter rules))
  		signals (filter is-signal rules)
      param-fields (find-param-fields counters signals)
  		data [{}]
		](zipmap [:counters :signals :param-fields :data ] [counters signals param-fields data ])
  ))
         
(defn process-data [state new-data]
  ( let [
      [state results] (evaluate-rules state new-data)
      new-state (merge state {:data (filter-fields (state :data) new-data (state :param-fields))})
  	]
  [new-state results]))
         
(defn query-counter [state counter-name counter-args]
  (get-counter-value (:counters  state) counter-name counter-args))
