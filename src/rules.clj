(ns rules)

(defn define-subcounter[params]
	( let [
			condition params
			;;count 0
		]
	(zipmap [:condition] [condition])))

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

(defmethod define-rule 'define-counter [function, params]         
    (define-counter params 'define-counter))

(defmethod define-rule 'define-signal [function, params]         
    (define-signal params 'define-signal))

(defn is-counter [rule] 
	(= (:type rule) 'define-counter))

(defn is-signal [rule] 
	(= (:type rule) 'define-signal))

(defn evaluate-function [f] 
	(define-rule {"function" (first f)} (rest f)))  
