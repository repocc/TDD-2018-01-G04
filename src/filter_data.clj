(ns filter-data)

(defn find-field-params [counter]
	(let [
		params-past  (filter #(= (% :type) 'past) (counter :parameters))
	]
	(into [] (map #(% :field) params-past))))
	

(defn find-data [counters signals]
	(let [
		data-params (remove #(= % []) (distinct (map find-field-params counters)))
	]
	(distinct (flatten (concat data-params data-params)))))