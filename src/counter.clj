(ns counter)

(defn get-counter-by-name [counters counter-name] 
	(first (filter #(= (:name %) counter-name) counters))) 


(defmulti get-counter-value (fn[counters counter-name args] (get-counter-by-name counters counter-name)))

(defmethod get-counter-value nil [counters counter-name args]
    0)

(defmethod get-counter-value :default [counters counter-name args]
	(let [
		counter (get-counter-by-name counters counter-name)
		counter-value (get (counter :subcounters) args)
	]
	(if (nil? counter-value) 0 counter-value)))



