(ns signal-processor)
(use 'condition)

(defn evaluate-expresion [signal past current counters]
	(let [
		value-expression (try
			(define-condition (signal :expression) current past counters)
			(catch Exception e '()))
	]
	( if (= value-expression '() ) '() {(signal :name)  value-expression})))

(defn process-signal [signal past current counters]
	(let [
    	past-data (first (filter #(define-condition (signal :condition) current % '({})) past))
    	ok (define-condition (signal :condition) current past-data '({}))
    ]
    ( if (false? ok ) '() (evaluate-expresion signal past-data current counters))
    ))