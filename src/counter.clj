(ns counter)
(use 'expression)

(defn get-counter-by-name [counters counter-name] 
	(first (filter #(= (:name %) counter-name) counters))) 

(defn evaluate-rules [state new-data]
	(let [
		current {"spam" "tes"}
		conditions (map #(define-expression (:condition %) current new-data) (:counters state))
	]
	conditions))

