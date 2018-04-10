(ns signal-processor)
(use 'condition)

(defn evaluate-condition [signal past current counters]
	(let [
		condition-value (try
			(define-condition (signal :expression) current past counters)
			(catch Exception e '()))
	]
	( if (= condition-value '() ) '() {(signal :name) condition-value})))

(defn process-signal [signal data current counters]
	(let [
    	past-data (first (filter #(define-condition (signal :condition) current % '({})) data))
    	ok (define-condition (signal :condition) current past-data '({}))
    ]
    ( if (false? ok ) '() (evaluate-condition signal past-data current counters))))