(ns expression)
(use 'condition)
(use 'counter)
(require '[clojure.string :as str])

(defmulti define-expression (fn[condition current past counters] (type condition)))
(defmulti define-expression-by-symbol (fn[operation condition current past counters] (symbol operation)))

(defmethod define-expression java.lang.Boolean [condition current past counters]         
    (define-condition condition current))

(defmethod define-expression java.lang.Integer [condition current past counters]         
    (define-condition condition current))

(defmethod define-expression java.lang.String [condition current past counters]         
    (define-condition condition current))

(defmethod define-expression :default [condition current past counters]         
    (let [
    		operation (first condition)
    	] 
    (define-expression-by-symbol operation condition current past counters)))

;current past
(defmethod define-expression-by-symbol 'current [operation condition current past counters]         
    (define-condition condition current))

(defmethod define-expression-by-symbol 'past [operation condition current past counters]         
    (define-condition condition past))

(defmethod define-expression-by-symbol 'counter-value [operation condition current past counters]         
    (get-counter-value counters (second condition) (last condition)))


;For every value
(defmethod define-expression-by-symbol '= [operation condition current past counters]         
    (= 
    	(define-expression (second condition) current past counters) 
    	(define-expression (last condition) current past counters)))

(defmethod define-expression-by-symbol '!= [operation condition current past counters]         
    (not= 
    	(define-expression (second condition) current past counters) 
    	(define-expression (last condition) current past counters)))

;For booleans
(defmethod define-expression-by-symbol 'or [operation condition current past counters]         
    (or 
    	(define-expression (second condition) current past counters) 
    	(define-expression (last condition) current past counters)))

(defmethod define-expression-by-symbol 'and [operation condition current past counters]         
    (and 
    	(define-expression (second condition) current past counters) 
    	(define-expression (last condition) current past counters)))

(defmethod define-expression-by-symbol 'not [operation condition current past counters]         
    (not (define-expression (second condition) current past counters)))

;For integer
(defmethod define-expression-by-symbol '+ [operation condition current past counters]         
    (+
    	(define-expression (second condition) current past counters) 
    	(define-expression (last condition) current past counters)))

(defmethod define-expression-by-symbol '- [operation condition current past counters]         
    (-
    	(define-expression (second condition) current past counters) 
    	(define-expression (last condition) current past counters)))

(defmethod define-expression-by-symbol '/ [operation condition current past counters]         
    (/
    	(define-expression (second condition) current past counters) 
    	(define-expression (last condition) current past counters)))

(defmethod define-expression-by-symbol 'mod [operation condition current past counters]         
    (mod
    	(define-expression (second condition) current past counters) 
    	(define-expression (last condition) current past counters)))

(defmethod define-expression-by-symbol '< [operation condition current past counters]         
    (<
    	(define-expression (second condition) current past counters) 
    	(define-expression (last condition) current past counters)))

(defmethod define-expression-by-symbol '> [operation condition current past counters]         
    (>
    	(define-expression (second condition) current past counters) 
    	(define-expression (last condition) current past counters)))

(defmethod define-expression-by-symbol '<= [operation condition current past counters]         
    (<=
    	(define-expression (second condition) current past counters) 
    	(define-expression (last condition) current past counters)))

(defmethod define-expression-by-symbol '>= [operation condition current past counters]         
    (>=
    	(define-expression (second condition) current past counters) 
    	(define-expression (last condition) current past counters)))

;For strings
(defmethod define-expression-by-symbol 'concat [operation condition current past counters]         
    (str
    	(define-expression (second condition) current past counters) 
    	(define-expression (last condition) current past counters)))

(defmethod define-expression-by-symbol 'includes? [operation condition current past counters]         
    (str/includes?
    	(define-expression (second condition) current past counters) 
    	(define-expression (last condition) current past counters)))

(defmethod define-expression-by-symbol 'starts-with? [operation condition current past counters]         
    (str/starts-with?
    	(define-expression (second condition) current past counters) 
    	(define-expression (last condition) current past counters)))

(defmethod define-expression-by-symbol 'ends-with? [operation condition current past counters]         
    (str/ends-with?
    	(define-expression (second condition) current past counters) 
    	(define-expression (last condition) current past counters)))
