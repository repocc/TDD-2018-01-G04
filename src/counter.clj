(ns data-processor)

(ns data-processor)

(deftype Subcounter [condition count])
(deftype Counter [name subcounters counter])

(defn define-subcounter[condition]
  (new Subcounter condition 0)
) 

(defn define-counter [name subcounters condition] 
	(new Counter 
	     name 
	     (map define-subcounter subcounters) 
	     (define-subcounter condition)
   )
)