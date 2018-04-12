(ns parameter)

(defmulti define-parameter (fn[param] (type param)))
(defmulti define-parameter-by-symbol (fn[param] (first param)))

(defn define-parameter-format [type field]
    (zipmap [:type :field ] [ type field]))

(defmethod define-parameter java.lang.Boolean [param]         
    (define-parameter-format "literal" param))

(defmethod define-parameter java.lang.Long [param]         
    (define-parameter-format "literal" param))

(defmethod define-parameter java.lang.String [param]         
    (define-parameter-format "literal" param))

(defmethod define-parameter :default [param]         
    (define-parameter-by-symbol param))


(defmethod define-parameter-by-symbol 'current [param]
	(define-parameter-format (first param) (second param)))

(defmethod define-parameter-by-symbol 'past [param]
    (define-parameter-format (first param) (second param)))

(defmethod define-parameter-by-symbol :default [param]
    (define-parameter-format "literal" param))


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
	

(defmulti find-fields (fn[item] (first item)))

(defmethod find-fields 'past [item]
       (let [
          item  (into[] (concat (find-fields (rest item)) [(second item)]))
        ]
        item)
)

(defmethod find-fields nil [item]
       []
)

(defmethod find-fields :default [item]
    (find-fields (rest item)))

(defn find-param-fields [counters signals]
	(let [
		counter-param-fields (remove #(= % []) (distinct (map find-counter-param-fields counters)))
		counter-condition-fields (remove #(= % []) (distinct (map #(find-fields (flatten(% :condition)) ) counters)))
		signal-condition-fields (remove #(= % []) (distinct (map #(find-fields (flatten(% :condition)) ) signals)))
		signal-expression-fields (remove #(= % []) (distinct (map #(find-fields (flatten(% :expression)) ) signals)))
	]
	(distinct (flatten (concat counter-param-fields counter-condition-fields signal-condition-fields signal-expression-fields)))
	))

(defn filter-fields [data new-data filter-data]
	(distinct (conj data (select-keys new-data filter-data))))
