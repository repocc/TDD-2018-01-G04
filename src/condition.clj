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
