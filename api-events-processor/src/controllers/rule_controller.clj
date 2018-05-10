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

(defn in? 
  "true if coll contains elm"
  [coll elm]  
  (some #(= elm %) coll))

(defn find-all-rules [params] (
	let [
		id (get params "id")
		rules (db-find-all-rules)
		response (if (nil? rules) [] rules)
		parsed-id (if (nil? id) () (read-string id))
	]
	(if (nil? id) response (filter #(in? parsed-id (:id %)) response))
))


(defn count-all-rules [] (
	let [
		count (db-count-all-rules)
	]
	{:count count}))