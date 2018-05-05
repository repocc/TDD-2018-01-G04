(ns controllers.rule-controller)
(use 'db.rule-model)

(defn store-rule [request] (
	let [
			query (get-in request [:params :query])
      rule {:query query}
  ]
  (db-store-rule rule)     
  {:status 201 :body rule}
))

(defn find-all-rules [] (
	let [
		rules (db-find-all-rules)
	]
	(if (nil? rules) [] rules)))