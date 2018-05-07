(ns controllers.rule-controller)
(require '[clojure.string :as str])
(use 'db.rule-model)
(use 'core.rule)
(use 'utils.string-util)

(defn store-rule [request] (
	let [
			name (get-in request [:params :name])
			query (get-in request [:params :query])
			parsed-query (try (evaluate-function (read-string query)) (catch Exception e nil))
			invalid-query (nil? parsed-query)
      
      rule (if invalid-query nil {:id (uuid) :name name :query query}) 
      status-code (if invalid-query 500 201)
  ]
  (if invalid-query nil (db-store-rule rule))
 	{:status status-code :body rule}
))

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