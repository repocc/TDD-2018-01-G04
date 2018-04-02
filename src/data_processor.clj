(ns data-processor)
(use 'rules)

(defn initialize-processor [rules]
  (map evaluate-function rules))
         
(defn process-data [state new-data]
  [nil []])
         
(defn query-counter [state counter-name counter-args]
  0)