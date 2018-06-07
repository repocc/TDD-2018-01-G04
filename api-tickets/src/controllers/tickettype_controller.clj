(ns controllers.tickettype-controller)
(use 'db.tickettype-model)

(defn find-all-ticket-types [] (
	let [
		tickettypes (db-find-all-ticket-types)
	]
	(if (nil? tickettypes) [] tickettypes)))

