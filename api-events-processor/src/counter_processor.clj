(ns counter-processor)
(use 'condition)
(use 'counter)

(defmulti calculate-counter (fn[counter counters current past is-valid] (true? is-valid)))

(defmethod calculate-counter true [counter counters current past is-valid]         
    (let [
    	subcounter (map #(define-condition % current past counters) (counter :parameters))
        step-evaluation (define-condition (counter :step) current past counters)
    	subcounter-value (increment-counter-value (counter :subcounters) subcounter step-evaluation)
    	final-subcounter (merge (counter :subcounters) {(into [] subcounter) subcounter-value})
    ]
    (merge counter {:subcounters final-subcounter})))

(defmethod calculate-counter :default [counter counters current past is-valid]         
    counter)

(defn process-counter [counter counters data new-data] 
    (let [
        [sample-data ok] (validate-condition (:condition counter) new-data data)
    ]
    (calculate-counter counter counters new-data sample-data ok)))
