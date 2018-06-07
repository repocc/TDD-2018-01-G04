(ns db.role-model)
(use 'db.base-model)

(defn db-find-all-roles [] (db-find-all :roles))
