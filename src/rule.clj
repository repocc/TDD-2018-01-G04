(ns rule)
(use 'parameter)

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

(defn define-counter-step [params, type]
	( let [
			name (first params)
			step (second params)
			last-params (last params)
			parameters (map define-parameter (second last-params))
			condition (second last-params)	
	] 
	(zipmap [:type :name :step :parameters :condition] [type name step parameters condition])))


(defmulti define-rule (fn[type params] (symbol type)))

(defmethod define-rule 'define-counter [type, params]         
    (define-counter params type))

(defmethod define-rule 'define-signal [type, params]         
    (define-signal params type))

(defmethod define-rule 'define-counter-step [type, params]         
    (define-counter-step params type))

(defn is-counter [rule] 
	(= (:type rule) 'define-counter))

(defn is-signal [rule] 
	(= (:type rule) 'define-signal))

(defn is-counter-step [rule] 
	(= (:type rule) 'define-counter-step))

(defn evaluate-function [f] 
	(define-rule (first f) (rest f)))  
