(ns db.project-model)
(use 'db.base-model)

(defn db-find-all-projects [] (db-find-all :projects))

(defn db-store-project [project] (db-store :projects project))
