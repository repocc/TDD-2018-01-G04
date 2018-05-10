(ns core.rule)

(defn define-counter [params, type]
	( let [
			name (first params)
			parameters (second params)
			step 1
			subcounters {}
			condition (last params)
	] 
	(zipmap [:type :name :step :parameters :subcounters :condition ] [type name step parameters subcounters condition])))

(defn define-signal [params, type]
	( let [
			name (first (keys (first params)))
			expression (first (vals (first params)))
			condition (last params)
	] 
	(zipmap [:type :name :expression :condition] [type name expression condition])))

(defn define-step-counter [params, type]
	( let [
			name (first params)
			step (second params)
			last-params (rest params)
			parameters (second last-params)
			subcounters {}
			condition (last last-params)
	] 
	(zipmap [:type :name :step :parameters :subcounters :condition] [type name step parameters subcounters condition])))


(defmulti define-rule (fn[type params] (symbol type)))

(defmethod define-rule 'define-counter [type, params]         
    (define-counter params type))

(defmethod define-rule 'define-signal [type, params]         
    (define-signal params type))

(defmethod define-rule 'define-step-counter [type, params]         
    (define-step-counter params type))

(defn is-counter [rule] 
	(= (:type rule) 'define-counter))

(defn is-signal [rule] 
	(= (:type rule) 'define-signal))

(defn is-step-counter [rule] 
	(= (:type rule) 'define-step-counter))

(defn evaluate-function [f] 
	(define-rule (first f) (rest f)))  
