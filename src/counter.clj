(ns counter)
(use 'expression)
(use 'counter-processor)

(defn get-counter-by-name [counters counter-name] 
	(first (filter #(= (:name %) counter-name) counters))) 


(defn evaluate-counters-rules [counters data new-data]
	(map #(process-counter % data new-data) counters))



(defn evaluate-rules [state new-data]
	(let [
		counters-rules (evaluate-counters-rules (state :counters) (state :data) new-data)
	]
	(merge state {:counters counters-rules})))


;(defn evaluate-rules [state new-data]
;	(let [
;		current {"spam" "tes"}
;		conditions (map #(define-expression (:condition %) current new-data) (:counters state))
;	]
;	conditions))

