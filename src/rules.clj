(ns rules)

(defn define-counter [params]
	(zipmap [:name :subcounters :condition] params))

(defn define-signal [params]
	( let [
			name (first (keys (first params)))
			expression (first (vals (first params)))
			condition (last params)
		] 
	(zipmap [:name :expression :condition] [name expression condition])))

(defmulti define-rule 
  (fn[function params] (function "function")))

(defmethod define-rule "define-counter" [function, params]         
    (define-counter params))

(defmethod define-rule "define-signal" [function, params]         
    (define-signal params))

(defn evaluate-function [f] 
	(define-rule {"function" (str (first f))} (rest f)))  
