(ns condition)
(use 'counter)
(require '[clojure.string :as str])

(defmulti define-condition (fn[condition current past counters] (type condition)))
(defmulti define-condition-by-symbol (fn[operation condition current past counters] (symbol operation)))

(defmethod define-condition java.lang.Boolean [condition current past counters]         
    condition)

(defmethod define-condition java.lang.Integer [condition current past counters]         
    condition)

(defmethod define-condition java.lang.String [condition current past counters]         
    condition)

(defmethod define-condition :default [condition current past counters]         
    (let [
    		operation (first condition)
    	] 
    (define-condition-by-symbol operation condition current past counters)))

(def symbols 
    {'= =, 
    '!= not=, 
    ;'or some,
    ;'and every?,
    'not not,
    '+ +,
    '- -,
    '/ /,
    'mod mod,
    '< <,
    '> >,
    '<= <=,
    '>= >=,
    'concat str,
    'includes? str/includes?,
    'starts-with? str/starts-with?,
    'ends-with? str/ends-with?}
)

(defn get-condition-value [condition, element] 
    (   let [
            key (second condition)
            value (get element key)
        ]
    value))

(defmethod define-condition-by-symbol 'current [operation condition current past counters]         
    (get-condition-value condition current))

(defmethod define-condition-by-symbol 'past [operation condition current past counters]         
    (get-condition-value condition past))

(defn get-conditions [condition current past counters] 
    (map #(define-condition % current past counters) (rest condition)))

(defmethod define-condition-by-symbol 'counter-value [operation condition current past counters]         
    (get-counter-value counters (second condition) (last condition)))

(defmethod define-condition-by-symbol 'and [operation condition current past counters]         
    (every? true? (get-conditions condition current past counters)))

(defmethod define-condition-by-symbol 'or [operation condition current past counters]         
    (some true? (get-conditions condition current past counters)))

(defmethod define-condition-by-symbol :default [operation condition current past counters]
    (apply (get symbols operation) (get-conditions condition current past counters)))

