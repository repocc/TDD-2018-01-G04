(ns signal-processor)
(use 'expression)

(defn evaluate-expresion [signal past current counters]
	(let [
		value-expression (try
			(define-expression (signal :expression) current past counters)
			(catch Exception e '()))
	]
	( if (= value-expression '() ) '() {(signal :name)  value-expression})))

(defn process-signal [signal past current counters]
	(let [
    	past-data (first (filter #(define-expression (signal :condition) current % '({})) past))
    	ok (define-expression (signal :condition) current past-data '({}))
    ]
    ( if (false? ok ) '() (evaluate-expresion signal past-data current counters))
    ))