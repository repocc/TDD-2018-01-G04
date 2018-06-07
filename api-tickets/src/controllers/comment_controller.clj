(ns controllers.comment-controller)
(use 'db.comment-model)
(use 'db.ticket-model)

(defn store-comment [request] (
	let [
			ticket (get-in request [:params :ticket])
			author (get-in request [:params :author])
			text (get-in request [:params :text])
			comment {:ticket ticket :author author :text text} 
  ]
  (db-store-comment comment)
 	{:status 200 :body comment}
))
