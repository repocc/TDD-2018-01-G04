(ns parameter)

(defmulti define-parameter (fn[param] (first param)))

(defn define-parameter-format [type field]
    (zipmap [:type :field ] [ type field]))

(defmethod define-parameter 'current [param]
	(define-parameter-format (first param) (second param)))

(defmethod define-parameter 'past [param]
    (define-parameter-format (first param) (second param)))

(defmethod define-parameter :default [param]
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
	

(defmulti find-fields (fn[dato] (first dato)))

(defmethod find-fields 'past [dato]
       (let [
          dato  (into[] (concat (find-fields (rest dato)) [(second dato)]))
        ]
        dato)
)

(defmethod find-fields nil [dato]
       []
)

(defmethod find-fields :default [dato]
    (find-fields (rest dato)))

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
