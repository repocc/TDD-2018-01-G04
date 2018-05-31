(ns db.tickettype-model)
(use 'db.base-model)

(defn db-find-all-ticket-types [] (db-find-all :tickettypes))