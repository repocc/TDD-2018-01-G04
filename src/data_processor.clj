(ns data-processor)

(defn define-counter [params]
	(zipmap [:name :subcounters :condition] params))

(defmulti define-rule 
  (fn[function params] (function "function")))

(defmethod define-rule "define-counter" [function, params]         
    (define-counter params))
  
(defn evaluate-function [f] 
	(define-rule {"function" (str (first f))} (rest f)))  

(defn initialize-processor [rules]
  (map evaluate-function rules))
         
(defn process-data [state new-data]
  [nil []])
         
(defn query-counter [state counter-name counter-args]
  0)