(ns core.field)

(defmulti find-fields (fn[item] (first item)))

(defmethod find-fields 'past [item]
       (let [
          item  (into[] (concat (find-fields (rest item)) [(second item)]))
        ]
        item)
)

(defmethod find-fields nil [item]
       []
)

(defmethod find-fields :default [item]
    (find-fields (rest item)))

(defn find-param-fields [counters signals]
    (let [
        counter-param-fields (remove #(= % []) (distinct (map #(find-fields (flatten(% :parameters)) ) counters)))
        counter-condition-fields (remove #(= % []) (distinct (map #(find-fields (flatten(% :condition)) ) counters)))
        signal-condition-fields (remove #(= % []) (distinct (map #(find-fields (flatten(% :condition)) ) signals)))
        signal-expression-fields (remove #(= % []) (distinct (map #(find-fields (flatten(% :expression)) ) signals)))
    ]
    (distinct (flatten (concat counter-param-fields counter-condition-fields signal-condition-fields signal-expression-fields)))
    ))

(defn filter-fields [data new-data filter-data]
    (distinct (conj data (select-keys new-data filter-data))))
