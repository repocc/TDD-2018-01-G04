(ns counter-processor)
(use 'condition)
(use 'counter)
(use 'parameter)

;validate conditions
(defn validate-condition [condition current past]
	(define-condition condition current past '({})))

(defn get-validate-data [counter current past]
	(let [
    	past-data (first (filter #(validate-condition (counter :condition) current %) past))
    	ok (validate-condition (counter :condition) current past-data)
    ]
    [past-data ok]))


;check if params exist in the data
(defn has-param-field [param data] 
    (contains? data (param :field)))

(defn has-every-param-field [params data]
	(every? true? (map #(has-param-field % data) params)))

(defn exist-params-in-fields [counter data new-data] 
	(let [
        parameters (counter :parameters)
        current-parameters (get-params-of-type 'current parameters)
        past-parameters (get-params-of-type 'past parameters)
		exist-current (every? true? (map #(has-param-field % new-data) current-parameters))
		exist-past (some true? (map #(has-every-param-field past-parameters %) data))
	]
    (every? true? [exist-current exist-past])))


(defmulti calculate-counter (fn[counter current past is-valid] (true? is-valid)))

(defmethod calculate-counter true [counter current past is-valid]         
    (let [
    	subcounter (map #(get-param-value % current past) (counter :parameters))
    	subcounter-value (increment-counter-value (counter :subcounters) subcounter)
    	final-subcounter (merge (counter :subcounters) {(into [] subcounter) subcounter-value})
    ]
    (merge counter {:subcounters final-subcounter})))

(defmethod calculate-counter false [counter current past is-valid]         
    counter)


(defn process-counter-with-params [counter data new-data] 
    (let [
        is-param-in-fields (exist-params-in-fields counter data new-data)
        [past-data ok] (get-validate-data counter new-data data)
        is-valid (and is-param-in-fields ok)
    ]
    (calculate-counter counter new-data past-data is-valid)))

(defn process-counter-without-params [counter data new-data] 
    (let [
        is-valid (second (get-validate-data counter new-data data))
    ]
    (calculate-counter counter new-data data is-valid)))


(defmulti process-counter (fn[counter data new-data] 
    (let [
        parameters (counter :parameters)
    ]
    (or 
        (has-params-of-type 'current parameters)
        (has-params-of-type 'past parameters)
    ))))

(defmethod process-counter true [counter data new-data]
    (process-counter-with-params counter data new-data))

(defmethod process-counter :default [counter data new-data]         
    (process-counter-without-params counter data new-data))