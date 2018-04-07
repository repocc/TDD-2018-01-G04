(ns signal-processor)
(use 'expression)

(defmulti process-signal (fn[signal past current] (define-expression (signal :condition) current past)))

(defmethod process-signal true [signal past current]
	(let [
		;value-expression (define-expression (signal :expression) current past)
		value-expression (true? false)
	]
	{(signal :name)  value-expression}))

(defmethod process-signal false [signal past current]
    '())
