(ns parameter)

(defn define-literal [literal]
	(zipmap [:type :field ] [ "literal" literal]))

(defmulti define-parameter (fn[param] (type param)))

(defmethod define-parameter java.lang.Boolean [param]
    (define-literal param))

(defmethod define-parameter java.lang.Long [param]         
    (define-literal param))

(defmethod define-parameter java.lang.String [param]         
    (define-literal param))

(defmethod define-parameter clojure.lang.PersistentVector [param]
    (define-literal param))

(defmethod define-parameter clojure.lang.PersistentArrayMap [param]         
    (define-literal param))

(defmethod define-parameter clojure.lang.Symbol [param]         
    (define-literal param))

(defmethod define-parameter :default [param]
	( let [
			type (first param)
			field (second param)
	]	
   	(zipmap [:type :field ] [type field])))
