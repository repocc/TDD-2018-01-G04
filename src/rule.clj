(ns rule)
(use 'condition)
(use 'parameter)

(defn find-data [counters signals]
	(true? true))

(defn define-subcounter[params]
	( let [
			condition params
		]
	(zipmap [:condition] [condition])))

(defn define-counter [params, type]
	( let [
			name (first params)
			parameters (map define-parameter (second params))
			subcounters {}
			condition (last params)
	] 
	(zipmap [:type :name :parameters :subcounters :condition ] [type name parameters subcounters condition])))

(defn define-signal [params, type]
	( let [
			name (first (keys (first params)))
			expression (first (vals (first params)))
			condition (last params)
	] 
	(zipmap [:type :name :expression :condition] [type name expression condition])))

(defmulti define-rule (fn[type params] (symbol type)))

(defmethod define-rule 'define-counter [type, params]         
    (define-counter params type))

(defmethod define-rule 'define-signal [type, params]         
    (define-signal params type))

(defn is-counter [rule] 
	(= (:type rule) 'define-counter))

(defn is-signal [rule] 
	(= (:type rule) 'define-signal))

(defn evaluate-function [f] 
	(define-rule (first f) (rest f)))  
