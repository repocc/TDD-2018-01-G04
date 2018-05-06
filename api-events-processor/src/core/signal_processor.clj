(ns core.signal-processor)
(use 'core.condition)

(defn evaluate-condition [signal past current counters]
	(let [
		condition-value (try
			(define-condition (signal :expression) current past counters)
			(catch Exception e '()))
	]
	( if (= condition-value '() ) '() {(signal :name) condition-value})))

(defn process-signal [signal data current counters]
	(let [
    	[sample-data ok] (validate-condition (:condition signal) current data)
    ]
    ( if (false? ok ) '() (evaluate-condition signal sample-data current counters))))