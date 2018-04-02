(ns rules)

(defn define-counter [params, type]
	( let [
			name (first params)
			subcounters (second params)
			condition (last params)
	] 
	(zipmap [:type :name :subcounters :condition] [type name subcounters condition])))

(defn define-signal [params, type]
	( let [
			name (first (keys (first params)))
			expression (first (vals (first params)))
			condition (last params)
	] 
	(zipmap [:type :name :expression :condition] [type name expression condition])))

(defmulti define-rule 
  (fn[function params] (function "function")))

(def define-counter-type "define-counter")
(def define-signal-type "define-signal")

(defmethod define-rule define-counter-type [function, params]         
    (define-counter params define-counter-type))

(defmethod define-rule define-signal-type [function, params]         
    (define-signal params define-signal-type))

(defn is-counter [rule] 
	(= (:type rule) define-counter-type))

(defn is-signal [rule] 
	(= (:type rule) define-signal-type))

(defn evaluate-function [f] 
	(define-rule {"function" (str (first f))} (rest f)))  
