(ns counter)
(use 'expression)
(use 'counter-processor)

(defn get-counter-by-name [counters counter-name] 
	(first (filter #(= (:name %) counter-name) counters))) 


(defmulti get-counter-value (fn[counters counter-name args] (get-counter-by-name counters counter-name)))

(defmethod get-counter-value nil [counters counter-name args]
    (first [0]))

(defmethod get-counter-value :default [counters counter-name args]
	(let [
		counter (get-counter-by-name counters counter-name)
		counter-value (get (counter :subcounters) args)
	]
	(if (nil? counter-value) 0 (first [counter-value]))))


(defn evaluate-counters-rules [counters data new-data]
	(map #(process-counter % data new-data) counters))


(defn evaluate-rules [state new-data]
	(let [
		counters-rules (evaluate-counters-rules (state :counters) (state :data) new-data)
	]
	(merge state {:counters counters-rules})))

