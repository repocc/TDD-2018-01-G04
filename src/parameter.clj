(ns parameter)

(defn get-ordered-parameters [params]
	(merge {} {(second params) []}))

(defn get-parameters [parameters]
	(let [
		type (symbol (first parameters))
		field (second parameters)
	]
	(zipmap [:type :field] [type field])))

(defn is-current [parameters] 
	(= (:type parameters) 'current))

(defn is-past [parameters] 
	(= (:type parameters) 'past))

(defn get-field [parameter]
	(first (vals (select-keys parameter [:field]))))

(defn define-parameter [parameters]
	(let [ 
		parameters	(map get-parameters parameters)
		current	(map get-field (filter is-current parameters))
		past	(map get-field (filter is-past parameters))
	]
	(zipmap [:current :past ] [current past])))