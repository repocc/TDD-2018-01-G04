(ns rules)

(defn define-subcounter[params]
	( let [
			condition params
			count 0
		]
	(zipmap [:condition :count] [condition count])))

(defn define-counter [params, type]
	( let [
			name (first params)
			subcounters (map define-subcounter (second params))
			condition (last params)
			count 0
	] 
	(zipmap [:type :name :subcounters :condition :count] [type name subcounters condition count])))

(defn define-signal [params, type]
	( let [
			name (first (keys (first params)))
			expression (first (vals (first params)))
			condition (last params)
			count 0
	] 
	(zipmap [:type :name :expression :condition :count] [type name expression condition count])))

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
