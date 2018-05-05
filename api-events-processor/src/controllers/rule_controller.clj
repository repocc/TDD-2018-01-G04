(ns controllers.rule-controller)
(require '[clojure.string :as str])
(use 'db.rule-model)
(use 'rule)

(defn store-rule [request] (
	let [
			query (get-in request [:params :query])
			parsed-query (try (evaluate-function (read-string query)) (catch Exception e nil))
      rule {:query query :parsed-query parsed-query}
      status-code (if (nil? parsed-query) 500 201)
  ]
  (if (nil? parsed-query) nil (db-store-rule rule))
 	{:status status-code :body rule}
))

(defn find-all-rules [] (
	let [
		rules (db-find-all-rules)
	]
	(if (nil? rules) [] rules)))