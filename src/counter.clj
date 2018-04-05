(ns counter)
(use 'expression)

(defn get-counter-by-name [counters counter-name] 
	(first (filter #(= (:name %) counter-name) counters))) 

(defn evaluate-rules [state new-data]
	(let [
		conditions (map #(define-expression (:condition %) new-data) (:counters state))
	]
	conditions))