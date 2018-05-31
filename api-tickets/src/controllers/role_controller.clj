(ns controllers.role-controller)
(use 'db.role-model)

(defn find-all-roles [] (
	let [
		roles (db-find-all-roles)
	]
	(if (nil? roles) [] roles)))
