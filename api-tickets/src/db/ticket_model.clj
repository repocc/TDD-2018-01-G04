(ns db.ticket-model)
(use 'db.base-model)

(defn db-find-all-tickets [] (db-find-all :tickets))

(defn db-get-tickets-by-project [project] (db-find-where :tickets :project project))

(defn db-drop-ticket-by-id [id] (db-drop-where :tickets :id id))

(defn db-store-ticket [ticket] (db-store :tickets ticket))
