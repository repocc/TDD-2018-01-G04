(ns controllers.rule-controller)
(require '[clojure.string :as str])
(use 'db.rule-model)
(use 'core.rule)

(defn store-rule [request] (
	let [
			name (get-in request [:params :name])
			query (get-in request [:params :query])
			parsed-query (try (evaluate-function (read-string query)) (catch Exception e nil))
			invalid-query (nil? parsed-query)
      
      rule (if invalid-query nil {:name name :query query}) 
      status-code (if invalid-query 500 201)
  ]
  (if invalid-query nil (db-store-rule rule))
 	{:status status-code :body rule}
))

(defn drop-rule-by-name [name] 
	(db-drop-rule-by-name name)
 	{:status 200}
)

(defn find-all-rules [] (
	let [
		rules (db-find-all-rules)
	]
	(if (nil? rules) [] rules)))


(defn count-all-rules [] (
	let [
		count (db-count-all-rules)
	]
	{:count count}))