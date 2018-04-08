(ns expression)
(use 'counter)
(require '[clojure.string :as str])

(defmulti define-expression (fn[condition current past counters] (type condition)))
(defmulti define-expression-by-symbol (fn[operation condition current past counters] (symbol operation)))

(defmethod define-expression java.lang.Boolean [condition current past counters]         
    condition)

(defmethod define-expression java.lang.Integer [condition current past counters]         
    condition)

(defmethod define-expression java.lang.String [condition current past counters]         
    condition)

(defmethod define-expression :default [condition current past counters]         
    (let [
    		operation (first condition)
    	] 
    (define-expression-by-symbol operation condition current past counters)))

(def symbols 
    {'= =, 
    '!= not=, 
    ;'or or,
    ;'and and,
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

(defmethod define-expression-by-symbol 'current [operation condition current past counters]         
    (get-condition-value condition current))

(defmethod define-expression-by-symbol 'past [operation condition current past counters]         
    (get-condition-value condition past))

(defn get-conditions [condition current past counters] 
    (map #(define-expression % current past counters) (rest condition)))

(defmethod define-expression-by-symbol 'counter-value [operation condition current past counters]         
    (get-counter-value counters (second condition) (last condition)))

(defmethod define-expression-by-symbol 'and [operation condition current past counters]         
    (and (get-conditions condition current past counters)))

(defmethod define-expression-by-symbol 'or [operation condition current past counters]         
    (or (get-conditions condition current past counters)))

(defmethod define-expression-by-symbol :default [operation condition current past counters]         
    (apply (get symbols operation) (get-conditions condition current past counters)))

