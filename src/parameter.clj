(ns parameter)

(defmulti define-parameter (fn[param] (first param)))

(defn define-parameter-format [type field]
    (zipmap [:type :field ] [ type field]))

(defmethod define-parameter 'current [param]
	(define-parameter-format (first param) (second param)))

(defmethod define-parameter 'past [param]
    (define-parameter-format (first param) (second param)))

(defmethod define-parameter :default [param]
    (define-parameter-format "literal" (second param)))


(defn has-params-of-type [type params]
    (some true? (map #(= (% :type) type) params)))

(defn get-params-of-type [type params]
    (filter #(= (% :type) type) params))

(defmulti get-param-value (fn[param current past] (param :type)))

(defmethod get-param-value "literal" [param current past]
    (param :field))

(defmethod get-param-value 'current [param current past]
    (get current (param :field)))

(defmethod get-param-value 'past [param current past]
    (get past (param :field)))

(defn find-counter-param-fields [counter]
	(let [
		past-params (get-params-of-type 'past (counter :parameters))
	]
	(into [] (map #(% :field) past-params))))
	

(defn find-param-fields [counters]
	(let [
		counter-param-fields (remove #(= % []) (distinct (map find-counter-param-fields counters)))
	]
	(distinct (flatten counter-param-fields))))