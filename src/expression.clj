(ns expression)
(use 'condition)
(require '[clojure.string :as str])

(defmulti define-expression (fn[condition current past] (type condition)))
(defmulti define-expression-by-symbol (fn[operation condition current past] (symbol operation)))

(defmethod define-expression java.lang.Boolean [condition current past]         
    (define-condition condition current))

(defmethod define-expression :default [condition current past]         
    (let [
    		operation (first condition)
    	] 
    (define-expression-by-symbol operation condition current past)))

;current past
(defmethod define-expression-by-symbol 'current [operation condition current past]         
    (define-condition condition current))

(defmethod define-expression-by-symbol 'past [operation condition current past]         
    (define-condition condition past))

;For every value
(defmethod define-expression-by-symbol '= [operation condition current past]         
    (= 
    	(define-expression (second condition) current past) 
    	(define-expression (last condition) current past)))

(defmethod define-expression-by-symbol '!= [operation condition current past]         
    (not= 
    	(define-expression (second condition) current past) 
    	(define-expression (last condition) current past)))

;For booleans
(defmethod define-expression-by-symbol 'or [operation condition current past]         
    (or 
    	(define-expression (second condition) current past) 
    	(define-expression (last condition) current past)))

(defmethod define-expression-by-symbol 'and [operation condition current past]         
    (and 
    	(define-expression (second condition) current past) 
    	(define-expression (last condition) current past)))

(defmethod define-expression-by-symbol 'not [operation condition current past]         
    (not (define-expression (second condition) current past)))

;For integer
(defmethod define-expression-by-symbol '+ [operation condition current past]         
    (+
    	(define-expression (second condition) current past) 
    	(define-expression (last condition) current past)))

(defmethod define-expression-by-symbol '- [operation condition current past]         
    (-
    	(define-expression (second condition) current past) 
    	(define-expression (last condition) current past)))

(defmethod define-expression-by-symbol '/ [operation condition current past]         
    (/
    	(define-expression (second condition) current past) 
    	(define-expression (last condition) current past)))

(defmethod define-expression-by-symbol 'mod [operation condition current past]         
    (mod
    	(define-expression (second condition) current past) 
    	(define-expression (last condition) current past)))

(defmethod define-expression-by-symbol '< [operation condition current past]         
    (<
    	(define-expression (second condition) current past) 
    	(define-expression (last condition) current past)))

(defmethod define-expression-by-symbol '> [operation condition current past]         
    (>
    	(define-expression (second condition) current past) 
    	(define-expression (last condition) current past)))

(defmethod define-expression-by-symbol '<= [operation condition current past]         
    (<=
    	(define-expression (second condition) current past) 
    	(define-expression (last condition) current past)))

(defmethod define-expression-by-symbol '>= [operation condition current past]         
    (>=
    	(define-expression (second condition) current past) 
    	(define-expression (last condition) current past)))

;For strings
(defmethod define-expression-by-symbol 'concat [operation condition current past]         
    (str
    	(define-expression (second condition) current past) 
    	(define-expression (last condition) current past)))

(defmethod define-expression-by-symbol 'includes? [operation condition current past]         
    (str/includes?
    	(define-expression (second condition) current past) 
    	(define-expression (last condition) current past)))

(defmethod define-expression-by-symbol 'starts-with? [operation condition current past]         
    (str/starts-with?
    	(define-expression (second condition) current past) 
    	(define-expression (last condition) current past)))

(defmethod define-expression-by-symbol 'ends-with? [operation condition current past]         
    (str/ends-with?
    	(define-expression (second condition) current past) 
    	(define-expression (last condition) current past)))
