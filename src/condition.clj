(ns condition)

;TODO: We need to cover all the cases. Probably a recursive function

(defmulti define-condition (fn[condition] (type condition)))

(defmethod define-condition java.lang.Boolean [condition]         
    condition)

(defmethod define-condition :default [condition]
	( let [
			type (first condition)
			key (second condition)
			value true
		]
   	(zipmap [:type :key :value] [type key value])))
