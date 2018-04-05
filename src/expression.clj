(ns expression)
(use 'condition)

(defmulti define-expression (fn[condition element] (type condition)))
(defmulti define-expression-by-symbol (fn[operation condition element] (symbol operation)))

(defmethod define-expression java.lang.Boolean [condition element]         
    (define-condition condition element))

(defmethod define-expression :default [condition element]         
    (let [
    		operation (first condition)
    	] 
    (define-expression-by-symbol operation condition element)))

(defmethod define-expression-by-symbol 'current [operation condition element]         
    (define-condition condition element))

(defmethod define-expression-by-symbol 'past [operation condition element]         
    (define-condition condition element))

(defmethod define-expression-by-symbol '= [operation condition element]         
    (let [
    		firstTerm (second condition)
    		secondTerm (last condition)	
    	]
    (= (define-expression firstTerm element) (define-expression secondTerm element))))

