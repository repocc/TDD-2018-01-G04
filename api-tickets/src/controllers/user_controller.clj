(ns controllers.user-controller)
(use 'db.user-model)

(defn auth-by-username [request] (
	let [
		username (get-in request [:params :username])
		users (db-find-user-by-username username)
	]
	(if (or (nil? users) (empty? users)) 
		{:status 500 } 
		{:status 200 :body (first users)})
))

(defn find-all-users [] (
	let [
		users (db-find-all-users)
	]
	(if (nil? users) [] users)))

