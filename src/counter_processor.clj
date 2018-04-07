(ns counter-processor)
(use 'expression)

;validate conditions
(defn validate-condition [condition current past]
	(define-expression condition current past '({})))

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
    (true? true))

(defmulti check-past (fn[param data] (param :type)))

(defmethod check-past 'past [param data]
    (contains? data (param :field)))

(defmethod check-past :default [param data]
    (true? true))

(defn check-single-data [params data]
	(every? true? (map #(check-past % data) params)))

(defn exist-params-in-fields [counter data new-data] 
	(let [
		exist-current (every? true? (map #(check-current % new-data) (counter :parameters)))
		exist-past (some true? (map #(check-single-data (counter :parameters) %) data))
	]
	(every? true? [exist-current exist-past])))


(defmulti get_subcounter-value (fn[subcounters key] (nil? (get subcounters key))))

(defmethod get_subcounter-value true [subcounters key]
    (+ 0 1))

(defmethod get_subcounter-value false [subcounters key]
    (+ (get subcounters key) 1))

(defmulti get_field (fn[param current past] (param :type)))

(defmethod get_field "literal" [param current past]
    (param :field))

(defmethod get_field 'current [param current past]
    (get current (param :field)))

(defmethod get_field 'past [param current past]
    (get past (param :field)))

(defmulti calculate-counter (fn[counter current past ok] (true? ok)))

(defmethod calculate-counter true [counter current past ok]         
    (let [
    	subcounter (map #(get_field % current past) (counter :parameters))
    	subcounter-value (get_subcounter-value (counter :subcounters) subcounter)
    	final-subcounter (merge (counter :subcounters) {(into [] subcounter) subcounter-value})
    ]
    (merge counter {:subcounters final-subcounter})))

(defmethod calculate-counter false [counter current past ok]         
    (first [counter]))


(defmulti process-counter-with-params (fn[counter data new-data] (exist-params-in-fields counter data new-data)))

(defmethod process-counter-with-params true [counter data new-data]
    (let [
    	[past-data ok] (get-validate-data counter new-data data)
    ]
    (calculate-counter counter new-data past-data ok)))

(defmethod process-counter-with-params false [counter data new-data]         
    (first [counter]))


(defmulti process-counter-without-params (fn[counter data new-data] (second (get-validate-data counter new-data data))))

(defmethod process-counter-without-params true [counter data new-data]
    (let [
    	subcounter (map #(get_field % data new-data) (counter :parameters))
    	subcounter-value (get_subcounter-value (counter :subcounters) subcounter)
    	final-subcounter (merge (counter :subcounters) {(into [] subcounter) subcounter-value})
    ]
    (merge counter {:subcounters final-subcounter})))

(defmethod process-counter-without-params false [counter data new-data]         
    (first [counter]))


(defn check-counter-with-params [params]
	(let [
		current-params (some true? (map #(= (% :type) 'current) params))
		past-params (some true? (map #(= (% :type) 'past) params))
	]
	(or current-params past-params)))


(defmulti process-counter (fn[counter data new-data] (check-counter-with-params (counter :parameters))))

(defmethod process-counter true [counter data new-data]
    (process-counter-with-params counter data new-data))

(defmethod process-counter nil [counter data new-data]         
    (process-counter-without-params counter data new-data))