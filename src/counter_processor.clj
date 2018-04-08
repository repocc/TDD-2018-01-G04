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
(defmulti check-current (fn[param data] (param :type)))

(defmethod check-current 'current [param data]
    (contains? data (param :field)))

(defmethod check-current :default [param data]
    true)

(defmulti check-past (fn[param data] (param :type)))

(defmethod check-past 'past [param data]
    (contains? data (param :field)))

(defmethod check-past :default [param data]
    true)

(defn check-single-data [params data]
	(every? true? (map #(check-past % data) params)))

(defn exist-params-in-fields [counter data new-data] 
	(let [
		exist-current (every? true? (map #(check-current % new-data) (counter :parameters)))
		exist-past (some true? (map #(check-single-data (counter :parameters) %) data))
	]
	(every? true? [exist-current exist-past])))

(defmulti calculate-counter (fn[counter current past ok] (true? ok)))

(defmethod calculate-counter true [counter current past ok]         
    (let [
    	subcounter (map #(get-param-value % current past) (counter :parameters))
    	subcounter-value (increment-counter-value (counter :subcounters) subcounter)
    	final-subcounter (merge (counter :subcounters) {(into [] subcounter) subcounter-value})
    ]
    (merge counter {:subcounters final-subcounter})))

(defmethod calculate-counter false [counter current past ok]         
    counter)


(defmulti process-counter-with-params (fn[counter data new-data] 
    (exist-params-in-fields counter data new-data)))

(defmethod process-counter-with-params true [counter data new-data]
    (let [
    	[past-data ok] (get-validate-data counter new-data data)
    ]
    (calculate-counter counter new-data past-data ok)))

(defmethod process-counter-with-params false [counter data new-data]         
    counter)


(defmulti process-counter-without-params (fn[counter data new-data] 
    (second (get-validate-data counter new-data data))))

(defmethod process-counter-without-params true [counter data new-data]
    (let [
    	subcounter (map #(get-param-value % data new-data) (counter :parameters))
    	subcounter-value (increment-counter-value (counter :subcounters) subcounter)
    	final-subcounter (merge (counter :subcounters) {(into [] subcounter) subcounter-value})
    ]
    (merge counter {:subcounters final-subcounter})))

(defmethod process-counter-without-params false [counter data new-data]         
    counter)

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

(defmethod process-counter nil [counter data new-data]         
    (process-counter-without-params counter data new-data))