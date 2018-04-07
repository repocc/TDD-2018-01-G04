(ns condition)

(defmulti define-condition (fn[condition element] (type condition)))

(defmethod define-condition java.lang.Boolean [condition element]         
    condition)

(defmethod define-condition java.lang.Integer [condition element]         
    condition)

(defmethod define-condition java.lang.String [condition element]         
    condition)

(defmethod define-condition :default [condition element]
	( let [
			type (first condition)
			key (second condition)
			value (get element key)
		]
		;element could be current or past objects being evaluated
   	value))


;TODO: refactoring
(defn get-counter-value-expr [counters counter-name args]
	(let [
		counter (first (filter #(= (:name %) counter-name) counters))
		counter-value ( if (nil? counter) 0 (get (counter :subcounters) args) )
		]
		(if (nil? counter-value) 0 counter-value )))